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

package net.voxelplanet.lorforandroid.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.api.Adapter;
import net.voxelplanet.lorforandroid.api.ApiUser;
import net.voxelplanet.lorforandroid.model.User;
import net.voxelplanet.lorforandroid.util.StringUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserFragment extends Fragment {
    //private ImageView avatar;
    private TextView id;
    private TextView nick;
    private TextView url;
    private TextView regDate;
    private TextView lastVisit;
    private TextView status;
    private TextView score;
    private TextView favTags;
    private TextView ignoredTags;
    private TextView ignoredUsers;
    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        //avatar = (ImageView) view.findViewById(R.id.userAvatar);
        id = (TextView) view.findViewById(R.id.userId);
        nick = (TextView) view.findViewById(R.id.userNick);
        url = (TextView) view.findViewById(R.id.userUrl);
        regDate = (TextView) view.findViewById(R.id.userRegDate);
        lastVisit = (TextView) view.findViewById(R.id.userLastVisit);
        status = (TextView) view.findViewById(R.id.userStatus);
        score = (TextView) view.findViewById(R.id.userScore);
        favTags = (TextView) view.findViewById(R.id.userFavTags);
        ignoredTags = (TextView) view.findViewById(R.id.userIgnoredTags);
        ignoredUsers = (TextView) view.findViewById(R.id.userIgnoredUsers);
        return view;
    }

    public void getInfo() {
        // TODO: Implement user authorization
        ApiUser apiUser = Adapter.restAdapter.create(ApiUser.class);
        apiUser.getUser("anonymous", new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                //avatar.setImageURI(Uri.parse(user.getAvatar()));
                id.setText(user.getId());
                nick.setText(user.getNick());
                url.setText(user.getUrl());
                regDate.setText(StringUtils.getDate(user.getRegDate()));
                lastVisit.setText(StringUtils.getDate(user.getLastVisit()));
                status.setText(user.getStatus());
                score.setText(user.getScore());
                favTags.setText(TextUtils.join(", ", user.getFavTags()));
                ignoredTags.setText(TextUtils.join(", ", user.getIgnoredTags()));
                ignoredUsers.setText(TextUtils.join(", ", user.getIgnoredUsers()));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(activity, R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
