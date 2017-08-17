package com.hunter.news.pipeline;

import com.hunter.news.bean.News;
import com.hunter.news.dao.HunterDao;
import com.hunter.news.dao.HunterDaoImpl;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;


/**
 * Created by duwei on 2017/8/2.
 */
public class DBPipeline implements Pipeline {

    private HunterDao hunterDao;

    public void process(ResultItems resultItems, Task task) {
        List<News> newsList = (List<News>) resultItems.get("newsList");
        //获取持久层
        hunterDao = new HunterDaoImpl();
        for (int i = 0; i < newsList.size(); i++) {
            boolean flag = hunterDao.saveData(newsList.get(i));
            if (flag) {
                System.out.println("第" + i + "持久化成功");
            } else {
                System.out.println("第" + i + "持久化失败");
            }
        }

    }
}
