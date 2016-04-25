package com.example.sccn.taskfinal.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.example.sccn.taskfinal.R;
import com.example.sccn.taskfinal.activity.functional.AddEquation;
import com.example.sccn.taskfinal.activity.functional.ChemicalPeriodicList;
import com.example.sccn.taskfinal.activity.functional.SeachEquation;



public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    Animation animRotate;
    Animation animRotateIn_icon;
    Animation animRotateIn_icon2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        animRotate = AnimationUtils.loadAnimation(this, R.anim.test);
        animRotateIn_icon = AnimationUtils.loadAnimation(this, R.anim.left_in);
        animRotateIn_icon2 = AnimationUtils.loadAnimation(this, R.anim.right_in);

        getSupportActionBar().hide();
        setThemes();
        setActionButton();
    }

    private void setThemes() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void setActionButton() {
        //set seach
        final ImageButton buttonSeach = (ImageButton) findViewById(R.id.seachbutton);
        final ImageButton buttonList = (ImageButton) findViewById(R.id.periodicButton);
        final ImageButton buttonAdd = (ImageButton) findViewById(R.id.imageButton);
        final ImageButton buttonTest = (ImageButton) findViewById(R.id.imageButton2);
        final ImageButton buttonSetting = (ImageButton) findViewById(R.id.imageButton3);
        final ImageButton buttonUpdate = (ImageButton) findViewById(R.id.imageButton4);
        final ImageButton buttonAbout = (ImageButton) findViewById(R.id.imageButton5);
        final ImageButton buttonExit = (ImageButton) findViewById(R.id.imageButton6);

        buttonSeach.startAnimation(animRotateIn_icon);
        buttonAdd.startAnimation(animRotateIn_icon);
        buttonSetting.startAnimation(animRotateIn_icon);
        buttonAbout.startAnimation(animRotateIn_icon);

        buttonList.startAnimation(animRotateIn_icon2);
        buttonTest.startAnimation(animRotateIn_icon2);
        buttonUpdate.startAnimation(animRotateIn_icon2);
        buttonExit.startAnimation(animRotateIn_icon2);

        buttonSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animRotate);
                Intent next = new Intent(MainActivity.this, SeachEquation.class);
                next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
                //overridePendingTransition(R.anim.right_in, R.anim.left_out);
                overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
            }
        });

        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChemicalPeriodicList.class));
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animRotate);
                Intent next = new Intent(MainActivity.this, AddEquation.class);
                next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
                //overridePendingTransition(R.anim.right_in, R.anim.left_out);
                overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animRotate);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });
    }

    //set back
    public void onBackPressed() {

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Tra cứu phương trình";
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
