package cn.swun.sp.stu.news_swun.View;

import java.util.List;

/**
 * Create time : 2016/6/4.
 * Created by :saipeng
 * Description:
 */
public interface ShowNewsActivityView extends BaseView {
    void getDetailInfo(List<String> infos);

    void CamouflageRefresh();
}
