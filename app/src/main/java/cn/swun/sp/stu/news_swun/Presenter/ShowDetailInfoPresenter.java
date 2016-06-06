package cn.swun.sp.stu.news_swun.Presenter;

import java.util.List;

import cn.swun.sp.stu.news_swun.View.ShowNewsActivityView;
import cn.swun.sp.stu.news_swun.utils.CrawlerUtils;

/**
 * Create time : 2016/6/4.
 * Created by :saipeng
 * Description:
 */
public class ShowDetailInfoPresenter extends BasePresenter<ShowNewsActivityView> {

    private static final String TAG = "ShowDetailInfoPresenter";

    public ShowDetailInfoPresenter() {
    }

    public void getDetailToNew(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> infos = CrawlerUtils.getDetailToNew(url);
                view.getDetailInfo(infos);
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
