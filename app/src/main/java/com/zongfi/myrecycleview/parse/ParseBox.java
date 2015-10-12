package com.zongfi.myrecycleview.parse;

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
public class ParseBox implements BaseParser {

    private List data;
    private static ParseBox parseBox;

    private ParseBox(){};

    public static ParseBox getInstance(){
        if(parseBox==null){
            parseBox = new ParseBox();
        }
        return parseBox;
    }

    @Override
    public List<String> parse(Integer page) {
        if(page==null){
            page = 1;
        }
        String url = "http://news.ifeng.com/listpage/7129/"+page+"/list.shtmll";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements units = doc.getElementsByClass("comListBox");
            Iterator<Element> items = units.iterator();
            if(page==1 || data==null){
                data = new ArrayList<String>();
            }
            while (items.hasNext()){
                Element ele = items.next();
                Elements as = ele.getElementsByTag("a");
                data.add((data.size()+1)+"、"+as.first().text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
