/*
 *
 *  * Copyright 2016 getsmp
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package io.github.getsmp.lorforandroid.ui.topic;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.api.ApiManager;
import io.github.getsmp.lorforandroid.api.ApiTopic;
import io.github.getsmp.lorforandroid.model.Topic;
import io.github.getsmp.lorforandroid.model.Topics;
import io.github.getsmp.lorforandroid.util.StringUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TopicFragmentGallery extends TopicFragmentCommon {
    @Bind(R.id.galleryTopicTitle) TextView title;
    @Bind(R.id.galleryTopicTags) TextView tags;
    @Bind(R.id.galleryTopicAuthor) TextView author;
    @Bind(R.id.galleryTopicImage) ImageView image;
    @Bind(R.id.galleryTopicMessage) TextView message;
    private Context context;
    private String url;
    private String imageUrl;

    public static TopicFragmentGallery newInstance(String url, String imageUrl) {
        TopicFragmentGallery fragment = new TopicFragmentGallery();
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("imageUrl", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
        imageUrl = getArguments().getString("imageUrl");
    }

    @Override
    protected void loadTopic() {
        loadingStarted();

        ApiManager.INSTANCE.apiRestAdapter.create(ApiTopic.class).getTopic(url, new Callback<Topics>() {
            @Override
            public void success(Topics topics, Response response) {
                Topic topic = topics.topic;
                title.setText(Html.fromHtml(topic.getTitle()));
                List<String> tagsList = topic.getTags();
                if (!tagsList.isEmpty()) {
                    tags.setVisibility(View.VISIBLE);
                    tags.setText(StringUtils.tagsFromStrings(tagsList));
                } else tags.setVisibility(View.GONE);

                Picasso.with(context).load(imageUrl).into((image));
                author.setText(topic.getAuthor().getNick());
                message.setText(Html.fromHtml(topic.getMessage()));
                message.setMovementMethod(LinkMovementMethod.getInstance());

                loadingEnded();
            }

            @Override
            public void failure(RetrofitError error) { loadingError(); }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_topic_gallery;
    }
}
