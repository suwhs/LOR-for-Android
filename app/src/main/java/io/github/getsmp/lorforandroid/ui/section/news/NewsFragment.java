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

package io.github.getsmp.lorforandroid.ui.section.news;

import android.support.v7.widget.RecyclerView;

import io.github.getsmp.lorforandroid.ui.base.BaseTopicsFragment;
import io.github.getsmp.lorforandroid.ui.section.SectionEnum;

public class NewsFragment extends BaseTopicsFragment {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new NewsAdapter(items);
    }

    @Override
    protected SectionEnum getSection() {
        return SectionEnum.news;
    }
}
