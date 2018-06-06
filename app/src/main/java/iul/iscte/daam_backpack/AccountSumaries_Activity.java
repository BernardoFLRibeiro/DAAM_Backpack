package iul.iscte.daam_backpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccountSumaries_Activity extends MenuPage {


    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference().child("Resumos");

    private ArrayList<Resumo> listaResumos;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ListAnexoAdapter mAnexoListAdapter;

    private FirebaseUser user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_sumaries_);
        getSupportActionBar().setTitle("Meus Resumos");

        layoutManager = new LinearLayoutManager(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.listResumos);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        listaResumos = new ArrayList<Resumo>();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        showResumos();

        createListen();
        setupDrawer();
    }

    public void createNewResumo(View view){
        Intent intent = new Intent(AccountSumaries_Activity.this, AnexarFicheiro_Activity.class);
        startActivity(intent);
    }

    public void showResumos(){


        ValueEventListener postListener = new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaResumos = new ArrayList<Resumo>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Resumo resumo = postSnapshot.getValue(Resumo.class);
                    if (resumo.getUserId().equals(user.getUid())){
                        listaResumos.add(resumo);
                    }
                }

                mAnexoListAdapter = new ListAnexoAdapter(listaResumos);
                mRecyclerView.setAdapter(mAnexoListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AccountSumaries_Activity.this,"Ocorreu um erro!", Toast.LENGTH_SHORT).show();
            }
        };

        myRef.addValueEventListener(postListener);

    }

}
