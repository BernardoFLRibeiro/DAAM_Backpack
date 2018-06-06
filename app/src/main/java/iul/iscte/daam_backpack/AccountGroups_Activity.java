package iul.iscte.daam_backpack;

import android.os.Bundle;

public class AccountGroups_Activity extends MenuPage {


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_groups_);
        getSupportActionBar().setTitle("Os meus Grupos");
        createListen();
        setupDrawer();


    }
}
