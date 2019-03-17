package com.arsbars.reminderandroid;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.arsbars.reminderandroid.data.Note;
import com.arsbars.reminderandroid.data.NotesDbHelper;
import com.arsbars.reminderandroid.view.NotesViewModel;
import com.arsbars.reminderandroid.view.factory.NotesViewModelFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NotesAdapter notesAdapter;
    private NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView notesListView = findViewById(R.id.listView);

        notesViewModel = ViewModelProviders
                .of(this, new NotesViewModelFactory(new NotesDbHelper(getApplicationContext())))
                .get(NotesViewModel.class);
        notesViewModel = new NotesViewModel(new NotesDbHelper(getApplicationContext()));

        List<Note> favourites = notesViewModel.getNotes();
        notesAdapter = new NotesAdapter(this, R.layout.list_item_row, favourites);
        notesListView.setAdapter(notesAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            // Handle the camera action
        } else if (id == R.id.nav_todo) {

        } else if (id == R.id.nav_birthdays) {

        } else if (id == R.id.nav_achievements) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_exit) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class NotesAdapter extends ArrayAdapter<Note> {
        private class ViewHolder {
            TextView description;
            TextView editDate;
        }

        public NotesAdapter(Context context, int layoutResourceId, List<Note> todos) {
            super(context, layoutResourceId, todos);
        }

        @Override
        @NonNull
        public View getView(int position, View convertView, ViewGroup parent) {
            Note note = getItem(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_item_row, parent, false);
                viewHolder.description = convertView.findViewById(R.id.noteDescription);
                viewHolder.editDate = convertView.findViewById(R.id.noteEditDate);
                convertView.setTag(R.id.VH, viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag(R.id.VH);
            }

            if (note != null) {
                viewHolder.description.setText(note.getDescription());
                viewHolder.editDate.setText((note.getEditDate()).toString());
            }
            convertView.setTag(R.id.POS, position);
            return convertView;
        }

    }
}
