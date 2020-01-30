package app.android.saranalaporbandung.More;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.android.saranalaporbandung.Home.MainActivity;
import app.android.saranalaporbandung.R;

public class ContactUs extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText edtSubject, edtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        edtSubject = (EditText)findViewById(R.id.edtSubject);
        edtMessage = (EditText)findViewById(R.id.edtMessage);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
//                Toast.makeText(ContactUs.this, "Fitur Belum Bisa Digunakan", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnBackContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void sendEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String currentYear = sdf.format(cal.getTime());
        String[] TO = {"mypointcompany@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, ""+ edtSubject.getText());
        emailIntent.putExtra(Intent.EXTRA_TEXT, edtMessage.getText() + "\n\n" +
                user.getDisplayName() + " - " + user.getUid() + "\n" + "© My Point " + currentYear);
        try {
            startActivity(emailIntent);
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactUs.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
