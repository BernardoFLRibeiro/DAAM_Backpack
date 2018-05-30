package iul.iscte.daam_backpack;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


public class MenuPage extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

    }

    public void createListen() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        dl.addDrawerListener(t);
        nv = (NavigationView) findViewById(R.id.nv_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        position = 0;
                        break;
                    case R.id.nav_account:
                        position = 1;
                        break;

                    case R.id.nav_accountSummaries:
                        position = 2;
                        break;

                    case R.id.nav_accountGroups:
                        position = 3;
                        break;

                    case R.id.nav_settings:
                        position = 4;
                        break;

                    case R.id.nav_logout:
                        position = 5;
                        break;
                }
                if (!(position == -1)) {
                    selectItem();
                }
                return false;
            }
        });

    }

    public void selectItem() {

        switch (position) {
            case 0:
                startActivity(new Intent(getApplicationContext(), HomePage.class));
                break;
            case 1:
                startActivity(new Intent(getApplicationContext(), Account_Activity.class));
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(), AccountSumaries_Activity.class));
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), AccountGroups_Activity.class));
                break;
            case 4:
                startActivity(new Intent(getApplicationContext(), Settings_Activity.class));
                break;
            case 5:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
        }
    }


    public void setupDrawer() {
        t = new ActionBarDrawerToggle(this, dl, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        t.setDrawerIndicatorEnabled(true);
        //dl.setDrawerListener(t);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        t.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        t.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (t.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }

}
