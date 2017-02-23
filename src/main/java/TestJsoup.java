package main.java;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Alex on 2017/2/13.
 */
public class TestJsoup {
    public static  void main(String[] args){
//        String pattern ="http://mp\\.weixin\\.qq\\.com/s\\?_.*?";
//        Pattern r = Pattern.compile(pattern);
//        String url = "http://mp.weixin.qq.com/s?__biz=MjM5ODI5Njc2MA==&mid=2655808582&idx=1&sn=cd925b4ffbf94d2ef325b294ec31d67e&chksm=bd7421918a03a887623579d8e3bb71131a9c9e2ead1e6cc55b75405d9ce4b6ee2784da3d4b05&scene=4#wechat_redirect";
//        Matcher m = r.matcher(url);
//        if(m.matches()){
//            System.out.println(url);
        File file = new File("D:/wechat.html");
        Document doc = null;
        try {
            doc = Jsoup.parse(file,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Elements links = doc.body().select("a.simple_appmsg_link_box redirect");
        Elements links = doc.select("a[class=simple_appmsg_link_box redirect]");
        for (Element link:links) {
            System.out.println(link.attr("hrefs"));
        }
        }
    }
