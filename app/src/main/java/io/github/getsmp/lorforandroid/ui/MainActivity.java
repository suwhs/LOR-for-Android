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

package io.github.getsmp.lorforandroid.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.base.ThemeActivity;
import io.github.getsmp.lorforandroid.ui.section.forum.ForumOverviewFragment;
import io.github.getsmp.lorforandroid.ui.section.forum.section.ForumSectionActivity;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryFilterEnum;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryFragment;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryItem;
import io.github.getsmp.lorforandroid.ui.section.news.NewsFragment;
import io.github.getsmp.lorforandroid.ui.section.tracker.TrackerFilterEnum;
import io.github.getsmp.lorforandroid.ui.section.tracker.TrackerFragment;
import io.github.getsmp.lorforandroid.ui.topic.TopicActivity;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;

public class MainActivity extends ThemeActivity implements NavigationView.OnNavigationItemSelectedListener, ItemClickCallback {
    private static final String NAV_ITEM_ID = "NAV_ITEM_ID";
    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
    @Bind(R.id.navigationView) NavigationView navigationView;
    private int currentNavigationItemId;
    private int requestedNavigationItemId;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupActionBar(this);

        if (savedInstanceState == null) {
            if (getIntent().getBooleanExtra(getString(R.string.intent_settings), false)) {
                currentNavigationItemId = R.id.drawer_settings;
            } else {
                currentNavigationItemId = R.id.drawer_news;
            }
        } else {
            currentNavigationItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        navigationView.setNavigationItemSelectedListener(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (requestedNavigationItemId != currentNavigationItemId) {
                    currentNavigationItemId = requestedNavigationItemId;
                    navigate(requestedNavigationItemId);
                }
            }
        });
        drawerToggle.syncState();

        onNavigationItemSelected(navigationView.getMenu().findItem(currentNavigationItemId));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        requestedNavigationItemId = menuItem.getItemId();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            navigate(requestedNavigationItemId);
        }
        return true;
    }

    private void navigate(int selection) {
        actionBar.setDisplayShowCustomEnabled(false);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;
        String tag;
        switch (selection) {
            case R.id.drawer_news:
                actionBar.setTitle(R.string.drawer_news);
                tag = "news";
                fragment = fm.findFragmentByTag(tag);
                if (fragment == null) fragment = new NewsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit();
                break;
            case R.id.drawer_gallery:
                actionBar.setTitle(R.string.drawer_gallery);
                tag = "gallery";
                fragment = fm.findFragmentByTag(tag);
                if (fragment == null) fragment = GalleryFragment.newInstance(GalleryFilterEnum.all);
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit();
                break;
            case R.id.drawer_tracker:
                actionBar.setTitle(R.string.drawer_tracker);
                tag = "tracker";
                fragment = fm.findFragmentByTag(tag);
                if (fragment == null) fragment = TrackerFragment.newInstance(TrackerFilterEnum.all);
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit();
                break;
            case R.id.drawer_forum:
                actionBar.setTitle(R.string.drawer_forum);
                tag = "forum";
                fragment = fm.findFragmentByTag(tag);
                if (fragment == null) fragment = new ForumOverviewFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit();
                break;
            case R.id.drawer_settings:
                actionBar.setTitle(R.string.drawer_settings);
                tag = "settings";
                fragment = fm.findFragmentByTag(tag);
                if (fragment == null) fragment = new SettingsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit();
                break;
        }
        fm.executePendingTransactions();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
        drawerToggle.onConfigurationChanged(newConfiguration);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, currentNavigationItemId);
    }

    @Override
    public void onTopicRequested(String url) {
        Intent intent = new Intent(this, TopicActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void onGalleryTopicRequested(GalleryItem item) {
        Intent intent = new Intent(this, TopicActivity.class);
        intent.putExtra("url", item.getUrl());
        intent.putExtra("imageUrl", item.getImageUrl());
        startActivity(intent);
    }

    @Override
    public void onForumSectionRequested(String group, String name) {
        Intent intent = new Intent(MainActivity.this, ForumSectionActivity.class);
        intent.putExtra("group", group);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
