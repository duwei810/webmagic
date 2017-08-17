package com.hunter.news.utils;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import us.codecraft.webmagic.Page;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 分析页面，提取News中需要的字段
 * Created by duwei on 2017/8/2.
 */
public class ExtractUtils {

    public static NodeList parseHTML(String url, String exp) {
        NodeList result = null;
        try {
            Connection connect = Jsoup.connect(url);
            String html = connect.get().body().html();
            HtmlCleaner hc = new HtmlCleaner();
            TagNode tn = hc.clean(html);
            Document dom = new DomSerializer(new CleanerProperties()).createDOM(tn);
            XPath xPath = XPathFactory.newInstance().newXPath();
            result = (NodeList) xPath.evaluate(exp, dom, XPathConstants.NODESET);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getUrl(Page page, int i) {
        return page.getHtml().xpath("//*[@id=\"" + i + "\"]/h3/a/@href").toString();
    }

    public static String getTitle(Page page, int i) {
        String url = page.getUrl().toString();
        String exp = "//*[@id=" + i + "]/h3/a";
        NodeList nodeList = parseHTML(url, exp);
        Node node = nodeList.item(0);
        return HTMLDecoder.decode(node.getTextContent());
    }

    public static String getInfo(Page page, int i) {
        return page.getHtml().xpath("//*[@id=" + i + "]/div/div[2]/p/text() | " +
                "//*[@id=" + i + "]/div/p/text()").toString();
    }

    public static String getSource(Page page, int i) {
        String info = getInfo(page, i);
        String[] result = info.split(String.valueOf((char) (160)));
        return result[0];
    }

    public static String getDate(Page page, int i) {
        String info = getInfo(page, i);
        String[] result = info.split(String.valueOf((char) (160)));
        return result[2];
    }

    public static String getContent(Page page, int i) {
        String url = page.getUrl().toString();
        String exp = "//*[@id=" + i + "]/div/div[2]/p | //*[@id=" + i + "]/div/p";
        NodeList nodeList = parseHTML(url, exp);
        Node node = nodeList.item(0);
        StringBuilder sb = new StringBuilder();
        while (!node.getNextSibling().getNodeName().equals("span")) {
            sb.append(node.getNextSibling().getTextContent());
            node = node.getNextSibling();
        }
        String result = HTMLDecoder.decode(sb.toString()).replace("\n", "");
        return result.replace(" ", "");
    }

    public static String getImg(Page page, int i) {
        String url = page.getUrl().toString();
        String exp = "//*[@id=" + i + "]/div/div[1]/a/img/@src";
        NodeList nodeList = parseHTML(url, exp);
        Node node = nodeList.item(0);
        if(node == null){
            return null;
        }
        return HTMLDecoder.decode(node.getTextContent());
    }

}
