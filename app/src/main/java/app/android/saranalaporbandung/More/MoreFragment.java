package app.android.saranalaporbandung.More;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import app.android.saranalaporbandung.Authentication.LoginActivity;
import app.android.saranalaporbandung.R;

public class MoreFragment extends Fragment {

    private FirebaseAuth mAuth;
    List<MoreMenu> menuList;
    ListView lvMenu;
    TextView tvNameAccount;
    ImageView ivAccount;
    CardView btnExit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_more, container , false);

        lvMenu = (ListView) v.findViewById(R.id.lvMenu);
        tvNameAccount = (TextView)v.findViewById(R.id.tvNameAccountP);
        ivAccount = (ImageView)v.findViewById(R.id.ivAccountP);
        btnExit = (CardView)v.findViewById(R.id.btnExit);

        menuList = new ArrayList<>();
        addMenu();
        mAuth = FirebaseAuth.getInstance();

        MoreMenuAdapter adapter = new MoreMenuAdapter(getContext(), R.layout.custom_lv_menu, menuList);
        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    //kalo account
                    startActivity(new Intent(getActivity(),ProfileActivity.class));
                } else if (position == 1) {
                    //kalo privasi
                    startActivity(new Intent(getActivity(),PrivacyPolice.class));
                } else if (position == 2) {
                    //kalo setting
                    startActivity(new Intent(getActivity(),SettingAplication.class));
                } else if (position == 3) {
                    //kalo about
                    startActivity(new Intent(getActivity(),AboutAplication.class));
                } else if (position == 4) {
                    //kalo contact
                    startActivity(new Intent(getActivity(),ContactUs.class));
                } else if (position == 5) {
                    //kalo rate
                    Toast.makeText(getContext(), "Fitur Ini Belum Tersedia", Toast.LENGTH_SHORT).show();
                } else if (position == 6) {
                    //kalo bagikan
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "http://saranalaporbandung.com");
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, "Pilih Media");
                    startActivity(shareIntent);
//                    Toast.makeText(getContext(), "Fitur Ini Belum Tersedia", Toast.LENGTH_SHORT).show();
                } else {
                    //null
                }
            }
        });

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser user = mAuth.getCurrentUser();

        if (user.getDisplayName()!=null){
            tvNameAccount.setText(user.getDisplayName());
        }

        if (user.getPhotoUrl()!=null) {
            Glide.with(getActivity() )
                    .load(user.getPhotoUrl())
                    .into(ivAccount);
        }

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private void logout() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_exit_dialog,null);
        dialogBuilder.setView(dialogView);

        Button yes = (Button) dialogView.findViewById(R.id.btnYa);
        Button no = (Button) dialogView.findViewById(R.id.btnTidak);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                alertDialog.dismiss();
                getActivity().finish();
                startActivity(new Intent(getContext() , LoginActivity.class));
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private void addMenu() {
        menuList.add(new MoreMenu(R.drawable.ic_account, "Akun"));
        menuList.add(new MoreMenu(R.drawable.ic_privasi, "Kebijakan Privasi"));
        menuList.add(new MoreMenu(R.drawable.ic_settings, "Pengaturan"));
        menuList.add(new MoreMenu(R.drawable.ic_about, "Tentang Aplikasi"));
        menuList.add(new MoreMenu(R.drawable.ic_contact_us, "Hubungi Kami"));
        menuList.add(new MoreMenu(R.drawable.ic_rate, "Rate Aplikasi Ini"));
        menuList.add(new MoreMenu(R.drawable.ic_share, "Bagikan Aplikasi Ini"));
    }


}
