package app.android.saranalaporbandung.More;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import app.android.saranalaporbandung.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        Button btnSimpan = (Button) findViewById(R.id.btnSimpanProfile);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(),"123456789");

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            user.updatePassword("0987654321").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ChangePassword.this, "Berhasil ganti Password", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(ChangePassword.this, "Gagal ganti Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });


            }
        });
    }
}
