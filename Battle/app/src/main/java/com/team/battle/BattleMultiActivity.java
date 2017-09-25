package com.team.battle;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

/**
 * This activity will be launched if we choosed a multiplayer. To connect with another player we need to click on Bluetooth logo.
 * We can only with a player which device is paired with our device.
 */

public class BattleMultiActivity extends BasicActivity {

    boolean myTurn=false;
    int countG = 0;


    final static int maxN = 10;
    public  ImageView[][] opCells = new ImageView[maxN][maxN];
    public  ImageView[][] myCells = new ImageView[maxN][maxN];
    public  Drawable[] drawCell = new Drawable[5];

    Context context;
    int rows[];
    int cols[];
    TextView statki1;
    TextView statki2;


    String mNick=""; String mOpponentNick="";
    boolean multi;
    boolean status = false;

    //Bluetooth
    BluetoothConnectionService mService;
    BluetoothAdapter mAdapter;
    AlertDialog mAlert;

    //Name of the connected device
    private String mConnectedDeviceName = null;
    private String mConnectedDeviceMAC = null;
    //Paired devices
    private ArrayAdapter<String> pairedDevicesArrayAdapter;
    Button btButton;
    Button atackButton;

    static int setX;
    static int setY;

    //Debugging
    private final static String TAG = "BattleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate()");
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_battle_multi);

        Bundle ships = getIntent().getExtras();
        rows = ships.getIntArray("rzedy");
        cols = ships.getIntArray("kolumny");

        context = this;

        CompShips.array();
        CompShips.row();
        CompShips.col();
        loadResources();
        designMyBoardGame();
        designOpBoardGame();


        statki1 = (TextView) findViewById(R.id.statki1);
        mNick = ships.getString("nick");
        statki1.setText(mNick);
        Log.d(TAG, "Nick: " + mNick);

        statki2 = (TextView) findViewById(R.id.statki2);
        statki2.setText("Wait for opponent");



        atackButton = (Button) findViewById(R.id.atackButton);
        atackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCoordinates(setX, setY);
            }
        });


        //Bluetooth
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        multi = ships.getBoolean("multi");

        btButton = (Button) findViewById(R.id.bButton);
        btButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDevice();
            }
        });

        //Prevent locking screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

    }


    //***************************************************
    // GRA


    public void gameOver(){
        AlertDialog.Builder a_build = new AlertDialog.Builder(BattleMultiActivity.this);
        final Intent i = new Intent(this, StartActivity.class);
        if (status) {
            a_build.setMessage(R.string.wygrana)
                    .setCancelable(false)
                    .setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(i);
                        }
                    })
                    .setNegativeButton(R.string.nie, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog alert = a_build.create();
            alert.setTitle(R.string.koniec);
            alert.show();
        } else {
            a_build.setMessage(R.string.przegrana)
                    .setCancelable(false)
                    .setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(i);
                        }
                    })
                    .setNegativeButton(R.string.nie, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog alert = a_build.create();
            alert.setTitle(R.string.koniec);
            alert.show();

        }
    }



    //****************************************************************************
    //

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy()");

        if (mService != null){
            mService.stop();
        }

        gameOver();
    }

    @Override
    public void onResume() {
        super.onResume();
        CompShips.array();
        CompShips.row();
        CompShips.col();

        Log.d(TAG, "onResume()");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mService != null){
            // Only if the state is STATE_NONE, we know that we haven't started already
            if (mService.getState() == BluetoothConnectionService.STATE_NONE) {
                // Start the Bluetooth chat services
                mService.start();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart()");
        if (mAdapter == null) {
            //handle the case where device doesn't support Bluetooth
            Toast.makeText(this,"Bluetooth is not supported by your device",
                    Toast.LENGTH_SHORT).show();
            onBackPressed();
            this.finish();
        } else {
            //bluetooth supported
            ensureEnabled();
            //if BT enabled:
            //in onActivityResult - setupService()
            //else make a Toast
        }
    }

    //******************************************************
    //PLANSZE


    private void designOpBoardGame()  {

        int SizeOfCells = Math.round(ScreenWidth()/2/maxN);
        LinearLayout.LayoutParams lpRow = new LinearLayout.LayoutParams(SizeOfCells*maxN, SizeOfCells);
        LinearLayout.LayoutParams lpCell = new LinearLayout.LayoutParams(SizeOfCells, SizeOfCells);

        LinearLayout tablica = (LinearLayout) findViewById(R.id.tablica2);

        for(int i=0; i<maxN; i++){
            LinearLayout linRow = new LinearLayout(context);
            for(int j=0; j<maxN; j++) {
                opCells[i][j] = new ImageView(context);
                opCells[i][j].setBackground(drawCell[3]);
                linRow.addView(opCells[i][j], lpCell);
                final int x = i;
                final int y = j;
                opCells[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (opCells[x][y].getBackground() == drawCell[3] && myTurn == true) {
                            opCells[x][y].setBackground(drawCell[4]);
                            setX = x;
                            setY = y;

                        } else if (opCells[x][y].getBackground() == drawCell[4] && myTurn == true) {
                            opCells[x][y].setBackground(drawCell[3]);
                        }
                    }
                });
            }
            tablica.addView(linRow,lpRow);
        }
    }

    private void designMyBoardGame()  {

        int SizeOfCells = Math.round(ScreenWidth()/2/maxN);
        LinearLayout.LayoutParams lpRow = new LinearLayout.LayoutParams(SizeOfCells*maxN, SizeOfCells);
        LinearLayout.LayoutParams lpCell = new LinearLayout.LayoutParams(SizeOfCells, SizeOfCells);

        LinearLayout tablica = (LinearLayout) findViewById(R.id.tablica1);

        for(int i=0; i<maxN; i++){
            LinearLayout linRow = new LinearLayout(context);
            for(int j=0; j<maxN; j++) {
                myCells[i][j] = new ImageView(context);
                myCells[i][j].setBackground(drawCell[3]);
                linRow.addView(myCells[i][j], lpCell);

            }
            tablica.addView(linRow,lpRow);
        }
        for (int k = 0; k < 19; k++) {
            myCells[rows[k]][cols[k]].setBackground(drawCell[4]);
        }

    }

    //METODY DO STWORZENIA PLANSZY
    //KOLORKI KOMOREK
    public void loadResources() {
        drawCell[4] = context.getResources().getDrawable(R.drawable.statek);
        drawCell[3] = context.getResources().getDrawable(R.drawable.bloczek);
        drawCell[2] = context.getResources().getDrawable(R.drawable.zatopiony);
        drawCell[1] = context.getResources().getDrawable(R.drawable.trafiony);
        drawCell[0] = context.getResources().getDrawable(R.drawable.pudlo);
    }

    //OBLICZANIE SZEROKOSCI EKRANU
    public float ScreenWidth() {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();

        int a = (int) Math.round(0.9*dm.widthPixels);

        return a;
    }




    //*******************************************************
    //
    // Bluetooth
    //
    // ******************************************************

    private void ensureEnabled() {
        if (!mAdapter.isEnabled()) {
            Log.d(TAG, "Enable BT");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, Constants.REQUEST_ENABLE_BT);
        }
        else{
            if (mService == null) {setupService();}
        }
    }

    private void setupService() {
        Log.d(TAG, "setupService");
        mService = new BluetoothConnectionService(this, mHandler);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(mConnectedDeviceMAC);
                }
                break;
            case Constants.REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    if(mService == null){setupService();}
                    //and choose device to connect
                    chooseDevice();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void chooseDevice() {

        if (mService==null){mService = new BluetoothConnectionService(this, mHandler);}

        Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();
        pairedDevicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            pairedDevicesArrayAdapter.add(noDevices);
        }

        //create Alert Dialog

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select_device);
        builder.setCancelable(false);
        builder.setAdapter(pairedDevicesArrayAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                Log.d(TAG, "polaczInsecure(): " + pairedDevicesArrayAdapter.getItem(item).toString());

                // Cancel discovery because it's costly and we're about to connect
                mAdapter.cancelDiscovery();
                // Get the device MAC address, which is the last 17 chars in the View
                String info = pairedDevicesArrayAdapter.getItem(item).toString();
                mConnectedDeviceMAC = info.substring(info.length() - 17);
                Log.d(TAG,"Trying to connect to: " + mConnectedDeviceMAC);
                // Create the result Intent and include the MAC address
                connectDevice(mConnectedDeviceMAC);
            }
        });


        /*
        builder.setPositiveButton(R.string.button_scan,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing here because we override this button later to change the close behaviour.
                        //However, we still need this because on older versions of Android unless we
                        //pass a handler the button doesn't get instantiated
                    }
                }
        );
        */
        builder.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing here because we override this button later to change the close behaviour.
                        //However, we still need this because on older versions of Android unless we
                        //pass a handler the button doesn't get instantiated
                    }
                }
        );

        // Creating alert dialog
        mAlert = builder.create();
        //Showing alert dialog
        mAlert.show();

        /*
        mAlert.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        { @Override
        public void onClick(View v){
            doDiscovery();}
        });
        */
        mAlert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        { @Override
        public void onClick(View v){
            mAlert.dismiss();}
        });

    }

    private void connectDevice(String deviceMAC) {
        // Get the BluetoothDevice object
        if(mService!=null) {
            // Attempt to connect to the device
            Log.d(TAG, "connectDevice()" + mAdapter.getRemoteDevice(deviceMAC));
            mService.connect(mAdapter.getRemoteDevice(deviceMAC));
        } else{
            Log.d(TAG, "connectDevice - setupService()");
            setupService();
            mService.connect(mAdapter.getRemoteDevice(deviceMAC));
        }
    }

    public void AttackP_multi(int x, int y) {

        status = false;
        for (int i = 0; i < 19; i++) {
            if (x == rows[i] && y == cols[i]) {
                //trafiony
                status = true;
                myTurn = false;
                countG++;
            }
        }

        if (!status) {
            //if pudlo
            myCells[x][y].setBackground(drawCell[0]);
            mService.write( ("Bg:0").getBytes() );
            myTurn = true;
        }
        else {
            //if trafiony
            myCells[x][y].setBackground(drawCell[1]);
            mService.write( ("Bg:1").getBytes() );
        }

        if (countG == 19) {
            gameOver();
        }
    }

    public void checkMe_multi(){
        if(myCells[rows[0]][cols[0]].getBackground() == drawCell[1]){
            mService.write( ("Zt:"+Integer.toString(rows[0])+Integer.toString(cols[0])).getBytes());
            myCells[rows[0]][cols[0]].setBackground(drawCell[2]);
            Log.d(TAG, "jednomasztowiec");
        }
        else if(myCells[rows[1]][cols[1]].getBackground() == drawCell[1]){
            mService.write( ("Zt:"+Integer.toString(rows[1])+Integer.toString(cols[1])).getBytes());
            myCells[rows[1]][cols[1]].setBackground(drawCell[2]);
            Log.d(TAG, "jednomasztowiec");
        }

        else if(myCells[rows[2]][cols[2]].getBackground() == drawCell[1]){
            mService.write( ("Zt:"+Integer.toString(rows[2])+Integer.toString(cols[2])).getBytes());
            myCells[rows[2]][cols[2]].setBackground(drawCell[2]);
            Log.d(TAG, "jednomasztowiec");
        }

        else if(myCells[rows[3]][cols[3]].getBackground() == drawCell[1] && myCells[rows[4]][cols[4]].getBackground() == drawCell[1] ){
            writeSunk(rows[3],cols[3]);
            writeSunk(rows[4],cols[4]);
            myCells[rows[3]][cols[3]].setBackground(drawCell[2]);
            myCells[rows[4]][cols[4]].setBackground(drawCell[2]);
            Log.d(TAG, "dwumasztowiec");
        }

        else if(myCells[rows[5]][cols[5]].getBackground() == drawCell[1] && myCells[rows[6]][cols[6]].getBackground() == drawCell[1] ){
            writeSunk(rows[5],cols[5]);
            writeSunk(rows[6],cols[6]);
            myCells[rows[5]][cols[5]].setBackground(drawCell[2]);
            myCells[rows[6]][cols[6]].setBackground(drawCell[2]);
            Log.d(TAG, "dwumasztowiec");
        }

        else if(myCells[rows[7]][cols[7]].getBackground() == drawCell[1] && myCells[rows[8]][cols[8]].getBackground() == drawCell[1] ){
            writeSunk(rows[7],cols[7]);
            writeSunk(rows[8],cols[8]);
            myCells[rows[7]][cols[7]].setBackground(drawCell[2]);
            myCells[rows[8]][cols[8]].setBackground(drawCell[2]);
            Log.d(TAG, "dwumasztowiec");
        }

        else if(myCells[rows[9]][cols[9]].getBackground() == drawCell[1] && myCells[rows[10]][cols[10]].getBackground() == drawCell[1]
                && myCells[rows[11]][cols[11]].getBackground() == drawCell[1]){
            writeSunk(rows[9],cols[9]);
            writeSunk(rows[10],cols[10]);
            writeSunk(rows[11],cols[11]);
            myCells[rows[9]][cols[9]].setBackground(drawCell[2]);
            myCells[rows[10]][cols[10]].setBackground(drawCell[2]);
            myCells[rows[11]][cols[11]].setBackground(drawCell[2]);
            Log.d(TAG, "trzymasztowiec");
        }
        else if(myCells[rows[12]][cols[12]].getBackground() == drawCell[1] && myCells[rows[13]][cols[13]].getBackground() == drawCell[1]
                && myCells[rows[14]][cols[14]].getBackground() == drawCell[1]){
            writeSunk(rows[12],cols[12]);
            writeSunk(rows[13],cols[13]);
            writeSunk(rows[14],cols[14]);
            myCells[rows[12]][cols[12]].setBackground(drawCell[2]);
            myCells[rows[13]][cols[13]].setBackground(drawCell[2]);
            myCells[rows[14]][cols[14]].setBackground(drawCell[2]);
            Log.d(TAG, "trzymasztowiec");
        }
        else if(myCells[rows[15]][cols[15]].getBackground() == drawCell[1] && myCells[rows[16]][cols[16]].getBackground() == drawCell[1]
                && myCells[rows[17]][cols[17]].getBackground() == drawCell[1] && myCells[rows[18]][cols[18]].getBackground() == drawCell[1]){
            writeSunk(rows[15],cols[15]);
            writeSunk(rows[16],cols[16]);
            writeSunk(rows[17],cols[17]);
            writeSunk(rows[18],cols[18]);
            myCells[rows[15]][cols[15]].setBackground(drawCell[2]);
            myCells[rows[16]][cols[16]].setBackground(drawCell[2]);
            myCells[rows[17]][cols[17]].setBackground(drawCell[2]);
            myCells[rows[18]][cols[18]].setBackground(drawCell[2]);
            Log.d(TAG, "czteromasztowiec");
        }

    }

    private void writeCoordinates(int x, int y) {
        if (mService != null){
            String coordinates = "Co:" + Integer.toString(x) + Integer.toString(y);
            mService.write(coordinates.getBytes());
            Log.d(TAG, "writeCoordinates() " + coordinates);
        }
        else{
            Toast.makeText(getApplication(), "Connect to device first ", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeSunk(int x, int y){
        String coordinates = "Zt:" + Integer.toString(x) + Integer.toString(y);
        mService.write(coordinates.getBytes());
    }

    private void doStuffWithReadedMssg(String readMessage) {
        Log.d(TAG,readMessage);
        switch (readMessage.substring(0,3)){
            case "Op:":
                //when U get mssg with opponent's nick
                mOpponentNick = readMessage.substring(3);
                statki2.setText(mOpponentNick);
                if (mOpponentNick.length() > mNick.length()) {
                    myTurn = true;
                }else {myTurn = false;}
                break;
            case "Co:":
                //when U get mssg with coordinatest to check
                int x = Integer.valueOf(readMessage.substring(3,4));
                int y = Integer.valueOf(readMessage.substring(4,5));
                AttackP_multi(x,y);
                checkMe_multi();
                break;
            case "Bg:":
                //when U get info about bg color
                int bg = Integer.valueOf(readMessage.substring(3,4));
                Log.d(TAG,"Bg: " + bg);
                if (bg==0){
                    opCells[setX][setY].setBackground(drawCell[0]);
                    myTurn = false;
                } else {
                    opCells[setX][setY].setBackground(drawCell[1]);
                }
                break;
            case "Zt:":
                //when U get info that ship is "zatopiony"
                int xx = Integer.valueOf(readMessage.substring(3,4));
                int yy = Integer.valueOf(readMessage.substring(4,5));
                Log.d(TAG,"Zt: " + xx + yy);
                opCells[xx][yy].setBackground(drawCell[2]);
                break;
        }
    }

    //Handler for communication with service
    //Handler for communication with service
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Application activity = getApplication();
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case Constants.STATE_CONNECTED:
                            Log.d(TAG, "STATE_CONNECTED");
                            mAdapter.cancelDiscovery();
                            break;
                        case Constants.STATE_CONNECTING:
                            Log.d(TAG, "STATE_CONNECTING");
                            break;
                        case Constants.STATE_LISTEN:
                            Log.d(TAG, "STATE_LISTEN");
                            break;
                        case Constants.STATE_NONE:
                            Log.d(TAG, "STATE_NONE");
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    Log.d(TAG, "MESSAGE_WRITE");
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    Log.d(TAG, "MESSAGE_READ");
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    doStuffWithReadedMssg(readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    Log.d(TAG, "MESSAGE_DEVICE_NAME");
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    mService.write(("Op:" +mNick).getBytes());
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
