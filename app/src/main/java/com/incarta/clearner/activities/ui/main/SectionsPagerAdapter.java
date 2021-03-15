package com.incarta.clearner.activities.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.incarta.clearner.R;
import com.incarta.clearner.fragments.contents.Content1Fragment;
import com.incarta.clearner.fragments.contents.Content2Fragment;
import com.incarta.clearner.fragments.contents.Content3Fragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1/*, R.string.tab_text_2*/};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
//        ((ContentActivity)mContext).setPage(position);
        switch (position) {
            case 0:
                return Content1Fragment.newInstance(null,null);
            case 1:
                return Content2Fragment.newInstance(null,null);
            case 2:
                return Content3Fragment.newInstance(null,null);
        }
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 1;
    }
}