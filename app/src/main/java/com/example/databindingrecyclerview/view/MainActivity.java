package com.example.databindingrecyclerview.view;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.example.databindingrecyclerview.R;
import com.example.databindingrecyclerview.model.Post;
import com.example.databindingrecyclerview.model.User;
import com.example.databindingrecyclerview.util.GridSpacingItemDecoration;
import com.example.databindingrecyclerview.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostsAdapter.PostsAdapterListener {

    private MyClickHandlers handlers;
    private PostsAdapter mAdapter;
    private RecyclerView recyclerView;
    private ActivityMainBinding binding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setUpToolbar();

        handlers = new MyClickHandlers(this);

        initializeRecyclerView();

        loadProfile();
    }

    private void setUpToolbar() {
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeRecyclerView() {
        recyclerView = binding.content.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(4), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new PostsAdapter(getPosts(), this);
        recyclerView.setAdapter(mAdapter);
    }

    private void loadProfile() {
        user = new User();
        user.setName("Mukesh Gami");
        user.setEmail("gami@gmail.com");
        user.setProfileImage("https://picsum.photos/200/200?image=2");
        user.setAbout("IOS Developer");

        /*ObservableField doesn't have setter method, instead
           will be called using set() method*/
        user.numberOfPosts.set(5600L);
        user.numberOfFollowers.set(253614253L);
        user.numberOfFollowing.set(360L);
        binding.setUser(user);
        binding.content.setHandlers(handlers);
    }

    private ArrayList<Post> getPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 1; i < 40; i++) {
            Post post = new Post();
            post.setImageUrl("https://picsum.photos/200/200?image=" + i );

            posts.add(post);
        }

        return posts;
    }

    @Override
    public void onPostClicked(Post post) {
        Toast.makeText(getApplicationContext(), "Post clicked " + post.getImageUrl(), Toast.LENGTH_SHORT).show();
    }

    public class MyClickHandlers {

        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }


        public void onProfileFabClicked(View view) {
            user.setName("Sir Sunil");
            user.setProfileImage("https://picsum.photos/200/200?image=1");
            user.setAbout("Android Developer");
            user.numberOfPosts.set(84500L);
            user.numberOfFollowers.set(9632145L);
            user.numberOfFollowing.set(630L);
        }

        public boolean onProfileImageLongPressed(View view) {
            Toast.makeText(getApplicationContext(), "Profile img long pressed!", Toast.LENGTH_LONG).show();
            return false;
        }


        public void onFollowersClicked(View view) {
            Toast.makeText(context, "Followers is clicked", Toast.LENGTH_SHORT).show();
        }

        public void onFollowingClicked(View view) {
            Toast.makeText(context, "Following is clicked", Toast.LENGTH_SHORT).show();
        }

        public void onPostsClicked(View view) {
            Toast.makeText(context, "Posts is clicked", Toast.LENGTH_SHORT).show();
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}