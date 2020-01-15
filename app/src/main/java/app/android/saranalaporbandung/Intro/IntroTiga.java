package app.android.saranalaporbandung.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import app.android.saranalaporbandung.Authentication.LoginActivity;
import app.android.saranalaporbandung.R;

public class IntroTiga extends Fragment {

    public IntroTiga(){}

    Button btnNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.intro_tiga, container, false);

        btnNext = v.findViewById(R.id.btn_next3);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        return v;
    }
}