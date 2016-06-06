package cn.swun.sp.stu.news_swun.Presenter;

/**
 * Create time : 2016/6/1.
 * Created by :saipeng
 * Description: 解释器的基类
 */
public abstract class BasePresenter<T> {
    public T view;

    public void attach(T baseView) {
        view = baseView;
    }

    public void detach() {
        if (view != null) {
            view = null;
        }
    }
}
