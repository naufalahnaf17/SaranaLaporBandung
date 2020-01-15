package app.android.saranalaporbandung.StartedApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.android.saranalaporbandung.Intro.IntroAppActivity;
import app.android.saranalaporbandung.R;

public class StartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started);

        Button btnStart = (Button)findViewById(R.id.btn_getStart1);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartedActivity.this, IntroAppActivity.class));
            }
        });

    }
}
