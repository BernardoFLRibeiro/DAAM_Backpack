package iul.iscte.daam_backpack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AnexarFicheiro extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int GALLERY_INTENT = 2;
    private static final int CAMERA_INTENT = 3;

    private RecyclerView mUploadList;
    private ImageView mImageView;
    private ProgressDialog mProgressDialog;

    private List<String> fileNameList;
    private List<String> fileDoneList;

    private StorageReference mStorageReference;
    private UploadListAdapter uploadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anexar_ficheiro);

        mProgressDialog = new ProgressDialog(this);
        mImageView  = (ImageView) findViewById(R.id.image_uploaded);
        mStorageReference = FirebaseStorage.getInstance().getReference();

        mUploadList = (RecyclerView) findViewById(R.id.listUpload);

        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();

        uploadListAdapter = new UploadListAdapter(fileNameList,fileDoneList);

        mUploadList.setLayoutManager(new LinearLayoutManager(this));
        mUploadList.setHasFixedSize(true);
        mUploadList.setAdapter(uploadListAdapter);

    }

    public void capturarImagem(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_INTENT);
    }


    public void anexarFicheiro(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }


    public void selecionarImagem(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(intent, GALLERY_INTENT );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            if(data.getClipData()!= null) {
                final int totalItems = data.getClipData().getItemCount();

                for (int i = 0; i < totalItems; i++) {
                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    String fileName = getFileName(fileUri);

                    fileNameList.add(fileName);
                    fileDoneList.add("uploading");
                    uploadListAdapter.notifyDataSetChanged();

                    StorageReference fileToUpload = mStorageReference.child("Images").child(fileName);

                    final int finalI = i;
                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileDoneList.remove(finalI);
                            fileDoneList.add(finalI, "done");

                            uploadListAdapter.notifyDataSetChanged();
                            //Toast.makeText(AnexarFicheiro.this, "Carregou " + totalItems + " imagens!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AnexarFicheiro.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                        }
                    });;
                }
            }
        }else if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            mProgressDialog.setMessage("A carregar...");
            mProgressDialog.show();

            Uri uri = data.getData();
            StorageReference filePath = mStorageReference.child("Gallery").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AnexarFicheiro.this, "Carregamento com sucesso!", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AnexarFicheiro.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            });

        }else if(requestCode == CAMERA_INTENT && resultCode == RESULT_OK){
            mProgressDialog.setMessage("A carregar...");
            mProgressDialog.show();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Photos");
            ref.setValue(imageBitmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(AnexarFicheiro.this, "Carregamento com sucesso!", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AnexarFicheiro.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            });
        }

    }

    public String getFileName(Uri uri){
        String result = null;
        if(uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if(result == null){
            result = uri.getPath();
            int cut = result.lastIndexOf('h');
            if(cut != -1){
                result = result.substring(cut+1);
            }
        }
        return result;
    }

}

