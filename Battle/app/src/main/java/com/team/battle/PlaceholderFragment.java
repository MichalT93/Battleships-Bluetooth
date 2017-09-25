package com.team.battle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    static ChooseGameActivity oo;

    private static final String ARG_SECTION_NUMBER = "section_number";

    //Debugging
    private static final String TAG = "PlaceholderFragment";

    //Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";



    public PlaceholderFragment() {}

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, ChooseGameActivity o) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        oo = o;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_choose_game, container, false);
        Button but = (Button) rootView.findViewById(R.id.button);
        but.setHeight(100);

        if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
            but.setText(R.string.players1);

            View.OnClickListener l1 = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reakcja1(oo);
                }
            };
            but.setOnClickListener(l1);
        } else {

            but.setText(R.string.players2);

            View.OnClickListener l2 = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reakcja2(oo);
                }
            };
            but.setOnClickListener(l2);
        }
        //fresh();

        return rootView;
    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_choose_game, menu);
    }

    public void reakcja1(ChooseGameActivity o) {
        Intent i = new Intent(o, Prepare.class);
        i.putExtra("nick", "Ty");
        i.putExtra("multi", false);
        startActivity(i);
        o.finish();
    }

    public void reakcja2(final ChooseGameActivity o) {
        final Intent i = new Intent(o, Prepare.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(o);
        builder.setTitle(R.string.nicks);
// Set up the input
        final EditText input = new EditText(o);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();

                i.putExtra(Constants.NICK, m_Text);
                i.putExtra("multi", true);
                startActivity(i);
                o.finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }




    /*@Override
    public void onResume(){
        super.onResume();
        fresh();
    }*/

    /*public void fresh() {
        SharedPreferences settings = getContext().getApplicationContext().getSharedPreferences("mysettings",
                Context.MODE_PRIVATE);
        Boolean night = settings.getBoolean("night", false);

        android.support.percent.PercentRelativeLayout rl = (android.support.percent.PercentRelativeLayout) getActivity().findViewById(R.id.main_content);

        if (!night) {
            rl.setBackgroundColor(ContextCompat.getColor(getContext().getApplicationContext(), R.color.white));
        } else {
            rl.setBackgroundColor(ContextCompat.getColor(getContext().getApplicationContext(), R.color.backNight));
        }
    }*/
}