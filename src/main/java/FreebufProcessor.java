package main.java;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alex on 2016/11/19.
 */

public class FreebufProcessor implements PageProcessor{
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    public void process(Page page) {
        /*
         * 解析页面
          */
    //   page.addTargetRequests(page.getHtml().links().regex("(www\\.freebuf\\.com/\\w+/\\w+)").all());
 //       System.out.println(page.getUrl().toString());
   /*    List<String> url_list= page.getHtml().links().regex("(http://www\\.freebuf\\.com/\\w+/\\w+\\.html)").all();
        for(String url : url_list){
            System.out.println(url);
        }
*/
       page.addTargetRequests(page.getHtml().links().regex("(http://www\\.freebuf\\.com/\\w+/\\w+\\.html)").all());

        if(page.getUrl().regex("(http://www\\.freebuf\\.com/\\w+/\\w+\\.html)").match()){

            page.putField("title",page.getHtml().xpath("//title/text()").regex("(.*) \\- FreeBuf\\.COM \\| 关注黑客与极客").toString());//获取页面标题并且去掉“- FreeBuf.COM | 关注黑客与极客”的后缀
            page.putField("url",page.getUrl());
        }

    }

    public Site getSite() {
       return site;
    }
    public static  void main(String[] args){
        Spider.create(new FreebufProcessor()).addUrl("http://freebuf.com").addPipeline(new ConsolePipeline()).thread(5).run();
    }
}

