/*
 * Copyright (c) 2016 getsmp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.getsmp.lorforandroid.ui.section.forum;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.ItemFactory;
import io.github.getsmp.lorforandroid.ui.section.SectionFragment;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class ForumOverviewFragment extends SectionFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public int getItemsPerPage() {
        return 0;
    }

    @Override
    public int getMaxOffset() {
        return 0;
    }

    @Override
    public String getPath() {
        return "forum";
    }

    @Override
    public RequestParams getRequestParams() {
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected ItemFactory getItemFactory() {
        return new ForumOverviewItemFactory();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ForumOverviewAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        ForumOverviewItem item = (ForumOverviewItem) items.get(position);
        if (StringUtils.isClub(item.getUrl())) {
            Toast.makeText(context, R.string.error_access_denied, Toast.LENGTH_SHORT).show();
        } else {
            ((ItemClickCallback) context).onForumSectionRequested(item.getUrl(), item.getName());
        }
    }

    @Override
    protected boolean loadMoreAllowed() {
        return false;
    }

    @Override
    protected boolean showDividers() {
        return false;
    }

}
