package com.codepath.instagram.fragments;

import com.codepath.instagram.Handler.TagsResultHandler;
import com.codepath.instagram.core.MainApplication;
import com.codepath.instagram.networking.InstagramClient;

/**
 * Created by jie on 12/3/15.
 */
public class SearchTagsResultFragment extends SearchResultsFragment {

    @Override
    public void query(String query) {
        InstagramClient client = MainApplication.getRestClient();
        client.queryTag(query, new TagsResultHandler(getActivity()));
    }
}

