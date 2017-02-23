package main.java;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.management.JMException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alex on 2016/11/30.
 */
public class PeopleNewsCrawler implements PageProcessor{
    private  Site site=Site.me().setRetryTimes(3);
    static final String urlRegx = "<[^>]+>";//[]内匹配不是>(^ 反向)的所有内容
    public static  final String content_pattern = "http://\\w+\\.people\\.com\\.cn/n1/\\d{4}/\\d{4}/c\\d+\\-\\d+\\.html";//新闻内容页面
    public static  final String nocontent_pattern = "http://（media|game|tv|pic）\\.people\\.com\\.cn/n1/\\d{4}/\\d{4}/c\\d+\\-\\d+\\.html";//无新闻内容页面
    public static  final String list_pattern = "http://news\\.people\\.com\\.cn/210801/211150/index\\.js\\?\\_\\=\\.*"; //新闻列表页面
    @Override
    public void process(Page page) {
        if(page.getUrl().regex(list_pattern).match()){
            String raw = page.getRawText();
            String json = raw.substring(9, raw.length()-1);

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int size = jsonArray.length();
//			System.out.println(size);
            for(int i=0;i<size;i++){
                String url = null;
                try {
                    url = jsonArray.getJSONObject(i).optString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
     //           System.out.println(url);
                page.addTargetRequest(url);
            }
         //   page.addTargetRequests(page.getHtml().links().regex("").all());
        }
        else if(page.getUrl().regex(nocontent_pattern).match()){
            System.out.println(page.getUrl().toString()+"pass!");
            page.setSkip(true);
        }
        else if(page.getUrl().regex(content_pattern).match()){
            processNews(page);
        }
        else{
            page.setSkip(true);
        }

    }

    public void processNews(Page page){

        String newsUrl = page.getUrl().toString(); //新闻链接
 //       System.out.println("url:"+newsUrl);
        String newsIDs = newsUrl.split("/")[newsUrl.split("/").length-1];
        String newsID = newsIDs.split("\\.")[0];
        String newsTitle = page.getHtml().xpath("/html/body//div[@class='clearfix w1000_320 text_title']/h1/text()").toString();      //新闻标题 body > div.
 //       System.out.println("title:"+newsTitle);
        String time = page.getHtml().xpath("//div[@class='box01']/div[@class='fl']/text()").regex("\\d{4}年\\d{2}月\\d{2}日\\d{2}\\:\\d{2}").toString();		//发布时间
  //      System.out.println("time:"+time);
        String time_pattern="(\\d{4})年(\\d{2})月(\\d{2})日(\\d{2}):(\\d{2})";
        Pattern r =Pattern.compile(time_pattern);
        Matcher m = r.matcher(time);
        String newsTime="";
        if (m.matches())
        {
             newsTime=m.group(1)+'-'+m.group(2)+'-'+m.group(3)+" "+m.group(4)+":"+m.group(5);
  //          System.out.println("time:"+newsTime);
        }

        List<String> newsContents = page.getHtml().xpath("//div[@id=\"rwb_zw\"]//p/text()").replace("\n", "").all();		//新闻内容
        String newsContent = "";
        if(newsContents!=null){
            for(String newsContentp : newsContents){
                newsContent = newsContent.concat(newsContentp);
            }
        }
        if(newsContent.equals("")){
            newsContent = newsTitle;
        }
 //       System.out.println("content:"+newsContent);
        List<String> newsRelatedUrls =  new ArrayList<String>();  //相关链接
        newsRelatedUrls = page.getHtml().xpath("//div[@id='rwb_xgxw']/div/ul//li/a/@href").all();
        List<String> newsKeywords =  new ArrayList<String>();   //新闻关键词
        newsKeywords = Arrays.asList(page.getHtml().xpath("//head/meta[@name=\"keywords\"]/@content").toString().split(" ")) ;
        if(newsKeywords==null){
            newsKeywords.add("");
        }
   //     System.out.println("keywords:"+newsKeywords);
        String newsType = page.getHtml().xpath("//span[@id='rwb_navpath']/a[2]/text()").toString(); //新闻类型
        if(newsType == null){
            newsType = "综合";
        }

        String newsSource = page.getHtml().xpath("/html/head/meta[15]/@content").regex("[^来源：].*").toString(); //新闻来源
        if(newsSource ==null){
            newsSource = "人民网";
        }

        page.putField("title",newsTitle);
        page.putField("url",newsUrl);
        page.putField("id",newsID);
        page.putField("keywords",newsKeywords);
        page.putField("time",newsTime);
        page.putField("type",newsType);
        page.putField("source",newsSource);
        page.putField("content",newsContent.replaceAll(urlRegx, ""));
        page.putField("relative", newsRelatedUrls);
    }


    @Override
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) throws JMException {
        Date today = new Date();
      //  System.out.println(today.getTime());
       String  startURL = "http://news.people.com.cn/210801/211150/index.js?_="+today.getTime();
      // String  startURL = "http://game.people.com.cn/n1/2016/1202/c40130-28920751.html";
      //  String startURL ="http://world.people.com.cn/n1/2016/1202/c1002-28920469.html";
        Spider people = Spider.create(new PeopleNewsCrawler()).addUrl(startURL).addPipeline(new ConsolePipeline()).setExitWhenComplete(true).thread(4);
        people.run();
        people.close();
    }
}

