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

package io.github.getsmp.lorforandroid.ui.section.tracker

import android.text.Html
import io.github.getsmp.lorforandroid.ui.section.Item
import io.github.getsmp.lorforandroid.ui.section.ItemFactory
import io.github.getsmp.lorforandroid.util.StringUtils
import org.jsoup.nodes.Element

class TrackerItemFactory : ItemFactory {
    override fun prepareItems(body: Element, items: MutableList<Any>) {
        val topics = body.select("tbody > tr")
        for (topic in topics) {
            items.add(Item(
                    url = topic.select("td:eq(1)").select("a").first().attr("href").substring(1),
                    title = Html.fromHtml(topic.select("td:eq(1)").select("a").first().ownText()).toString(),
                    groupTitle = topic.select("a.secondary").first().ownText(),
                    tags = StringUtils.tagsFromElements(topic.select("span.tag")),
                    date = topic.select("time").first().ownText(),
                    author = topic.select("td.dateinterval > time").first().nextSibling().toString().replace(", ", ""),
                    comments = StringUtils.numericStringToHumanReadable(topic.select("td.numbers").first().ownText())))
        }
    }
}
