package cn.swun.sp.stu.news_swun.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.swun.sp.stu.news_swun.Adapter.ViewPagerAdapter;
import cn.swun.sp.stu.news_swun.Presenter.ActivityPresenter;
import cn.swun.sp.stu.news_swun.R;
import cn.swun.sp.stu.news_swun.View.BaseView;
import cn.swun.sp.stu.news_swun.utils.Constant;
import cn.swun.sp.stu.news_swun.utils.Verdict_Net;

public class NewsHomeActivity extends BaseActivity<BaseView, ActivityPresenter> implements BaseView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.app_bar_image)
    ImageView appBarImage;

    private Bitmap mBitmap;

    private static final String TAG = "NewsHomeActivity";
    private int w;
    private int h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_home);
        ButterKnife.bind(this);

        initdata();
        initView();
        initEvent();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Constant.count = 0;

//        Picasso.with(this).load(Constant.bg_url).resize(w,250).centerCrop().placeholder(R.mipmap.swun_logo).error(R.mipmap.swun_logo).into(appBarImage);

     /*   Constant.net_state = Verdict_Net.getNetWorkStatus(this);
        if (Constant.net_state) {
            //有网络
            return;
        } else {
            //无网络
            Log.i(TAG, "no net work");
            Verdict_Net.showNetSetting(Constant.net_state, this);
        }*/

    }

    private void getWindowWH() {
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        w = metrics.widthPixels;
        h = metrics.heightPixels;
        Log.i(TAG, "w:" + w);
        Log.i(TAG, "h:" + h);
    }

    @Override
    public void onAttachedToWindow() {
        if (!Verdict_Net.getNetWorkStatus(this)) {
            Verdict_Net.showNetSetting(false, this);
        }
    }

    /**
     * 初始化数据
     */
    private void initdata() {
        //设置自己的toolbar
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(NewsHomeActivity.this, "fafa", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        collapsingToolbarLayout.setTitle("西南民族大学-新闻网");
        //初始化背景
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg);

        //给 CollapsingToolbarLayout 设置title
//        collapsingToolbarLayout.setContentScrim(new BitmapDrawable(BlurUtil.fastblur(getApplicationContext(), mBitmap, 150)));

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        getWindowWH();
    }

    /**
     * 初始化视图
     */
    private void initView() {

    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        //给appBar设置折叠监听
        appBarLayout.addOnOffsetChangedListener(new AppbarOffsetListener());

        //设置ViewPager与TabLayout之间的联动
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbarTab));
        toolbarTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }

    @Override
    protected ActivityPresenter initPresenter() {
        return new ActivityPresenter();
    }

    class AppbarOffsetListener implements AppBarLayout.OnOffsetChangedListener {

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (verticalOffset <= -appBarImage.getHeight() / 2) {
                collapsingToolbarLayout.setTitle("西南民族大学");
            } else {
                collapsingToolbarLayout.setTitle("");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_news_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (Constant.count++ == 0) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        } else {
            Constant.count = 0;
            super.onBackPressed();
        }
    }
}
