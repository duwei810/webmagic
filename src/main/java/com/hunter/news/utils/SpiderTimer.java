package com.hunter.news.utils;

import com.hunter.news.pipeline.DBPipeline;
import com.hunter.news.processer.BaiduNewsProcesser;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by duwei on 2017/8/4.
 */
public class SpiderTimer {

    private static Timer timer ;

    public SpiderTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new NewsTask(), 0, 1000*60*60);  //一小时执行一次
        //timer.scheduleAtFixedRate(new WeatherTask(), 0, 1000*60*60*24); //一天执行一次
    }


    class NewsTask extends TimerTask {

        public void run() {
            System.out.println("开始爬取新闻");
            String str1 = CodeUtils.encode(TestUtils.city);
            PriorityScheduler scheduler = new PriorityScheduler();
            Spider spider = Spider.create(new BaiduNewsProcesser()).addPipeline(new DBPipeline()).scheduler(scheduler);
            for(String str : TestUtils.type){
               String str2 = CodeUtils.encode(str);
               String url = "http://news.baidu.com/ns?cl=2&rn=20&tn=news&word=" + str1 + "%20" + str2;
               scheduler.push(new Request(url),spider);
            }
            spider.run();
            //Spider.create(new BaiduNewsProcesser()).addPipeline(new DBPipeline()).addUrl(url).run();
            //timer.cancel(); //Terminate the timer thread
        }
    }

    class WeatherTask extends TimerTask{

        public void run() {
            System.out.println("开始获取天气");
            XmlParser.getWeatherInfo();
        }
    }

    public static void main(String args[]) {
        System.out.println("Task scheduled.");
        new SpiderTimer();
    }
}
