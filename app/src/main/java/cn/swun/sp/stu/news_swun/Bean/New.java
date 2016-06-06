package cn.swun.sp.stu.news_swun.Bean;

import java.util.ArrayList;

/**
 * Create time : 2016/6/4.
 * Created by :saipeng
 * Description:
 */
public class New {
    private String time;
    private String title;
    private String url;
    private ArrayList<String> infos;


    public New(String time, String title, String url, ArrayList<String> infos) {
        this.time = time;
        this.title = title;
        this.url = url;
        this.infos = infos;
    }

    public ArrayList<String> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<String> infos) {
        this.infos = infos;
    }

    @Override
    public String toString() {
        return "New{" +
                "time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", infos=" + infos +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
