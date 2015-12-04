package com.codepath.instagram.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.instagram.R;
import com.codepath.instagram.adapter.HomeFragmentStatePagerAdapter;
import com.codepath.instagram.adapter.SearchFragmentStatePagerAdapter;

public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ViewPager viewPager = (ViewPager) getView().findViewById(R.id.viewpagerSearch);
        viewPager.setAdapter(new SearchFragmentStatePagerAdapter(getChildFragmentManager()));

        TabLayout tableLayout = (TabLayout) getView().findViewById(R.id.tabsSearchType);
        tableLayout.setupWithViewPager(viewPager);
    }
}
