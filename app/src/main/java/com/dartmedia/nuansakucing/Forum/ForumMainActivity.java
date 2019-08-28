package com.dartmedia.nuansakucing.Forum;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dartmedia.nuansakucing.Adapter.ForumAdapter;
import com.dartmedia.nuansakucing.MainActivity;
import com.dartmedia.nuansakucing.Model.Forum;
import com.dartmedia.nuansakucing.R;

import java.util.ArrayList;

public class ForumMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Forum> mForumData;
    private ForumAdapter forumAdapter;
//    BaseAPIService APIService;
    private FloatingActionButton floatingActionButton;
    private ActionBar menu;
    private DrawerLayout DL;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_main);



        Toolbar t = findViewById(R.id.toolbarMain);
        setSupportActionBar(t);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mForumData = new ArrayList<>();
        forumAdapter = new ForumAdapter(this, mForumData);
        recyclerView.setAdapter(forumAdapter);
        floatingActionButton = findViewById(R.id.fab);

//        APIService = UtilsAPI.GetAPIService();

        initializedData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newThreadIntent = new Intent(ForumMainActivity.this, NewThreadForumActivity.class);
                startActivity(newThreadIntent);

                Log.i("Status", "onResponse: Gagal");
            }
        });

    }

    private void initializedData() {
        String[] forumJudul = getResources().getStringArray(R.array.forum_titles);
        String[] forumInfo = getResources().getStringArray(R.array.forum_info);
        String[] forumImage = getResources().getStringArray(R.array.forum_image);

        mForumData.clear();

        for (int i = 0; i < forumJudul.length; i++) {
            mForumData.add(new Forum(forumJudul[i], forumInfo[i], forumImage[i]));
        }
        forumAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.SearchIconDetail){
            Intent newThreadIntent = new Intent(ForumMainActivity.this, NewThreadForumActivity.class);
            startActivity(newThreadIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
