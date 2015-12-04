package com.codepath.instagram.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.instagram.Handler.TagsResultHandler;
import com.codepath.instagram.R;
import com.codepath.instagram.core.MainApplication;
import com.codepath.instagram.networking.InstagramClient;

/**
 * Created by jie on 12/3/15.
 */
public class SearchTagsResultFragment extends SearchResultsFragment {

    @Override
    public void query(String query) {
        InstagramClient client = MainApplication.getRestClient();
        client.queryTag(query, new TagsResultHandler(getView()));
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_tags_result, container, false);
    }
}

