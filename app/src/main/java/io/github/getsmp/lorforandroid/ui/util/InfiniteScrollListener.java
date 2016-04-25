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

    public void reset() {
        loading = true;
        previousTotal = 0;
    }
}