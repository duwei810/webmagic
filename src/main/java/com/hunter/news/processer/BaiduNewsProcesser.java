package com.hunter.news.processer;

import com.hunter.news.utils.CodeUtils;
import com.hunter.news.utils.ExtractUtils;
import com.hunter.news.bean.News;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by duwei on 2017/8/1.
 */
public class BaiduNewsProcesser implements PageProcessor {

    /**
     * 抓取网站的相关配置，编码，抓取间隔，重试次数等
     */
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0")
            .setTimeOut(400);

    public void process(Page page) {
        String type = CodeUtils.decode(page.getUrl().toString().split("%20")[1]);
        List<News> newsList = new ArrayList<News>();
        for (int i = 1; i <= 20; i++) {
            News news = new News();
            String dateStr = ExtractUtils.getDate(page, i);
            news.setUrl(ExtractUtils.getUrl(page, i));
            news.setTitle(ExtractUtils.getTitle(page, i));
            news.setType(type);
            news.setSource(ExtractUtils.getSource(page, i));
            news.setTime(CodeUtils.getDate(dateStr));
            news.setContent(ExtractUtils.getContent(page, i));
            String img = ExtractUtils.getImg(page, i);
            if(img != null){
                news.setImg(img);
            }
            newsList.add(news);

           /* System.out.println(ExtractUtils.getUrl(page, i));
            System.out.println(ExtractUtils.getTitle(page, i));
            System.out.println(ExtractUtils.getSource(page, i));
            System.out.println(ExtractUtils.getDate(page, i));
            System.out.println(ExtractUtils.getContent(page, i));
            System.out.println(ExtractUtils.getImg(page, i));*/

        }
        // page.addTargetRequests(page.getHtml().xpath("//div[@id=\"content_left\"]/div/div[@class=\"result\"]/h3/a/@href").all());
        page.putField("newsList", newsList);

    }

    public Site getSite() {
        return site;
    }

}
