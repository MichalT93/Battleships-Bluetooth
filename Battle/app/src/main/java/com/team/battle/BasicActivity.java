package com.team.battle;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import java.util.Locale;

/**
 * Helper class.
 * appLocale is static, so it will retain it's value for all instances.
 * currentLocale is individual for all instances, so we can detect if locale was changed.
 */

public class BasicActivity extends AppCompatActivity {

    enum Language {EN, PL}

    public static Language appLocale = Language.EN;
    protected Language currentLocale = null;

    protected void setUpLocale(Language lang) {
        if (lang == null) {
            lang = appLocale; // When null is provided, set locale to current appLocale.
        }
        Locale loc = null;
        if (!appLocale.equals(lang) || currentLocale == null) {
            switch (lang) {
                case EN:
                    loc = new Locale("en");
                    break;
                case PL:
                    loc = new Locale("pl");
                    break;
            }
        }

        Locale.setDefault(loc);
        Resources res = getApplicationContext().getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(loc);
        res.updateConfiguration(config, res.getDisplayMetrics());
        appLocale = lang;
        currentLocale = lang;
    }

    protected void recreateIfLocaleChanged() {
        if (!currentLocale.equals(appLocale)) {
            currentLocale = appLocale;
            recreate();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLocale(null); // Get Locale
    }

    @Override
    protected void onResume() {
        super.onResume();
        recreateIfLocaleChanged();
    }
}
