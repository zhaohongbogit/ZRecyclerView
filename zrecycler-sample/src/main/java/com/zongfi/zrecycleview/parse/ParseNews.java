package com.zongfi.zrecycleview.parse;

import com.zhzephi.recycler.parse.BaseParser;
import com.zongfi.zrecycleview.pojo.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ZHZEPHI on 2015/10/10.
 */
public class ParseNews extends BaseParser {

    @Override
    public List<News> parse(Integer page) {
        if(page==null){
            page = 1;
        }
        List data = new ArrayList();
        String url = "http://ent.ifeng.com/listpage/3/"+page+"/list.shtml";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements units = doc.getElementsByClass("box_list");
            Iterator<Element> items = units.iterator();
            while (items.hasNext()){
                News news = new News();
                Element ele = items.next();
                news.setTitle(ele.select("h2").select("a").text());
                news.setContent(ele.getElementsByClass("box_txt").select("p").text());
                news.setTime(ele.getElementsByClass("box_txt").select("span").text());
                news.setImgPath(ele.getElementsByClass("box_pic").select("a").select("img").attr("src"));
                news.setSourceUrl(ele.getElementsByClass("box_pic").select("a").attr("href"));
                data.add(news);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
