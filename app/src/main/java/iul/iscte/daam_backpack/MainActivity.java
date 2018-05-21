package iul.iscte.daam_backpack;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText email, password;
    private TextView userRegistration;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        email =  findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        userRegistration = findViewById(R.id.newUser);

        login = findViewById(R.id.login_bt);


// funciona mas preciso de testar o login
//        FirebaseUser user = auth.getCurrentUser();
//
//        if(user != null){
//            finish();
//            startActivity(new Intent(MainActivity.this, Perfil.class));
//        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin(email.getText().toString(), password.getText().toString());
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Registo.class));
            }
        });


    }

    private void validateLogin(String email, String password){

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, " Login Sucessfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Perfil.class));
                } else{
                    Toast.makeText(MainActivity.this, " Login Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }







}
