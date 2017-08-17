package com.hunter.news.dao;

import com.hunter.news.bean.News;
import com.hunter.news.bean.Picture;
import com.hunter.news.bean.Weather;

import java.util.HashMap;

/**
 * Created by duwei on 2017/8/2.
 */
public interface HunterDao {

    public boolean saveData(News news);

    public boolean saveWeather(Weather weather);

    public boolean saveWeatherImg(Picture picture);

}
