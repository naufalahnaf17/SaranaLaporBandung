package app.android.saranalaporbandung.More;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import app.android.saranalaporbandung.R;

public class SettingAplication extends AppCompatActivity {

    RelativeLayout ringtone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_aplication);
        ringtone = (RelativeLayout)findViewById(R.id.ringtone);

        findViewById(R.id.btnBackSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
    }
}
