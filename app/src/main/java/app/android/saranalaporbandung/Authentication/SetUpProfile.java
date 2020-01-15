package app.android.saranalaporbandung.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import app.android.saranalaporbandung.Home.MainActivity;
import app.android.saranalaporbandung.R;

public class SetUpProfile extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    StorageTask uploadTask;
    ImageView ivImage;
    EditText eTxtNama;
    Button btnSimpan;
    ProgressBar loading;
    String profileUrl;

    Uri uriProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile);

        ivImage = (ImageView) findViewById(R.id.ivImage);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        loading = (ProgressBar)findViewById(R.id.loading);
        eTxtNama = (EditText)findViewById(R.id.edtUsername);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("images");

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pilihGambar();
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user.getDisplayName()!=null){
            finish();
            startActivity(new Intent(SetUpProfile.this , MainActivity.class));
        }

    }

    private void saveUserInformation() {
        String displayName = eTxtNama.getText().toString().trim();

        if (displayName.isEmpty()){
            eTxtNama.setError("Nama Tidak Boleh Kosong");
            eTxtNama.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && profileUrl != null){

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileUrl))
                    .build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SetUpProfile.this, "Berhasil Menyimpan Data Profile", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(SetUpProfile.this , MainActivity.class));
                    }
                }
            });
        }

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
                ivImage.setImageBitmap(bitmap);

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

            loading.setVisibility(View.VISIBLE);
            uploadTask = storageReference.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            loading.setVisibility(View.GONE);

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    profileUrl = url;
                                }
                            });
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SetUpProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}
