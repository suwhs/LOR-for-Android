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

package io.github.getsmp.lorforandroid.ui.section.forum

import io.github.getsmp.lorforandroid.ui.section.ItemFactory
import org.jsoup.nodes.Element

class ForumOverviewItemFactory : ItemFactory {
    override fun prepareItems(body: Element, items: MutableList<Any>) {
        val sections = body.select("div#bd").select("ul").first().select("li")
        for (section in sections) {
            items.add(ForumOverviewItem(
                    url = section.select("a").first().attr("href").replace("/forum/", "").replace("/", ""),
                    name = section.select("a").first().ownText()))
        }
    }
}
