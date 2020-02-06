package app.android.saranalaporbandung.More;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.android.saranalaporbandung.R;

public class SettingAplication extends AppCompatActivity {
    int checkedItem = 19;
    RelativeLayout ringtone;
    TextView subringtone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_aplication);
        ringtone = (RelativeLayout)findViewById(R.id.ringtone);
        subringtone = (TextView)findViewById(R.id.tvSubRingtone);
        subringtone.setText(getResources().getStringArray(R.array.list_ringtone)[checkedItem]);
        ringtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
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

    public void showAlertDialog(){
        // setup the alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nada");
        // add a radio button list

        builder.setSingleChoiceItems(R.array.list_ringtone, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item
                checkedItem = which;
            }
        });

        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                subringtone.setText(getResources().getStringArray(R.array.list_ringtone)[checkedItem]);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("BATAL", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
