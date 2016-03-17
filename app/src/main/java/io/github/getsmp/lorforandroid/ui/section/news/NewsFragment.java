/*
 * Copyright 2016 getsmp
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
import android.text.Html;

import com.loopj.android.http.RequestParams;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.github.getsmp.lorforandroid.ui.section.ItemCommon;
import io.github.getsmp.lorforandroid.ui.section.SectionCommon;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class NewsFragment extends SectionCommon {
    @Override
    protected void generateDataSet(Element responseBody) {
        Elements articles = responseBody.select("article");
        for (Element article : articles) {
            if (article.hasClass("mini-news")) {
                // Mini-news article
                items.add(new MiniNewsItem(
                        article.select("a[href^=/news/]").first().attr("href").substring(1),
                        article.select("a[href^=/news/]").first().ownText(),
                        Html.fromHtml(article.select("a").first().nextSibling().toString()).toString().replaceAll("[()]", "")
                ));
            } else {
                // Standard article
                items.add(new ItemCommon(
                        article.select("h2 > a[href^=/news/]").first().attr("href").substring(1),
                        article.select("h2 > a[href^=/news/]").first().ownText(),
                        StringUtils.removeSectionName(article.select("div.group").first().text()),
                        StringUtils.tagsFromElements(article.select("a.tag")),
                        article.select("time").first().ownText(),
                        article.select("a[itemprop^=creator], div.sign:contains(anonymous)").first().ownText().replace(" ()", ""),
                        article.select("div.nav > a[href$=#comments]:eq(0)").first().ownText()
                ));
            }
        }
    }

    @Override
    public int getItemsPerPage() {
        return 20;
    }

    @Override
    public int getMaxOffset() {
        return 200;
    }

    @Override
    public String getPath() {
        return "news";
    }

    @Override
    public RequestParams getRequestParams() {
        return new RequestParams("offset", offset);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new NewsAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        Object item = items.get(position);
        String url = null;
        if (item instanceof MiniNewsItem) {
            url = ((MiniNewsItem) items.get(position)).getUrl();
        } else if (item instanceof ItemCommon) {
            url = ((ItemCommon) items.get(position)).getUrl();
        } else
            throw new ClassCastException("Object cannot be cast neither to MiniNewsItem nor to ItemCommon.");

        ((ItemClickCallback) context).onTopicRequested(url);
    }
}
