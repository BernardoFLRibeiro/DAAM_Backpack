package iul.iscte.daam_backpack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Search extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mImageButton;
    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        mSearchField = (EditText) findViewById(R.id.search_field);
        mImageButton = (ImageButton) findViewById(R.id.search_button);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

    }


    public void startSearch() {
        Query usersQuery = mUserDatabase.orderByKey();
        FirebaseRecyclerOptions userOptions = new FirebaseRecyclerOptions.Builder<Users>().setQuery(usersQuery, Users.class).build();

        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>
                (userOptions) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {
                holder.setDetails(getApplicationContext(), model.getNome(), model.getStatus(), model.getImage());
            }
            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);
    }


    public class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setDetails(Context ctx, String username, String status, String image){

            TextView user_name = (TextView) mView.findViewById(R.id.search_name);
            TextView user_status = (TextView) mView.findViewById(R.id.search_description);
            ImageView user_image = (ImageView) mView.findViewById(R.id.search_image);

            user_name.setText(username);
            user_status.setText(status);
            Glide.with(ctx).load(image).into(user_image);
        }
    }

}
