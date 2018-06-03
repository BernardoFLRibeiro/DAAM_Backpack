package iul.iscte.daam_backpack;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.Serializable;


public class MenuPage extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

    }

    public void createListen() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        dl.addDrawerListener(t);
        nv = (NavigationView) findViewById(R.id.nv_view);

        LayoutInflater inflater = getLayoutInflater();

        View listHeaderView = inflater.inflate(R.layout.header_layout, null, false);

        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

        String emailUser = email.replace(".", "").replace("@", "");
        String fileName = emailUser + "_0";
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("ImagemPerfil").child(fileName);


        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageView imageView = (ImageView) findViewById(R.id.nv_image);

                final TextView tvemail = (TextView) findViewById(R.id.header_email);
                if (tvemail != null) {
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference ref = db.getReference();
                    ref.child("users").orderByChild("email").addListenerForSingleValueEvent(new ValueEventListener() {
                        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Utilizador u = ds.getValue(Utilizador.class);

                                String nome = u.getNome();
                                tvemail.setText(nome);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                    tvemail.setText(email);
                }
                if (imageView != null) {
                    Picasso.get().load(uri)
                            .resize(imageView.getWidth(), imageView.getHeight())
                            .into(imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError(Exception e) {
                                }
                            });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        nv.addHeaderView(listHeaderView);


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        position = 0;
                        break;
                    case R.id.nav_account:
                        position = 1;
                        break;

                    case R.id.nav_accountSummaries:
                        position = 2;
                        break;

                    case R.id.nav_accountGroups:
                        position = 3;
                        break;

                    case R.id.nav_settings:
                        position = 4;
                        break;

                    case R.id.nav_logout:
                        position = 5;
                        break;
                }
                if (!(position == -1)) {
                    selectItem();
                }
                return false;
            }
        });

    }

    public void loadImageNav(Uri uri) {

        ImageView imageView = (ImageView) findViewById(R.id.nv_image);
        if (imageView != null) {
            Picasso.get().load(uri)
                    .resize(imageView.getWidth(), imageView.getHeight())
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    });
        }
    }

    public void selectItem() {

        switch (position) {
            case 0:
                startActivity(new Intent(getApplicationContext(), HomePage.class));
                break;
            case 1:
                startActivity(new Intent(getApplicationContext(), Account_Activity.class));
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(), AccountSumaries_Activity.class));
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), AccountGroups_Activity.class));
                break;
            case 4:
                startActivity(new Intent(getApplicationContext(), Settings_Activity.class));
                break;
            case 5:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;

        }
    }


    public void setupDrawer() {

        t = new ActionBarDrawerToggle(this, dl, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        t.setDrawerIndicatorEnabled(true);
        //dl.setDrawerListener(t);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        t.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        t.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (t.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }

}
