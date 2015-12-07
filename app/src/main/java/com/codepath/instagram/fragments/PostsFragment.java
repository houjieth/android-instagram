package com.codepath.instagram.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.instagram.R;
import com.codepath.instagram.adapter.InstagramPostsAdapter;
import com.codepath.instagram.core.MainApplication;
import com.codepath.instagram.helpers.SimpleVerticalSpacerItemDecoration;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramPost;
import com.codepath.instagram.models.InstagramPosts;
import com.codepath.instagram.networking.InstagramClient;
import com.codepath.instagram.persistence.InstagramClientDatabase;
import com.codepath.instagram.receiver.NetworkResultReceiver;
import com.codepath.instagram.services.NetworkIntentService;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PostsFragment extends Fragment {

    private List<InstagramPost> posts;
    private SwipeRefreshLayout swipeContainer;
    private InstagramPostsAdapter adapter;
    private NetworkResultReceiver receiver;

    public static PostsFragment newInstance() {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isNetworkAvailable()) {
            Utils.makeToast("Please check your network", getActivity());
        }
        setupServiceReceiver();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fetchPosts();

        swipeContainer = (SwipeRefreshLayout) getView().findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPosts();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void fetchPosts() {
        // whether there's network or not, we first read posts and comments from local database
        InstagramClientDatabase db = InstagramClientDatabase.getInstance(getContext());
        posts = db.getAllInstagramPosts();
        db.close();

        // launch network service to fetch posts
        launchService();

        // start rendering even before we make network request to Instagram
        // we may end up with rendering local cache from db
        startRender();
    }

    private void startRender() {
        RecyclerView rvPosts = (RecyclerView) getView().findViewById(R.id.rvPosts);
        adapter = new InstagramPostsAdapter(posts, getActivity());
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPosts.addItemDecoration(new SimpleVerticalSpacerItemDecoration(24));
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void setupServiceReceiver() {
        receiver = new NetworkResultReceiver(new Handler());
        receiver.setReceiver(new NetworkResultReceiver.Receiver() {
            // this is the callback function
            // (this is implementing the delegate function)
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if(resultCode == Activity.RESULT_OK) {
                    InstagramPosts postsToUnbundle = (InstagramPosts) resultData.getSerializable("posts");
                    if (postsToUnbundle != null) {
                        posts = postsToUnbundle.posts;
                        adapter.notifyDataSetChanged();

                        Utils.makeToast("GOT POSTS from service", getContext());
                        // persist network response
                        InstagramClientDatabase db = InstagramClientDatabase.getInstance(getContext());
                        db.emptyAllTables();
                        db.addInstagramPosts(posts);
                        db.close();
                    } // otherwise, leave posts as it is (might keep the content read from db)
                } else {
                    Utils.makeToast("Please check your network", getActivity());
                }
                // stop the refreshing animation if there is
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void launchService() {
        Intent intent = new Intent(getContext(), NetworkIntentService.class);
        intent.putExtra("receiver", receiver);
        getActivity().startService(intent);
    }
}
