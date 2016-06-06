package cn.swun.sp.stu.news_swun.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.swun.sp.stu.news_swun.Fragment.PageFragment;

/**
 * Create time : 2016/6/3.
 * Created by :saipeng
 * Description:
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 7;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = PageFragment.newInstance(position);

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
