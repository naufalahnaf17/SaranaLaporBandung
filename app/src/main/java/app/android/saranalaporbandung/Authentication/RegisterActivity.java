package app.android.saranalaporbandung.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import app.android.saranalaporbandung.R;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText edtUsernameRegist , edtEmailRegist , edtPasswordRegist , edtPasswordConfirm;
    Button btnRegister;
    TextView btnToLogin;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        edtUsernameRegist = (EditText)findViewById(R.id.edt_username_regist);
        edtEmailRegist = (EditText)findViewById(R.id.edt_email_regist);
        edtPasswordRegist = (EditText)findViewById(R.id.edt_password_regist);
        edtPasswordConfirm = (EditText)findViewById(R.id.edt_retry_password_regist);
        btnRegister = (Button)findViewById(R.id.btn_register);
        btnToLogin = (TextView)findViewById(R.id.btn_tologin);
        loading = (ProgressBar)findViewById(R.id.loading);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterAccount();
                closeKeyboard();
            }
        });

    }

    private void RegisterAccount() {
        String username = edtUsernameRegist.getText().toString().trim();
        String email = edtEmailRegist.getText().toString().trim();
        String password = edtPasswordRegist.getText().toString().trim();
        String passwordConfirm = edtPasswordConfirm.getText().toString().trim();

        if (username.isEmpty()){
            edtUsernameRegist.setError("Username Tidak Boleh Kosong");
            edtUsernameRegist.requestFocus();
            return;
        }

        if (email.isEmpty()){
            edtEmailRegist.setError("Email Tidak Boleh Kosong");
            edtEmailRegist.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPasswordRegist.setError("Password Tidak Boleh Kosong");
            edtPasswordRegist.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmailRegist.setError("Masukan Email Dengan Benar");
            edtEmailRegist.requestFocus();
            return;
        }

        if (password.length()<=6){
            edtPasswordRegist.setError("Masukan Lebih Dari 6 Karakter ");
            edtPasswordRegist.requestFocus();
            return;
        }

        if (passwordConfirm.length()<=6){
            edtPasswordConfirm.setError("Masukan Lebih Dari 6 Karakter ");
            edtPasswordConfirm.requestFocus();
            return;
        }

        if (!password.equals(passwordConfirm)){
            edtPasswordRegist.setText("");
            edtPasswordConfirm.setText("");
            edtPasswordRegist.setError("Password Tidak Sama ");
            edtPasswordRegist.requestFocus();
            return;
        }

        loading.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    loading.setVisibility(View.GONE);
                    Intent intent = new Intent(RegisterActivity.this , SetUpProfile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.setVisibility(View.GONE);
                Snackbar.make(findViewById(R.id.viewRegister),"Terjadi Kesalahan " + e.getMessage() , Snackbar.LENGTH_SHORT).show();
                edtUsernameRegist.requestFocus();
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
