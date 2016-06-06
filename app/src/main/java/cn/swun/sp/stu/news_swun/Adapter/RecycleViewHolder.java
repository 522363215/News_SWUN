package cn.swun.sp.stu.news_swun.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.swun.sp.stu.news_swun.R;

/**
 * Create time : 2016/6/3.
 * Created by :saipeng
 * Description:
 */
public class RecycleViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_content;
    public TextView tv_time;
    public TextView tv_detail_content;
    public ImageView imageView;
    public RelativeLayout mRelativeLayout;

    public RecycleViewHolder(View itemView) {
        super(itemView);
        tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        tv_detail_content = (TextView) itemView.findViewById(R.id.detail_content);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.show_content);

    }

}