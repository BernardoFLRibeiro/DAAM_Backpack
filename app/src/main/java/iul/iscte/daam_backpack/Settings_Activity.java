package iul.iscte.daam_backpack;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Settings_Activity extends MenuPage {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private int position = 0;
    private String resultpop;
    private String[] listItems;

    private boolean b = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);
        fill();
        getSupportActionBar().setTitle("Definições");
        createListen();
        setupDrawer();
    }

    private void fill() {
        listItems = new String[]{
                "Nome",
                "Curso",
                "Universidade",
                "Password"
        };
        List<String> settings_list = new ArrayList<String>(Arrays.asList(listItems));
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        ListView mListView = (ListView) findViewById(R.id.lv_view);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                changeValues(selectedItem);
            }
        });
    }

    private void changeValues(String info) {
        final String temp = info;
        changeInformation(info);

 /*       if (temp.equals("Password")) {
            checkPassword();
        } else {
            changeInformation(info);
        }*/
    }

    private void changeInformation(String info) {
        final String typeToChange = getType(info);
        makeAlertDialog();
    }

    private void makeAlertDialog() {

        View promptsView = LayoutInflater.from(this).inflate(R.layout.popup_settings, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.popET);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                resultpop = String.valueOf((userInput.getText()));
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private String getType(String info) {
        String result = "";
        switch (info) {
            case "Nome":
                result = "nome";
                break;
            case "Curso":
                result = "course";
                break;
            case "Universidade":
                result = "university";
                break;
            case "Password":
                result = "password";
                break;
        }
        return result;
    }


}