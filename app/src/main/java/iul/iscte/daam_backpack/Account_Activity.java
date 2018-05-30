package iul.iscte.daam_backpack;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Account_Activity extends MenuPage {

    private TextView name, email2, university;
    private FirebaseDatabase db;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_);
        getSupportActionBar().setTitle("A Minha Conta");
        createListen();
        setupDrawer();

        name = findViewById(R.id.nameTV);
        email2 = findViewById(R.id.emailTV);
        university = findViewById(R.id.univerityTV);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        ref.child("users").orderByChild("email").addValueEventListener(new ValueEventListener() {
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Utilizador u = ds.getValue(Utilizador.class);
                    if(u.getEmail().equals(email)){
                        Log.d("tag email", u.getEmail());
                        Log.d("tag name", u.getNome());
                        Log.d("tag university", u.getUniversity());
                        name.setText(u.getNome());
                        email2.setText(u.getEmail());
                        university.setText(u.getUniversity());

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}