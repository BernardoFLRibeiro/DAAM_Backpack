package iul.iscte.daam_backpack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomePage extends MenuPage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().setTitle("Pagina Principal");
        createListen();
        setupDrawer();

    }
}
