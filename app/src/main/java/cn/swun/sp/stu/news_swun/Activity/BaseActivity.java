package cn.swun.sp.stu.news_swun.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.swun.sp.stu.news_swun.Presenter.BasePresenter;

/**
 * Create time : 2016/6/1.
 * Created by :saipeng
 * Description:
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V) this);
    }

    @Override
    protected void onStop() {
        presenter.detach();
        super.onStop();
    }

    protected abstract T initPresenter();
}
