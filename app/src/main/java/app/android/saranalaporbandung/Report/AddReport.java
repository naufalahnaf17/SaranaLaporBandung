package app.android.saranalaporbandung.Report;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import app.android.saranalaporbandung.Adapter.Adapter;
import app.android.saranalaporbandung.Adapter.Point;
import app.android.saranalaporbandung.R;

public class AddReport extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databasePoint;
    private DatabaseReference ref , userRef , pointRef;
    private static final int CHOOSE_IMAGE = 102;
    private ImageView imageReport;
    StorageReference storageRef;
    private Uri uriReportImage;
    StorageTask uploadTask;
    ProgressBar loading;
    EditText edtLaporan , edtLokasi;
    int currentPoint;
    private ValueEventListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        storageRef = FirebaseStorage.getInstance().getReference("laporan");
        databasePoint = FirebaseDatabase.getInstance().getReference("users").child("point");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String id = user.getUid();
        edtLaporan = (EditText)findViewById(R.id.edtLaporan);
        edtLokasi = (EditText)findViewById(R.id.edtLokasi);
        loading = (ProgressBar)findViewById(R.id.loading);

        ref = FirebaseDatabase.getInstance().getReference("laporan");
        userRef = FirebaseDatabase.getInstance().getReference("users").child("laporan").child(id);
        pointRef = FirebaseDatabase.getInstance().getReference("users").child("point").child(id);

        listener = databasePoint.child(id).limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        String idPointRandom = child.getKey();
                        String pointSekarang = dataSnapshot.child(idPointRandom).child("point").getValue().toString();
                        currentPoint = Integer.parseInt(pointSekarang);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageReport = (ImageView)findViewById(R.id.btnPickImage);
        imageReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pilihGambar();
            }
        });

        findViewById(R.id.buttonKirim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask!=null && uploadTask.isInProgress()){
                    Toast.makeText(AddReport.this, "sabar", Toast.LENGTH_SHORT).show();
                }else {
                    uploadImage();
                }
            }
        });

    }


    private void uploadImage() {
        if (uriReportImage!= null){
            final StorageReference storageReference = storageRef.child(System.currentTimeMillis()+"."+"jpg");

            uploadTask = storageReference.putFile(uriReportImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            loading.setProgress(0);
                            finish();

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    String idPost = ref.push().getKey();
                                    String captionPost = edtLaporan.getText().toString().trim();
                                    String locationPost = edtLokasi.getText().toString().trim();
                                    String urlPostPicture = uri.toString();
                                    String nameUserPost = currentUser.getDisplayName();
                                    String point = "30";
                                    int pointUpdate = Integer.parseInt(point);
                                    int hasilPoint = pointUpdate + currentPoint;
                                    String date = new SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault()).format(new Date());
                                    Adapter adapter = new Adapter(idPost,captionPost,locationPost,date,"0","menunggu",urlPostPicture,currentUser.getPhotoUrl().toString(),nameUserPost);
                                    Point adapPoint = new Point(String.valueOf(hasilPoint));
                                    ref.child(idPost).setValue(adapter);
                                    userRef.child(idPost).setValue(adapter);
                                    pointRef.child(idPost).setValue(adapPoint);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddReport.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progres =(100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            loading.setProgress((int) progres);
                        }
                    });
        }else{
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
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

            uriReportImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , uriReportImage);
                imageReport.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
