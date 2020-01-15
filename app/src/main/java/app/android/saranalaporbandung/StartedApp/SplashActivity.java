package app.android.saranalaporbandung.StartedApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import app.android.saranalaporbandung.Authentication.LoginActivity;
import app.android.saranalaporbandung.R;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sr;
    Boolean first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sr = getSharedPreferences("status" , MODE_PRIVATE);
        first = sr.getBoolean("status" , true);

        if (first){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences.Editor editor = sr.edit();
                    first = false;
                    editor.putBoolean("status" , first);
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), StartedActivity.class));
                    finish();
                }
            }, 3000L);
        }

        else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

    }
}
