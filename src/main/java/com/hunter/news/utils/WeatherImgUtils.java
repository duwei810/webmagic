package com.hunter.news.utils;

import com.hunter.news.bean.Picture;
import com.hunter.news.dao.HunterDao;
import com.hunter.news.dao.HunterDaoImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by duwei on 2017/8/7.
 */
public class WeatherImgUtils {
    public static void main(String[] args) throws IOException {
        HunterDao hunterDao = new HunterDaoImpl();
        String url = "http://www.weather.com.cn/static/html/legend.shtml";
        Document doc = Jsoup.connect(url).get();
        Elements weatherindex = doc.getElementsByClass("left_weatherindex");
        Document weatherindexDoc = Jsoup.parse(weatherindex.toString());
        Elements trTag = weatherindexDoc.getElementsByTag("tr");
        for(int i=1;i<5;i++){
            for(int j=1;j<16;j=j+2){
                Node nodeImg = trTag.get(i).childNodes().get(j).childNodes().get(2).childNodes().get(0).childNodes().get(0);
                Node nodeInfo = trTag.get(i).childNodes().get(j).childNodes().get(4).childNodes().get(0).childNodes().get(0);
                String img = nodeImg.attributes().get("src");
                String message = ((TextNode)nodeInfo).text();
                Picture picture = new Picture();
                picture.setMessage(message);
                picture.setImg(img);
                hunterDao.saveWeatherImg(picture);
            }
        }
        Node lastImg = trTag.get(5).childNodes().get(1).childNodes().get(2).childNodes().get(0).childNodes().get(0);
        Node lastInfo = trTag.get(5).childNodes().get(1).childNodes().get(4).childNodes().get(0).childNodes().get(0);
        Picture picture = new Picture();
        picture.setMessage(((TextNode)lastInfo).text());
        picture.setImg(lastImg.attributes().get("src"));
        hunterDao.saveWeatherImg(picture);

    }
}
