package app.android.saranalaporbandung.More;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import app.android.saranalaporbandung.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    EditText edtPassLama , edtPassBaru , edtRePassBaru;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        edtPassLama = (EditText) findViewById(R.id.edtPasswordLama);
        edtPassBaru = (EditText) findViewById(R.id.edtPasswordBaru);
        edtRePassBaru = (EditText) findViewById(R.id.edtRePasswordBaru);
        loading = (ProgressBar) findViewById(R.id.loading);

        Button btnSimpan = (Button) findViewById(R.id.btnSimpanProfile);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loading.setVisibility(View.VISIBLE);

                String passLama = edtPassLama.getText().toString();
                final String passBaru = edtPassBaru.getText().toString();
                String rePassBaru = edtRePassBaru.getText().toString();

                if (passLama.isEmpty()){
                    edtPassLama.setError("Masukan Password Lama Anda");
                    edtPassLama.setText("");
                    edtPassLama.requestFocus();
                    return;
                }

                if (passBaru.isEmpty()){
                    edtPassBaru.setError("Masukan Password Baru Anda");
                    edtPassBaru.setText("");
                    edtPassBaru.requestFocus();
                    return;
                }

                if (rePassBaru.isEmpty()){
                    edtRePassBaru.setError("Masukan Password Lama Anda");
                    edtRePassBaru.setText("");
                    edtRePassBaru.requestFocus();
                    return;
                }

                if (passBaru != rePassBaru){
                    edtPassBaru.setError("Password Yang Anda Masukan Tidak Sama");
                    edtPassBaru.requestFocus();
                    edtPassBaru.setText("");
                    edtRePassBaru.setText("");
                    return;
                }

                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(),passLama);

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            user.updatePassword(passBaru).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        loading.setVisibility(View.GONE);
                                        finish();
                                        Toast.makeText(ChangePassword.this, "Berhasil ganti Password", Toast.LENGTH_SHORT).show();
                                    }else {
                                        loading.setVisibility(View.GONE);
                                        finish();
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
