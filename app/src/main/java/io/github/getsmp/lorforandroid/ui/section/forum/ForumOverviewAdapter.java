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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;


public class ForumOverviewAdapter extends RecyclerView.Adapter<ForumOverviewViewHolder> {
    private List<ForumOverviewItem> sections;

    public ForumOverviewAdapter(List<ForumOverviewItem> sections) {
        this.sections = sections;
    }

    @Override
    public ForumOverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false);
        return new ForumOverviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForumOverviewViewHolder v, int position) {
        v.itemName.setText(sections.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }
}
