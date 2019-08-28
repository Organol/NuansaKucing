package com.dartmedia.nuansakucing.Forum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dartmedia.nuansakucing.Model.Forum;
import com.dartmedia.nuansakucing.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView mrecyclerView;
    private ArrayList<Forum> mForumData;
    ImageButton btnMenu;
    private ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar t = findViewById(R.id.toolbarDetail);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        TextView forumTitle = findViewById(R.id.title_forum_detail);
        TextView forumInfo = findViewById(R.id.subTite_forum_detail);
        ImageView forumImage = findViewById(R.id.contentImageDetail);

        forumTitle.setText(getIntent().getStringExtra("title"));
        forumInfo.setText(getIntent().getStringExtra("description"));

        Glide.with(this).load(getIntent().getStringExtra("Image_resource"))
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher).into(forumImage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
