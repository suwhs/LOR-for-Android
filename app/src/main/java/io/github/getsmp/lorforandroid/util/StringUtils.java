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

package io.github.getsmp.lorforandroid.util;

import android.text.Spanned;
import android.text.TextUtils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StringUtils {
    public static String getDate(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US).format(date);
    }

    /*
    * Removes part of url after "?" including it
    * @param url /forum/general/12336213?lastmod=1454852134842
    * @return /forum/general/12336213
    * */
    public static String removeParams(String url) {
        int length = url.length() - url.replace("/", "").length();
        if (length > 3) {
            return url.substring(0, url.lastIndexOf("/"));
        } else if (url.contains("?")) {
            return url.split("\\?")[0];
        }
        return url;
    }

    public static String tagsFromStrings(List<String> in) {
        StringBuilder sb = new StringBuilder();
        for (String s : in) {
            sb.append("#").append(s).append(" ");
        }
        return sb.toString();
    }

    public static String tagsFromElements(Elements e) {
        StringBuilder sb = new StringBuilder();
        for (Element tag : e) {
            sb.append("#").append(tag.ownText()).append(" ");
        }
        return sb.toString();
    }

    public static String removeSectionName(String in) {
        return in.substring(in.indexOf("-") + 2, in.length());
    }

    public static CharSequence removeLineBreak(Spanned spanned) {
        return spanned.subSequence(0, spanned.length() - 2);
    }
}
