package app.android.saranalaporbandung.Report;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import app.android.saranalaporbandung.Adapter.Adapter;
import app.android.saranalaporbandung.R;

public class ReportFragment extends Fragment {
    private FirebaseAuth mAuth;
    private ImageView myPhoto;
    private TextView myName , myPost;
    private UserLaporanAdapter mAdapter;
    private FloatingActionButton fab;
    private RecyclerView userRecyclerView;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference;
    private ValueEventListener listener;
    private List<Adapter> userLaporanList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report, container , false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        myPhoto = (ImageView) view.findViewById(R.id.myPhoto);
        myName = (TextView) view.findViewById(R.id.myName);
        myPost = (TextView) view.findViewById(R.id.myPost);
        fab = (FloatingActionButton) view.findViewById(R.id.btnAddReport);

        userRecyclerView = view.findViewById(R.id.rvMyPost);
        userRecyclerView.setHasFixedSize(true);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userLaporanList = new ArrayList<>();
        mAdapter = new UserLaporanAdapter(getContext(), userLaporanList);
        userRecyclerView.setAdapter(mAdapter);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        String userId = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child("laporan");

        if (user.getDisplayName()!=null){
            myName.setText(user.getDisplayName());
        }

        if (user.getPhotoUrl()!=null) {
            Glide.with(getActivity() )
                    .load(user.getPhotoUrl())
                    .into(myPhoto);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddReport.class));
            }
        });


        listener = databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()){
                    int jumlahLaporan =(int) dataSnapshot.getChildrenCount();
                    myPost.setText(Integer.toString(jumlahLaporan));
                }else {
                    myPost.setText("0");
                }

                userLaporanList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Adapter laporanUser = postSnapshot.getValue(Adapter.class);
                    userLaporanList.add(laporanUser);
                }

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
