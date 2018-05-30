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

import java.io.Serializable;
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

    private FirebaseDatabase db;
    private DatabaseReference ref;

    private String name, email, university,course;
    private Utilizador utilizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);
        getSupportActionBar().setTitle("Definições");
        createListen();
        setupDrawer();
        setupList();
    }

    private void setupList() {
        listItems = new String[]{
                "Nome",
                "Curso",
                "Universidade",
                "Password"
        };
      //  List<String> settings_list = new ArrayList<String>(Arrays.asList(listItems));
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        ListView mListView = (ListView) findViewById(R.id.lv_view);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                changeInformation(selectedItem);
            }
        });
    }


    private void changeInformation(String itemClicked) {
        String typeToChange = setType(itemClicked); //transforma o nome do que se quer mudar no nome que esta na database
        String result = getData(typeToChange); //vai buscar o valor atual do campo que se quer alterar a database do Firebase
        TextView tv = (TextView) findViewById(R.id.changeTV);
        tv.append("-"+result);// mostra esse campo no TextView
        // makeAlertDialog();
    }

    private String setType(String info) {
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

    private String getData(String typeToChange) {
        String result = "";
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        ref.child("users").orderByChild("email").addValueEventListener(new ValueEventListener() {
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Utilizador u = ds.getValue(Utilizador.class);
                    if (u.getEmail().equals(email)) {
                        Log.d("tag email", u.getEmail());
                        Log.d("tag name", u.getNome());
                        Log.d("tag university", u.getUniversity());
                        name = (u.getNome());
                        email = (u.getEmail());
                        university = (u.getUniversity());
                        course = (u.getCourse());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        switch (typeToChange) {
            case "nome":
                result = name;
                break;
            case "course":
                result = course;
                break;
            case "university":
                result = university;
                break;
            case "password":
                result = "";
                break;
        }
        return result;
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


}