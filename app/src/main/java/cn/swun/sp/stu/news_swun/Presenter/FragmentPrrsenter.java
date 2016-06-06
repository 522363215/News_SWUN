package cn.swun.sp.stu.news_swun.Presenter;

import android.util.Log;

import java.util.List;

import cn.swun.sp.stu.news_swun.Bean.New;
import cn.swun.sp.stu.news_swun.Fragment.FragmentView;
import cn.swun.sp.stu.news_swun.utils.CrawlerUtils;

/**
 * Create time : 2016/6/3.
 * Created by :saipeng
 * Description:
 */
public class FragmentPrrsenter extends BasePresenter<FragmentView> {

    private static final String TAG = "FragmentPrrsenter";

    public FragmentPrrsenter() {
    }

    public void refreshData(final List<New> news, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<New> lists = CrawlerUtils.getNewsByType(url);
                if (lists != null) {
                    Log.i(TAG, "lists:" + lists.size());
                    news.addAll(lists);
                    view.notifyDataChange(news, lists.size());
                } else {
                    view.WrongToNetwork();
                }

            }
        }).start();
    }

    /**
     * 伪装刷新
     */
    public void CamouflageRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    view.CamouflageRefresh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
