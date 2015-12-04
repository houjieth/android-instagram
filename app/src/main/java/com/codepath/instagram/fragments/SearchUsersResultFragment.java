package com.codepath.instagram.fragments;

import com.codepath.instagram.Handler.UsersResultHandler;
import com.codepath.instagram.core.MainApplication;
import com.codepath.instagram.networking.InstagramClient;

/**
 * Created by jie on 12/3/15.
 */
public class SearchUsersResultFragment extends SearchResultsFragment {

    @Override
    public void query(String query) {
        InstagramClient client = MainApplication.getRestClient();
        client.queryUser(query, new UsersResultHandler(getActivity()));
    }
}
