package cn.swun.sp.stu.news_swun.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.swun.sp.stu.news_swun.Activity.ShowNewsActivity;
import cn.swun.sp.stu.news_swun.Adapter.RecycleAdapter;
import cn.swun.sp.stu.news_swun.Bean.New;
import cn.swun.sp.stu.news_swun.Presenter.FragmentPrrsenter;
import cn.swun.sp.stu.news_swun.R;
import cn.swun.sp.stu.news_swun.utils.Constant;
import cn.swun.sp.stu.news_swun.utils.CrawlerUtils;
import cn.swun.sp.stu.news_swun.utils.Verdict_Net;

/**
 * Create time : 2016/6/3.
 * Created by :saipeng
 * Description:
 */
public class PageFragment extends BaseFragment<FragmentView, FragmentPrrsenter> implements FragmentView {

    //data
    private static final String TAG = "PageFragment";
    private int item;
    private List<New> datas;
    private int lastVisibleItem;
    private int w;
    private int h;
    public String next_url;

    private boolean isFristIn = true;

    //view
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;

    //event
    private RecycleAdapter adapter;

    private static final String[] urls = {
            "http://news.swun.edu.cn/ttxw.htm", "http://news.swun.edu.cn/xyxw.htm", "http://news.swun.edu.cn/xsxw.htm", "http://news.swun.edu.cn/mdrw.htm", "http://news.swun.edu.cn/jjxy.htm", "http://news.swun.edu.cn/llxx.htm", "http://news.swun.edu.cn/mtmd.htm"
    };


    public static Fragment newInstance(int item) {

        Bundle bundle = new Bundle();
        bundle.putInt("PAGE_ITEM", item);
        Fragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = getArguments().getInt("PAGE_ITEM");
        initArray();
        getWindowWH();
    }

    private void getWindowWH() {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        w = metrics.widthPixels;
        h = metrics.heightPixels;
        Log.i(TAG, "w:" + w);
        Log.i(TAG, "h:" + h);
    }

    @Override
    protected FragmentPrrsenter initPresenter() {
        return new FragmentPrrsenter();
    }

    private void initArray() {
        datas = new ArrayList<>();
     /*   datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("item:" + i);
        }*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_view, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);

        mRecyclerView.setOnScrollListener(new ToUpRefresh());

        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setProgressViewOffset(true, 0, 70);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.refresh_03);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.refresh_01, R.color.refresh_02, R.color.refresh_03, R.color.refresh_04);
        mSwipeRefreshLayout.setOnRefreshListener(new FragmentRefresh());

        if (mRecyclerView != null)

        {
            return view;
        }

        return null;
    }

    public void setDatas(List<New> datas) {
        this.datas = datas;
    }

    @Override
    public void notifyDataChange(List<New> datas, final int size) {
        setDatas(datas);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                ValideRefresh(true);
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "加载了" + size + "条新闻", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 伪装进行刷新
     */
    @Override
    public void CamouflageRefresh() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ValideRefresh(true);
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "没有网络，请先设置网络！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 网络异常
     */
    @Override
    public void WrongToNetwork() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ValideRefresh(true);
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class ToUpRefresh extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()) {
                //重置退出的点击次数
                Constant.count = 0;

                if (!Verdict_Net.getNetWorkStatus(getContext())) {
                    mRecyclerView.setNestedScrollingEnabled(false);
                    mSwipeRefreshLayout.setEnabled(false);
                    mSwipeRefreshLayout.setRefreshing(true);
                    presenter.CamouflageRefresh();
                }

                //防止当数据少的时候出现上拉和下拉一起出现的现象
                if (mSwipeRefreshLayout.isRefreshing()) {
                    return;
                }

                // 请求网络数据
                if (CrawlerUtils.getNext_url().equals("")) {
                    Toast.makeText(getContext(), "没有更多的消息可以下载啦！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "上拉刷新");
                    mRecyclerView.setNestedScrollingEnabled(false);
                    mSwipeRefreshLayout.setEnabled(false);
                    mSwipeRefreshLayout.setRefreshing(true);
                    presenter.refreshData(datas, CrawlerUtils.getNext_url());
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            Log.i(TAG, String.valueOf(lastVisibleItem));
        }
    }

    private class FragmentRefresh implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {

            //重置退出的点击次数
            Constant.count = 0;
            Log.i(TAG, "下拉刷新");

            if (Verdict_Net.getNetWorkStatus(getContext())) {
                //如果有网则刷新
                //防止刷新时又触发刷新
                ValideRefresh(false);
                //请求数据
                datas.clear();
                presenter.refreshData(datas, urls[item]);
            } else {
                ValideRefresh(false);
                presenter.CamouflageRefresh();
            }


        }


    }

    private void ValideRefresh(boolean disallowRefresh) {
        mSwipeRefreshLayout.setEnabled(disallowRefresh);
        mRecyclerView.setNestedScrollingEnabled(disallowRefresh);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecycleView();

    }

    private void initRecycleView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        adapter = new RecycleAdapter(getContext(), datas);
        mRecyclerView.setAdapter(adapter);

        adapter.setmRecycleViewOnClickListener(recycleViewOnClickListener);
    }

    private RecycleAdapter.RecycleViewOnClickListener recycleViewOnClickListener = new RecycleAdapter.RecycleViewOnClickListener() {
        @Override
        public void onClick(View v, int position) {

            Intent intent = new Intent(getContext(), ShowNewsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("INFOS", datas.get(position).getInfos());
            bundle.putString("TITLE", datas.get(position).getTitle());
            intent.putExtras(bundle);
            startActivity(intent);

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, item + "onResume");
        Constant.net_state = Verdict_Net.getNetWorkStatus(getContext());
        if (Constant.net_state && isFristIn) {
            isFristIn = false;
            //有网络
            initNews();
        } else {
            //无网络
            Log.i(TAG, "no net work");
        }

    }


    private void initNews() {
        ValideRefresh(false);
        mSwipeRefreshLayout.setRefreshing(true);
        presenter.refreshData(datas, urls[item]);
    }


}
