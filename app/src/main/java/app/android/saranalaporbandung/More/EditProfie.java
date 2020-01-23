package app.android.saranalaporbandung.More;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import app.android.saranalaporbandung.Authentication.SetUpProfile;
import app.android.saranalaporbandung.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class EditProfie extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    StorageReference mStorageRef;
    EditText edtNama;
    ImageView edtImage;
    private static final int CHOOSE_IMAGE = 102;
    Uri uriProfileImage;
    StorageTask uploadTask;
    String profileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profie);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtImage = (ImageView) findViewById(R.id.edtImage);
        mStorageRef = FirebaseStorage.getInstance().getReference("images");

        Button btnSave = (Button) findViewById(R.id.btnSimpanProfile);

        Toast.makeText(this, user.getPhotoUrl().toString(), Toast.LENGTH_SHORT).show();

        edtImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                        .setDisplayName(edtNama.getText().toString())
                        .setPhotoUri(Uri.parse(profileUrl))
                        .build();

                user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditProfie.this, "Berhasil Update Profile", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditProfie.this,ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

    }

    private void pilihGambar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data!= null && data.getData()!= null){

            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , uriProfileImage);
                edtImage.setImageBitmap(bitmap);

                uploadImage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void uploadImage() {
        if (uriProfileImage!= null){

            final StorageReference storageReference =
                    mStorageRef.child(System.currentTimeMillis()+".jpg");

            uploadTask = storageReference.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    profileUrl = url;
                                    Toast.makeText(EditProfie.this, profileUrl, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfie.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

}
