package app.android.saranalaporbandung.More;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.android.saranalaporbandung.R;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivAccount;
    TextView tvNameAccount , tvEmailAccount;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        ivAccount = (ImageView)findViewById(R.id.ivAccountP);
        tvEmailAccount = (TextView)findViewById(R.id.tvEmailAccount);
        tvNameAccount = (TextView)findViewById(R.id.tvNameAccountP);

        FirebaseUser user = mAuth.getCurrentUser();

        if (user.getDisplayName()!=null){
            tvNameAccount.setText(user.getDisplayName());
        }

        if (user.getPhotoUrl()!=null){
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(ivAccount);
        }

        if (user.getEmail()!=null){
            tvEmailAccount.setText(user.getEmail());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
