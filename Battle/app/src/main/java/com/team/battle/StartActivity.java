package com.team.battle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StartActivity extends BasicActivity {

    Button b1, b2, b3;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        SharedPreferences settings = getSharedPreferences("mysettings",
                Context.MODE_PRIVATE);
        Boolean langs = settings.getBoolean("langs", true);  //true - English
        Language lang;
        Locale locale;
        if (!langs) {
            locale = new Locale("pl");
            lang = Language.PL;
        } else {
            locale = new Locale("en");
            lang = Language.EN;
        }
        if (!Locale.getDefault().equals(locale)) {
            Locale.setDefault(locale);
            Resources res = getResources();
            Configuration config = res.getConfiguration();
            config.setLocale(locale);
            res.updateConfiguration(config, res.getDisplayMetrics());
            BasicActivity.appLocale = lang;
        }
        fresh();

        b1 = (Button) findViewById(R.id.button1);
        b1.setText(R.string.play);
        View.OnClickListener l1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reakcja1();
            }
        };
        b1.setOnClickListener(l1);

        b2 = (Button) findViewById(R.id.button2);
        b2.setText(R.string.opt);
        View.OnClickListener l2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reakcja2();
            }
        };
        b2.setOnClickListener(l2);

        b3 = (Button) findViewById(R.id.button3);
        b3.setText(R.string.exit);
        View.OnClickListener l3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reakcja3();
            }
        };
        b3.setOnClickListener(l3);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        View.OnClickListener lf = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reakcja4();
            }
        };
        fab.setOnClickListener(lf);
    }

    public void reakcja1() {
        Intent i = new Intent(this, ChooseGameActivity.class);
        startActivity(i);
        this.finish();
    }

    public void reakcja2() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    public void reakcja3() {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        final AlertDialog ad = dlgAlert.create();
        dlgAlert.setMessage(R.string.reallyEnd);
        dlgAlert.setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                StartActivity.this.finish();
            }
        });
        dlgAlert.setNegativeButton(R.string.nie, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ad.cancel();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.show();
    }

    public void reakcja4() {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        final AlertDialog ad = dlgAlert.create();
        dlgAlert.setMessage(R.string.about);
        dlgAlert.setPositiveButton(R.string.okejka, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ad.cancel();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        fresh();
    }

    public void fresh() {
        SharedPreferences settings = getSharedPreferences("mysettings",
                Context.MODE_PRIVATE);
        Boolean night = settings.getBoolean("night", false);

        android.support.percent.PercentRelativeLayout rl = (android.support.percent.PercentRelativeLayout) findViewById(R.id.startLayout);

        if (!night) {
            rl.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
        } else {
            rl.setBackgroundColor(ContextCompat.getColor(this, R.color.backNight));
        }
    }
}
