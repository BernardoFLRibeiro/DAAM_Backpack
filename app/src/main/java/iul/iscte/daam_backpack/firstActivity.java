package iul.iscte.daam_backpack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class firstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void goToLogIn(View view){
        Intent intent = new Intent(firstActivity.this, Login.class);
        startActivity(intent);
    }

    public void goToRegist(View view){
        Intent intent = new Intent(firstActivity.this, Regist.class);
        startActivity(intent);
    }

}
