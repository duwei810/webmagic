package com.hunter.news.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hunter.news.bean.Weather;
import com.hunter.news.dao.HunterDao;
import com.hunter.news.dao.HunterDaoImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by duwei on 2017/8/7.
 */
public class XmlParser {
    InputStream inStream;
    Element root;

    public InputStream getInStream() {
        return inStream;
    }

    public void setInStream(InputStream inStream) {
        this.inStream = inStream;
    }

    public Element getRoot() {
        return root;
    }

    public void setRoot(Element root) {
        this.root = root;
    }

    public XmlParser() {
    }

    public XmlParser(URL url) {
        InputStream inStream = null;
        try {
            inStream = url.openStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (inStream != null) {
            this.inStream = inStream;
            DocumentBuilderFactory domfac = DocumentBuilderFactory
                    .newInstance();
            try {
                DocumentBuilder domBuilder = domfac.newDocumentBuilder();
                Document doc = domBuilder.parse(inStream);
                root = doc.getDocumentElement();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param nodes
     * @return 单个节点多个值以分号分隔
     */
    public Map<String, String> getValue(String[] nodes) {
        if (inStream == null || root == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        // 初始化每个节点的值为null
        for (int i = 0; i < nodes.length; i++) {
            map.put(nodes[i], null);
        }

        // 遍历第一节点
        NodeList topNodes = root.getChildNodes();
        if (topNodes != null) {
            for (int i = 0; i < topNodes.getLength(); i++) {
                Node book = topNodes.item(i);
                if (book.getNodeType() == Node.ELEMENT_NODE) {
                    for (int j = 0; j < nodes.length; j++) {
                        for (Node node = book.getFirstChild(); node != null; node = node
                                .getNextSibling()) {
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                if (node.getNodeName().equals(nodes[j])) {
                                    //String val=node.getFirstChild().getNodeValue();
                                    String val = node.getTextContent();
                                    // System.out.println(nodes[j] + ":" + val);
                                    // 如果原来已经有值则以分号分隔
                                    String temp = map.get(nodes[j]);
                                    if (temp != null && !temp.equals("")) {
                                        temp = temp + ";" + val;
                                    } else {
                                        temp = val;
                                    }
                                    map.put(nodes[j], temp);
                                }
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    public static void getWeatherInfo() {
        Weather weather = new Weather();
        String city = null;
        try {
            city = java.net.URLEncoder.encode(TestUtils.city, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://php.weather.sina.com.cn/xml.php?city=" + city + "&password=DJOYnieT8234jlsK&day=0";
        XmlParser parser = null;
        try {
            parser = new XmlParser(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String[] nodes = {"city", "status1", "direction1", "power1", "temperature1", "temperature2", "savedate_life"};
        Map<String, String> map = parser.getValue(nodes);
        String message = map.get(nodes[1]) + " " + map.get(nodes[2]) + " 风级" + map.get(nodes[3]) + " 温度" + map.get(nodes[5]) + "~" + map.get(nodes[4]);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date time = null;
        try {
            time = sdf.parse(map.get(nodes[6]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        weather.setCity(TestUtils.city);
        weather.setMessage(message);
        weather.setTime(time);
        HunterDao hunterDao = new HunterDaoImpl();

        boolean flag = hunterDao.saveWeather(weather);
        if(flag == true){
            System.out.println("天气插入成功");
        }else{
            System.out.println("天气插入失败");
        }

    }

}
