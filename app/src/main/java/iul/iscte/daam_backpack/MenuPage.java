package iul.iscte.daam_backpack;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MenuPage extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
       // t = new ActionBarDrawerToggle(this,dl,1,0);

        dl.addDrawerListener(t);
      //  t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.nav_account:
                        Toast.makeText(MenuPage.this, "My Account",Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.nav_settings:
                        Toast.makeText(MenuPage.this, "Settings",Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.nav_logout:
                        Toast.makeText(MenuPage.this, "LogOut",Toast.LENGTH_SHORT).show();
                        return true;

                    default:
                        return true;
                }


    }
            });
    }
}
