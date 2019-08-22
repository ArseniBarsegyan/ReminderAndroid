package com.arsbars.reminderandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arsbars.reminderandroid.view.fragments.LoginFragment;
import com.arsbars.reminderandroid.view.fragments.NotesFragment;
import com.arsbars.reminderandroid.view.fragments.SettingsFragment;
import com.arsbars.reminderandroid.services.FragmentNavigationService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentNavigationService {
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // navigateToRoot(getString(R.string.notes), NotesFragment.newInstance());
        navigateToRoot(getString(R.string.login_title), LoginFragment.newInstance());
    }

    @Override
    public void navigateToRoot(String title, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ConstraintLayout rootLayout = (ConstraintLayout)findViewById(R.id.root_layout);
        if (rootLayout.getChildCount() == 0) {
            fragmentTransaction.add(R.id.root_layout, fragment);
        } else {
            fragmentTransaction.replace(R.id.root_layout, fragment);
        }
        fragmentTransaction.commit();

        toolbar.setTitle(title);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle.setDrawerIndicatorEnabled(true);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void push(String title, Fragment fragment) {
        toolbar.setTitle(title);

        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.root_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            navigateToRoot(getString(R.string.notes), NotesFragment.newInstance());
        } else if (id == R.id.nav_reminders) {
            Toast.makeText(this,getString(R.string.reminders), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_achievements) {
            Toast.makeText(this,getString(R.string.achievements), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_settings) {
            navigateToRoot(getResources().getString(R.string.settings), SettingsFragment.newInstance());
        } else if (id == R.id.nav_exit) {
            Toast.makeText(this,getString(R.string.logout), Toast.LENGTH_SHORT).show();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
