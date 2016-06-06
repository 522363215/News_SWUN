package cn.swun.sp.stu.news_swun.Fragment;

import java.util.List;

import cn.swun.sp.stu.news_swun.Bean.New;
import cn.swun.sp.stu.news_swun.View.BaseView;

/**
 * Create time : 2016/6/3.
 * Created by :saipeng
 * Description:
 */
public interface FragmentView extends BaseView {

    public void notifyDataChange(List<New> datas, int size);

    public void CamouflageRefresh();

    public void WrongToNetwork();
}
