package com.team.battle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

public class ChooseGameActivity extends BasicActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayShowHomeEnabled(false);
        //getActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_choose_game);
        fresh();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        /*Button but = (Button) findViewById(R.id.button);
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reakcja2();
            }
        };
        but.setOnClickListener(l);*/

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        ChooseGameActivity o;

        public SectionsPagerAdapter(FragmentManager fm, ChooseGameActivity o) {
            super(fm);
            this.o = o;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, o);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
            }
            return null;
        }
    }

    public void fresh() {
        SharedPreferences settings = getSharedPreferences("mysettings",
                Context.MODE_PRIVATE);
        Boolean night = settings.getBoolean("night", false);

        android.support.design.widget.CoordinatorLayout rl = (android.support.design.widget.CoordinatorLayout) findViewById(R.id.main_content);

        if (!night) {
            rl.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        } else {
            rl.setBackgroundColor(ContextCompat.getColor(this, R.color.backNight));
        }
    }
}
