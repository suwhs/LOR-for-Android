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

package io.github.getsmp.lorforandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.github.getsmp.lorforandroid.R;

public class PreferenceUtils {
    public static boolean shouldLoadImagesOnMobileData(Context context) {
        return getPrefs(context).getBoolean(context.getString(R.string.pref_load_images), false);
    }

    public static boolean shouldLoadImagesNow(Context context) {
        if (NetworkUtils.isMobileData(context)) {
            return shouldLoadImagesOnMobileData(context);
        }
        return true;
    }

    private static SharedPreferences getPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
