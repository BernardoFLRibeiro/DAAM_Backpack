package iul.iscte.daam_backpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class AccountSumaries_Activity extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_sumaries_);

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        dl.addDrawerListener(t);
        nv = (NavigationView) findViewById(R.id.nv_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
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
                if (!(position == 0)) {
                    selectItem();
                }
                return false;
            }
        });

    }

    public void funcionaCaralho(View view){
        Intent myIntent = new Intent(AccountSumaries_Activity.this, Search.class);
        startActivity(myIntent);
    }

    private void selectItem() {

        switch (position) {
            case 1:
                startActivity( new Intent(getApplicationContext(), Account_Activity.class));
                break;
            case 2:
                startActivity( new Intent(getApplicationContext(), AccountSumaries_Activity.class));
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), AccountGroups_Activity.class));
                break;
            case 4:
                startActivity(new Intent(getApplicationContext(), Settings_Activity.class));
                break;
        }


    }
}
