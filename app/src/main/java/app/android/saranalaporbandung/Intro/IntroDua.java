package app.android.saranalaporbandung.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import app.android.saranalaporbandung.Authentication.LoginActivity;
import app.android.saranalaporbandung.R;

public class IntroDua extends Fragment {

    public IntroDua(){}

    ViewPager viewPager;
    Button btnNext;
    TextView btnSkip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.intro_dua, container, false);

        btnNext = v.findViewById(R.id.btn_next2);
        viewPager = getActivity().findViewById(R.id.viewPager);
        btnSkip = v.findViewById(R.id.btn_skip);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}