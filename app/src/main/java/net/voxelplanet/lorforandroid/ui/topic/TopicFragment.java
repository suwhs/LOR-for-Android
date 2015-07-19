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

package net.voxelplanet.lorforandroid.ui.topic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.api.ApiManager;
import net.voxelplanet.lorforandroid.api.ApiTopic;
import net.voxelplanet.lorforandroid.model.Topic;
import net.voxelplanet.lorforandroid.model.Topics;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TopicFragment extends Fragment {
    @Bind(R.id.topicTitle)
    private TextView title;
    @Bind(R.id.topicTags)
    private TextView tags;
    @Bind(R.id.topicAuthor)
    private TextView author;
    @Bind(R.id.topicMessage)
    private TextView message;
    @Bind(R.id.topicSwipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private String url;
    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTopic(url);
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        return view;
    }

    public void loadTopic(String url) {
        this.url = url;
        ApiManager.API_MANAGER.getApiTopic().getTopic(url, new Callback<Topics>() {
            @Override
            public void success(Topics topics, Response response) {
                Topic topic = topics.topic;
                title.setText(Html.fromHtml(topic.getTitle()));
                List<String> tagsList = topic.getTags();
                if (!tagsList.isEmpty()) {
                    tags.setVisibility(View.VISIBLE);
                    tags.setText(TextUtils.join(", ", topic.getTags()));
                } else tags.setVisibility(View.GONE);
                author.setText(topic.getAuthor().getNick());
                message.setText(Html.fromHtml(topic.getMessage()));
                message.setMovementMethod(LinkMovementMethod.getInstance());
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(activity, R.string.error_network, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
