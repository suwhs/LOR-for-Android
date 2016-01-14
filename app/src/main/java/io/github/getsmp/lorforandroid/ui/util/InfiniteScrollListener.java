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

package io.github.getsmp.lorforandroid.ui.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {
    private final LinearLayoutManager layoutManager;
    private final int visibleThreshold = 5;
    private boolean loading = true;
    private int previousTotal = 0;

    public InfiniteScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visible = recyclerView.getChildCount();
        int total = layoutManager.getItemCount();
        int firstVisible = layoutManager.findFirstVisibleItemPosition();

        if (loading && (total > previousTotal)) {
            if ((visible + firstVisible >= total)) {
                loading = false;
                previousTotal = total;
            }
        }

        if (!loading && (total - visible) <= (firstVisible + visibleThreshold)) {
            onLoadMore();
            loading = true;
        }
    }

    public abstract void onLoadMore();
}