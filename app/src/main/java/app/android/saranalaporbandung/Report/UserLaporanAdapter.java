package app.android.saranalaporbandung.Report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;

import app.android.saranalaporbandung.Adapter.Adapter;
import app.android.saranalaporbandung.R;

public class UserLaporanAdapter extends RecyclerView.Adapter<UserLaporanAdapter.UserLaporanViewHolder> {

    private Context mContext;
    private List<Adapter> mLaporan;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public UserLaporanAdapter(Context context, List<Adapter> laporan) {
        mContext = context;
        mLaporan = laporan;
    }

    @NonNull
    @Override
    public UserLaporanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_my_post, parent, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        return new UserLaporanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserLaporanViewHolder holder, int position) {
        final Adapter adapterCurrent = mLaporan.get(position);

        Glide.with(mContext)
                .load(adapterCurrent.getUrlPost())
                .placeholder(R.drawable.loading)
                .fitCenter()
                .centerCrop()
                .into(holder.imagePost);

        holder.contentPost.setText(adapterCurrent.getCaptionPost());
        holder.locatePost.setText(adapterCurrent.getLocationPost());
        holder.datePost.setText(adapterCurrent.getDatePost());
        holder.countVoter.setText(adapterCurrent.getLikePost());
        holder.detailPost.setText(adapterCurrent.getStatusPost());

    }

    @Override
    public int getItemCount() {
        return mLaporan.size();
    }

    public class UserLaporanViewHolder extends RecyclerView.ViewHolder{

        ImageView imagePost;
        TextView contentPost , locatePost , datePost , countVoter , detailPost ;

        public UserLaporanViewHolder(@NonNull View itemView) {
            super(itemView);

            imagePost = itemView.findViewById(R.id.imagePostMe);
            contentPost = itemView.findViewById(R.id.contentPostMe);
            locatePost = itemView.findViewById(R.id.locatePostMe);
            datePost = itemView.findViewById(R.id.datePostMe);
            countVoter = itemView.findViewById(R.id.countVoterMe);
            detailPost = itemView.findViewById(R.id.detailPostMe);

        }
    }

}
