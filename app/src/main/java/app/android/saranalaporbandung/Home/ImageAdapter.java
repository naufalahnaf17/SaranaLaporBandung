package app.android.saranalaporbandung.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import app.android.saranalaporbandung.R;
import app.android.saranalaporbandung.Adapter.Adapter;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Adapter> mAdapters;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private int likeStat = 0;

    public ImageAdapter(Context context, List<Adapter> adapters) {
        mContext = context;
        mAdapters = adapters;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_post_home, parent, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final Adapter adapterCurrent = mAdapters.get(position);

        //photo Account
        Glide.with(mContext)
                .load(adapterCurrent.getUrlPhotoUser())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.photoAccount);

        //caption Post
        holder.contentPost.setText(adapterCurrent.getCaptionPost());

        //location Post
        holder.locationPost.setText(adapterCurrent.getLocationPost());

        //date Post
        holder.datePost.setText(adapterCurrent.getDatePost());

        //status Post
        holder.statusVoter.setText(adapterCurrent.getStatusPost());

        //Content Image Post
        Glide.with(mContext)
                .load(adapterCurrent.getUrlPost())
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .centerCrop()
                .into(holder.photoPost);

        //Username User Account Who Post The Fucking Picture
        holder.nameAccount.setText(adapterCurrent.getNameUserPost());

        //Vote Postingan
        holder.btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (likeStat == 0){
                    likeStat = 1;
                    holder.btnVote.setImageResource(R.drawable.like);
                }else if(likeStat == 1) {
                    likeStat = 0;
                    holder.btnVote.setImageResource(R.drawable.ic_vote_active);
                }

            }
        });


        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext, adapterCurrent.getUrlPhotoUser(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAdapters.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder  {
        public TextView contentPost , nameAccount , datePost , statusVoter ,locationPost;
        public ImageView photoPost , photoAccount;
        public CardView card;
        public ImageButton btnVote;

        public ImageViewHolder(View itemView) {
            super(itemView);

            contentPost = itemView.findViewById(R.id.contentPostMe);
            photoPost = itemView.findViewById(R.id.photoPost);
            photoAccount = itemView.findViewById(R.id.photoAccount);
            card = itemView.findViewById(R.id.cardPost);
            nameAccount = itemView.findViewById(R.id.nameAccount);
            datePost = itemView.findViewById(R.id.datePost);
            statusVoter = itemView.findViewById(R.id.detailPostMe);
            locationPost = itemView.findViewById(R.id.locatePost);
            btnVote = itemView.findViewById(R.id.btnVote);
        }


    }
}
