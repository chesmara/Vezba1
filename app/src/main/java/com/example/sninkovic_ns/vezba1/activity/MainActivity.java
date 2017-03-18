package com.example.sninkovic_ns.vezba1.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sninkovic_ns.vezba1.R;


import com.example.sninkovic_ns.vezba1.adapters.DrawerAdapter;
import com.example.sninkovic_ns.vezba1.async.SimpleService;
import com.example.sninkovic_ns.vezba1.db.model.Glumac;
import com.example.sninkovic_ns.vezba1.db.DatabaseHelper;
import com.example.sninkovic_ns.vezba1.dialogs.AboutDialog;
import com.example.sninkovic_ns.vezba1.model.NavigationItem;
import com.example.sninkovic_ns.vezba1.preferences.Preferences;
import com.example.sninkovic_ns.vezba1.tools.ReviewerTools;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    }




    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private RelativeLayout drawerPane;
    private CharSequence drawerTitle;
    private ArrayList<NavigationItem> drawerItems = new ArrayList<NavigationItem>();

    private AlertDialog dialog;
    private AlertDialog kdialog;
    private int itemId = 0;

    private DatabaseHelper databaseHelper;
    public static String ACTOR_KEY = "ACTOR_KEY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // refresh1();


        drawerItems.add(new NavigationItem(getString(R.string.drawer_home), getString(R.string.drawer_home_long), R.drawable.ic_action_create));
        drawerItems.add(new NavigationItem(getString(R.string.drawer_settings), getString(R.string.drawer_settings_long), R.drawable.ic_action_update));
        drawerItems.add(new NavigationItem(getString(R.string.drawer_about), getString(R.string.drawer_about_long), R.drawable.ic_action_delete));
        //drawerItems.add(new NavigationItem("Kontakt", "Podaci o udruženju", R.drawable.ic_action_contact));
        //drawerItems.add(new NavigationItem("O autoru", "Ko je tvorac svega spomenutog", R.drawable.ic_action_autor));


        drawerTitle = getTitle();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.navList);

        // Populates NavigtionDrawer with options
        drawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        DrawerAdapter adapter = new DrawerAdapter(this, drawerItems);

        // Sets a custom shadow that overlays the main content when NavigationDrawer opens
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerList.setAdapter(adapter);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                   setSupportActionBar(toolbar);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }

        final ListView listView = (ListView) findViewById(R.id.listaGlumaca);

        try {
            List<Glumac> list = getDatabaseHelper().getmGlumacDao().queryForAll();

            ListAdapter gadapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, list);
            listView.setAdapter(gadapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Glumac p = (Glumac) listView.getItemAtPosition(position);

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra(ACTOR_KEY, p.getId());
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        drawerToggle = new ActionBarDrawerToggle(
                this,                           /* host Activity */
                drawerLayout,                   /* DrawerLayout object */
                toolbar,                        /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,           /* "open drawer" description for accessibility */
                R.string.drawer_close           /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();        // Creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();        // Creates call to onPrepareOptionsMenu()
                drawerList.bringToFront();
                drawerLayout.requestLayout();
            }
        };
        //refresh();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // onOptionsItemSelected method is called whenever an item in the Toolbar is selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                //addItem();
                int status= ReviewerTools.getConnectivityStatus(getApplicationContext());
                Toast.makeText(this, "Provera vrste internet konekcije pokrenuta", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this, SimpleService.class);
                intent.putExtra("vrstaNeta", status);
                startService(intent);

                break;
            case R.id.action_update:
                //refresh ();
                Toast.makeText(this, "Podesavanja!!", Toast.LENGTH_SHORT).show();
                Intent podesavanja=new Intent(MainActivity.this, Preferences.class);
                startActivity(podesavanja);
                break;
            case R.id.action_delete:
                Toast.makeText(this, "Action " + getString(R.string.fragment_detal_action_delete) + " executed.", Toast.LENGTH_SHORT).show();
                if(dialog == null){
                    dialog =new AboutDialog(MainActivity.this).prepareDialog();
                    dialog.show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    // onConfigurationChanged method is called when the device configuration changes to pass configuration change to the drawer toggle
    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);

        // Pass any configuration change to the drawer toggle
        drawerToggle.onConfigurationChanged(configuration);
    }



    public void addItem( )  {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);


    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    //----------------------------------Simin za assignment 25----------------------------

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.listaGlumaca);

        if (listview != null){
            ArrayAdapter<Glumac> adapter = (ArrayAdapter<Glumac>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Glumac> list = getDatabaseHelper().getmGlumacDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    private void selectItemFromDrawer(int position) {


       if(position == 0) {

           Toast toast = Toast.makeText(getBaseContext(), "Prva opcija izabrana", Toast.LENGTH_SHORT);
           toast.show();
           Intent i = new Intent(MainActivity.this, SecondActivity.class);
           startActivity(i);
       }
            else if(position==1) {
           Toast toast1 = Toast.makeText(getBaseContext(), "Druga opcija izabrana", Toast.LENGTH_SHORT);
           toast1.show();

           Intent a = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ekoneimar.com/fleksibilni-smestaj/"));
           startActivity(a);
       }
            else if(position == 2) {

           Toast.makeText(getBaseContext(), "Treca opcija izabrana", Toast.LENGTH_SHORT).show();


       }

              //  dialog.show();





        drawerList.setItemChecked(position, true);
        setTitle(drawerItems.get(position).getTitle());
        drawerLayout.closeDrawer(drawerPane);

    }

    }
