package cn.swun.sp.stu.news_swun.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.swun.sp.stu.news_swun.Presenter.BasePresenter;
import cn.swun.sp.stu.news_swun.utils.Constant;

/**
 * Create time : 2016/6/3.
 * Created by :saipeng
 * Description:
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {
    public T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    protected abstract T initPresenter();

    @Override
    public void onResume() {
        super.onResume();
        Constant.count = 0;
        presenter.attach((V) this);
    }

    @Override
    public void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }
}
