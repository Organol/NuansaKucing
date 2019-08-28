package com.dartmedia.nuansakucing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dartmedia.nuansakucing.Forum.DetailActivity;
import com.dartmedia.nuansakucing.Model.Forum;
import com.dartmedia.nuansakucing.R;

import java.util.ArrayList;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {
    private ArrayList<Forum> forums;
    private Context context;

    public ForumAdapter(Context context, ArrayList<Forum> forumData){
        this.forums = forumData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Forum forum = forums.get(i);
        viewHolder.bindTo(forum);
    }

    @Override
    public int getItemCount() {
        return forums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView description;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_forum);
            description = itemView.findViewById(R.id.subTite_forum);
            image = itemView.findViewById(R.id.forumImage);

            itemView.setOnClickListener(this);
        }

        public void bindTo(Forum forum) {
            title.setText(forum.getTitle());
            description.setText(forum.getDescription());

            Glide.with(context).load(forum.getImageResource()).into(image);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Forum currentForum = forums.get(getAdapterPosition());

            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("title", currentForum.getTitle());
            detailIntent.putExtra("deskripsi", currentForum.getDescription());
            detailIntent.putExtra("Image_resource", currentForum.getImageResource());
            context.startActivity(detailIntent);
        }
    }
}
