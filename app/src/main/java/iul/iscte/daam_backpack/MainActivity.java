package iul.iscte.daam_backpack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToAnexar(View view){
        Intent intent = new Intent(MainActivity.this, AnexarFicheiro.class);
        startActivity(intent);
    }
}
