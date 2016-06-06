package cn.swun.sp.stu.news_swun.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.swun.sp.stu.news_swun.Bean.New;

/**
 * Create time : 2016/6/4.
 * Created by :saipeng
 * Description:
 */
public class CrawlerUtils {

    private static final String TAG = "CRAWLER";
    private static String next_url;

    public static String getNext_url() {
        return next_url;
    }

    public static void setNext_url(String next_url) {
        CrawlerUtils.next_url = next_url;
    }


    public static ArrayList<String> getDetailToNew(String url) {
        Document doc = getDocument(url);
        if (doc == null) return null;
        return getDetailContent(doc);
    }

    @NonNull
    private static ArrayList<String> getDetailContent(Document doc) {
        ArrayList<String> infos = new ArrayList<>();

        //先获取第一段内容
        infos.add(doc.select("div.page_detail").first().getElementsByTag("p").first().text());
        //再获取第一张图片的url
        infos.add(doc.getElementsByTag("img").first().attr("abs:src"));
        //解析详细的时间
        infos.add(doc.select("div.newsTime").first().getElementsByTag("span").first().text());

        //接着对详细内容进行解析
        Elements elements = doc.select("div.page_detail").first().getElementsByTag("p");
        Log.i(TAG, "elements:" + elements.size());
        for (Element e : elements) {
            Element img = e.getElementsByTag("img").first();
            if (img != null) {
                infos.add(img.attr("abs:src"));
            } else {
                infos.add(e.text());
            }
        }
        for (String s : infos) {
            Log.i(TAG, s);
        }
        return infos;
    }

    //得到新闻的第一张图片资源以及第一段文本
    public static String[] getUrlByTitle(String url) {
        Document doc = getDocument(url);
        if (doc == null) return null;

        String[] result = new String[2];

        result[0] = doc.select("div.page_detail").first().getElementsByTag("p").first().text();

        result[1] = doc.getElementsByTag("img").first().attr("abs:src");

        return result;
    }

    public static List<New> getNewsByType(String url) {

        Document doc = getDocument(url);
        if (doc == null) return null;


        Elements elements = doc.select("li[id]");
        Log.i(TAG, "elements:" + elements.size());

        //抓取下一页的url
        Element e = doc.select("a.Next").first();
        if (e != null) {
            next_url = e.attr("abs:href");
            Log.i(TAG, "next_url:" + next_url);
        } else {
            next_url = "";
        }

        if (elements.size() != 0 && elements != null) {
            List<New> news = new ArrayList<>();
            for (Element element : elements) {
                String time = element.getElementsByTag("span").text();
                String content = element.getElementsByTag("a").text();
                String href = element.getElementsByTag("a").attr("abs:href");
                ArrayList<String> infos = getDetailToNew(href);

                news.add(new New(time, content, href, infos));
            }
            return news;
        }
        return null;
    }

    @Nullable
    private static Document getDocument(String url) {
        Connection conn = Jsoup.connect(url);
        conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
        Document doc = null;
        try {
            doc = conn.cookie("cookie", "sp").timeout(3000).get();
            if (doc == null) {
                Log.i(TAG, "document is null");
                return null;
            }
        } catch (IOException e) {
              Log.i(TAG, "解析出错!");
            e.printStackTrace();
            return null;
        }
        return doc;
    }

}
