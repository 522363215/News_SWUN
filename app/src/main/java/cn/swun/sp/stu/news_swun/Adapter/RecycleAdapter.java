package cn.swun.sp.stu.news_swun.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.swun.sp.stu.news_swun.Bean.New;
import cn.swun.sp.stu.news_swun.R;

/**
 * Create time : 2016/6/3.
 * Created by :saipeng
 * Description:
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleViewHolder> {

    private Context context;
    private List<New> datas;


    private RecycleViewOnClickListener mRecycleViewOnClickListener;

    public RecycleViewOnClickListener getmRecycleViewOnClickListener() {
        if (mRecycleViewOnClickListener != null) {
            return mRecycleViewOnClickListener;
        }
        return null;
    }

    public void setmRecycleViewOnClickListener(RecycleViewOnClickListener mRecycleViewOnClickListener) {
        this.mRecycleViewOnClickListener = mRecycleViewOnClickListener;
    }

    public RecycleAdapter(Context context, List<New> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_item, parent, false);

        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, final int position) {
        holder.tv_content.setText(datas.get(position).getTitle());
        holder.tv_time.setText(datas.get(position).getInfos().get(2));
        holder.tv_detail_content.setText(datas.get(position).getInfos().get(0));
        Picasso.with(context).load(datas.get(position).getInfos().get(1)).resize(200, 200).centerCrop().placeholder(R.mipmap.swun_logo).error(R.mipmap.swun_logo).into(holder.imageView);

        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecycleViewOnClickListener != null) {
                    mRecycleViewOnClickListener.onClick(v, position);
                }
            }
        });

    }

    public interface RecycleViewOnClickListener {
        void onClick(View v, int position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public List<New> getDatas() {
        return datas;
    }

    public void setDatas(List<New> datas) {
        this.datas = datas;
    }
}
