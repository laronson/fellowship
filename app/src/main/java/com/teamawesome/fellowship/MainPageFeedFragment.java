package com.teamawesome.fellowship;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageFeedFragment extends Fragment {

    @BindView(R.id.no_initiatives_yet) TextView _no_initiatives_yet;
    @BindView(R.id.no_initiatives_find) TextView _no_initiatives_find;
    @BindView(R.id.no_initiatives_or) TextView _no_initiatives_or;
    @BindView(R.id.no_initiatives_create) TextView _no_initiatives_create;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_main_page_feed_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        _no_initiatives_yet.setText(R.string.no_initiatives_yet);
        _no_initiatives_find.setText(R.string.no_initiatives_find);
    }

    public void onFindInitiativeClicked(View v) {
        // Go to find page
    }

    public void onCreateInitiativeClicked(View v) {
        Intent i = new Intent(getActivity(), CreateInitiativeActivity.class);
        startActivity(i);
    }
}
