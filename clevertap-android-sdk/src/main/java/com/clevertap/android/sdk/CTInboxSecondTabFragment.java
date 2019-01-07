package com.clevertap.android.sdk;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CTInboxSecondTabFragment extends CTInboxTabBaseFragment {
    RecyclerView recyclerView;
    private boolean firstTime = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View allView = inflater.inflate(R.layout.inbox_first_tab,container,false);
        videoPresent = checkInboxMessagesContainVideo(inboxMessageArrayList);
        //Check if video present to render appropriate recyclerview
        //TODO this check can be removed and instead use the check while the activity is getting created
        //TODO Render exoplayerrecyclerview dynamically only if videos are present in the inbox messages
        CTInboxMessageAdapter inboxMessageAdapter;
        if(videoPresent) {
            exoPlayerRecyclerView = allView.findViewById(R.id.first_tab_exo_recycler_view);
            exoPlayerRecyclerView.setVisibility(View.VISIBLE);
            exoPlayerRecyclerView.setVideoInfoList(inboxMessageArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            exoPlayerRecyclerView.setLayoutManager(linearLayoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(exoPlayerRecyclerView.getContext(),
                    linearLayoutManager.getOrientation());
            exoPlayerRecyclerView.addItemDecoration(dividerItemDecoration);
            exoPlayerRecyclerView.setItemAnimator(new DefaultItemAnimator());

            inboxMessageAdapter = new CTInboxMessageAdapter(inboxMessageArrayList, getActivity(),this);
            inboxMessageAdapter.filterMessages(styleConfig.getSecondTab());//Filters the messages before rendering the list on tabs
            exoPlayerRecyclerView.setAdapter(inboxMessageAdapter);
            inboxMessageAdapter.notifyDataSetChanged();
            if (firstTime) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exoPlayerRecyclerView.playVideo();
                    }
                },1000);
                firstTime = false;
            }
        }else{
            recyclerView = allView.findViewById(R.id.first_tab_recycler_view);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    linearLayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            inboxMessageAdapter = new CTInboxMessageAdapter(inboxMessageArrayList, getActivity(),this);
            inboxMessageAdapter.filterMessages(styleConfig.getSecondTab());//Filters the messages before rendering the list on tabs
            recyclerView.setAdapter(inboxMessageAdapter);
            inboxMessageAdapter.notifyDataSetChanged();
        }

        return allView;
    }
}