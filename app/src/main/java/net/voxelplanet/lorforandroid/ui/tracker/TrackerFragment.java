/*
 * Copyright 2015 getsmp
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.voxelplanet.lorforandroid.ui.tracker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.api.Adapter;
import net.voxelplanet.lorforandroid.api.ApiTracker;
import net.voxelplanet.lorforandroid.model.TrackerItem;
import net.voxelplanet.lorforandroid.model.TrackerItems;
import net.voxelplanet.lorforandroid.ui.util.DividerItemDecoration;
import net.voxelplanet.lorforandroid.ui.util.InfiniteScrollListener;
import net.voxelplanet.lorforandroid.ui.util.ItemClickCallback;
import net.voxelplanet.lorforandroid.ui.util.ItemClickListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TrackerFragment extends Fragment {
    private final ArrayList<TrackerItem> items = new ArrayList<TrackerItem>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private Activity activity;
    private RecyclerView.Adapter adapter;
    private ItemClickCallback callback;
    private String filter;
    private int currentOffset = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        this.callback = (ItemClickCallback) activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        final RecyclerView trackerView = (RecyclerView) view.findViewById(R.id.trackerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        trackerView.setLayoutManager(layoutManager);
        trackerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));

        trackerView.setOnScrollListener(new InfiniteScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (currentOffset < 300) {
                    getListItems(filter);
                } else {
                    Toast.makeText(activity, R.string.error_limit, Toast.LENGTH_SHORT).show();
                }
            }
        });

        trackerView.addOnItemTouchListener(new ItemClickListener(activity, new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                callback.onItemClicked(items.get(trackerView.getChildPosition(view)).getUrl());
            }
        }));

        adapter = new TrackerAdapter(items, activity);
        trackerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.trackerSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListItems(filter);
            }
        });

        return view;
    }

    public void getListItems(String filter) {
        this.filter = filter;
        ApiTracker apiTracker = Adapter.restAdapter.create(ApiTracker.class);
        apiTracker.getTracker(currentOffset, filter, new Callback<TrackerItems>() {
            @Override
            public void success(TrackerItems trackerItems, Response response) {
                if (trackerItems.trackerItems.size() > 0) {
                    currentOffset += 30;
                    items.addAll(trackerItems.trackerItems);
                    // Next lines are here to remove duplicate topics from tracker due to linux.org.ru engine bug
                    Set<TrackerItem> trackerItemSet = new LinkedHashSet<TrackerItem>(items);
                    items.clear();
                    items.addAll(trackerItemSet);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(activity, R.string.error_no_more_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(activity, R.string.error_network, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
