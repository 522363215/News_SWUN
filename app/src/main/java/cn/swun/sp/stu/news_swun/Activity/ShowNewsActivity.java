package cn.swun.sp.stu.news_swun.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.swun.sp.stu.news_swun.Presenter.ShowDetailInfoPresenter;
import cn.swun.sp.stu.news_swun.R;
import cn.swun.sp.stu.news_swun.View.CircularProgress;
import cn.swun.sp.stu.news_swun.View.ShowNewsActivityView;

/**
 * Create time : 2016/6/4.
 * Created by :saipeng
 * Description:
 */
public class ShowNewsActivity extends BaseActivity<ShowNewsActivityView, ShowDetailInfoPresenter> implements ShowNewsActivityView {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.content)
    LinearLayout content;
    @Bind(R.id.circular_progress)
    CircularProgress circularProgress;
    @Bind(R.id.app_bar_image)
    ImageView appBarImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_app_bar)
    CollapsingToolbarLayout collapsingAppBar;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.col)
    CoordinatorLayout col;
    private String new_url;

    private List<String> infos;
    private LayoutInflater inflater;
    private String title_text;

    public List<String> getInfos() {
        return infos;
    }

    public void setInfos(List<String> infos) {
        this.infos = infos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_detail_new2);
        ButterKnife.bind(this);


        //对数据进行初始化
        initData();
        //初始化控件
        initView();
        //初始化事件
        initEvent();
    }

    private void initView() {

        title.setText(title_text);
        time.setText(infos.get(2));

        for (int i = 3; i < infos.size(); i++) {
            if (infos.get(i).contains("http://") && (infos.get(i).endsWith(".jpg") || infos.get(i).endsWith(".png") || infos.get(i).endsWith(".jpeg"))) {
                ImageView iv = (ImageView) inflater.inflate(R.layout.show_new_imageview, null);
                Picasso.with(ShowNewsActivity.this).load(infos.get(i)).resize(600, 600).centerCrop().placeholder(R.mipmap.swun_logo).error(R.mipmap.swun_logo).into(iv);
                content.addView(iv);
            } else {
                TextView tv = (TextView) inflater.inflate(R.layout.show_new_textview, null);
                tv.setText("        " + infos.get(i).trim());
                content.addView(tv);
            }
        }

    }

    private void initData() {
//        circularProgress.setVisibility(View.VISIBLE);
        ValidUrl();

        inflater = LayoutInflater.from(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        collapsingAppBar.setTitle(title_text);
        Picasso.with(this).load(infos.get(1)).resize(800, 800).centerCrop().placeholder(R.mipmap.swun_logo).error(R.mipmap.swun_logo).into(appBarImage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void initEvent() {
    }

    private void ValidUrl() {

        title_text = getIntent().getExtras().getString("TITLE");

        ArrayList<String> results = getIntent().getExtras().getStringArrayList("INFOS");
        if (results.size() == 0 || results == null) {
            infos = null;
        } else {
            infos = results;
        }
    }

    @Override
    public void onAttachedToWindow() {
     /*   if (Verdict_Net.getNetWorkStatus(this)) {
            presenter.getDetailToNew(new_url);
        } else {
            presenter.CamouflageRefresh();
        }*/
    }

    @Override
    protected ShowDetailInfoPresenter initPresenter() {
        return new ShowDetailInfoPresenter();
    }

    @Override
    public void getDetailInfo(final List<String> infos) {
        setInfos(infos);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                title.setText(infos.get(0));
                time.setText(infos.get(1));

                for (int i = 2; i < infos.size(); i++) {
                    if (infos.get(i).contains("http://") && (infos.get(i).endsWith(".jpg") || infos.get(i).endsWith(".png") || infos.get(i).endsWith(".jpeg"))) {
                        ImageView iv = (ImageView) inflater.inflate(R.layout.show_new_imageview, null);
                        Picasso.with(ShowNewsActivity.this).load(infos.get(i)).resize(400, 400).centerCrop().placeholder(R.mipmap.swun_logo).error(R.mipmap.swun_logo).into(iv);
                        content.addView(iv);
                    } else {
                        TextView tv = (TextView) inflater.inflate(R.layout.show_new_textview, null);
                        tv.setText("        " + infos.get(i).trim());
                        content.addView(tv);
                    }
                }
                circularProgress.setVisibility(View.GONE);

            }
        }, 700);

    }

    @Override
    public void CamouflageRefresh() {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                circularProgress.setVisibility(View.GONE);
                Toast.makeText(ShowNewsActivity.this, "没有网络，请检查网络连接！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
