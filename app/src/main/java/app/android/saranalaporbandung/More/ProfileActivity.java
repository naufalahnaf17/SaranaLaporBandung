package app.android.saranalaporbandung.More;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.widget.Toolbar;
import app.android.saranalaporbandung.Authentication.SetUpProfile;
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
        Toolbar toolBarProfile = (Toolbar)findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolBarProfile);
        toolBarProfile.setTitle(R.string.profile_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        //getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.edit_profile){
            startActivity(new Intent(this, EditProfie.class));
        } else if (item.getItemId() == R.id.edit_password) {
            startActivity(new Intent(this, ChangePassword.class));
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
