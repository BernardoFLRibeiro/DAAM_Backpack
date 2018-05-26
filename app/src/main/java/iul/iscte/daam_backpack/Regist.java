package iul.iscte.daam_backpack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Regist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
    }

    public void goToAccountActivity(View view){
        Intent intent = new Intent(Regist.this, HomePage.class);
        startActivity(intent);
    }
}
