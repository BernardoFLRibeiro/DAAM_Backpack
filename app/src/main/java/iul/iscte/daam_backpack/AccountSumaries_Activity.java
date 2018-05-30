package iul.iscte.daam_backpack;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

public class AccountSumaries_Activity extends MenuPage {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private int position = 0;

    //private FirebaseAuth auth = FirebaseAuth.getInstance();
    //private FirebaseUser user = auth.getCurrentUser();
    //private String currentUser = user.getUid();

    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference().child("Resumos");

    private ArrayList<Resumo> listaResumos;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ListAnexoAdapter mAnexoListAdapter;

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


        showResumos();

        createListen();
        setupDrawer();
    }

    public void createNewResumo(View view){
        Intent intent = new Intent(AccountSumaries_Activity.this, AnexarFicheiro_Activity.class);
        startActivity(intent);
    }

    public void showResumos(){

        //myRef.orderByChild("creator").equalTo(currentUser).addValueEventListener(new ValueEventListener() {

        ValueEventListener postListener = new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Resumo resumo = postSnapshot.getValue(Resumo.class);
                    if (resumo.getCadeira().equals("TSIO")){
                        listaResumos.add(resumo);
                    }
                }

                mAnexoListAdapter = new ListAnexoAdapter(listaResumos);
                mRecyclerView.setAdapter(mAnexoListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AccountSumaries_Activity.this,"Ocorreu um erro testeeee!", Toast.LENGTH_SHORT).show();
            }
        };

        myRef.addValueEventListener(postListener);

    }

}
