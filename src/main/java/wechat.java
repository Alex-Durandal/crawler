package main.java;

import com.google.common.base.Predicate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.management.JMException;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
/**
 * Created by Alex on 2017/2/12.
 */
public class wechat implements PageProcessor {
    private  Site site=Site.me().setRetryTimes(3);
    @Override
    public void process(Page page) {

        String url = page.getUrl().toString();
        System.out.println(url);
        String article_rex = "http://mp\\.weixin\\.qq\\.com/s\\?_.*?"; //文章页面
        String  list_rex = "http://mp\\.weixin\\.qq\\.com/mp/.*?"; //列表页面
        Pattern article_pattern =Pattern.compile(article_rex);
        Pattern list_pattern =Pattern.compile(list_rex);
        Matcher ariticle_m = article_pattern.matcher(url);
        Matcher list_m = list_pattern.matcher(url);
        if(ariticle_m.matches()){
          //  System.out.println("文章页");
            subprocess(page);
        }
        else if(list_m.matches()){
          //  System.out.println("列表页");
            page.addTargetRequests(getHistoryArticleUrl(url));
        }
        else{
            page.setSkip(true);
        }

    }
    public void subprocess(Page page) {
      //  System.out.println("文章页");
        String url =page.getUrl().toString();
        String title = page.getHtml().xpath("//title/text()").toString();
        String time = page.getHtml().xpath("//*[@id=\"post-date\"]/text()").toString();
        String post_user = page.getHtml().xpath("//*[@id=\"post-user\"]/text()").toString();
        List<String> contents = page.getHtml().xpath("//*[@id=\"js_content\"]//*/text()").all();
        String article = "";
        if(contents!=null){
            for(String content_tmp : contents){
                article = article.concat(content_tmp);
            }
        }
        String source_url = page.getHtml().xpath("//*[@id=\"js_view_source\"]/@href").toString();
        if(source_url == null)
            source_url = "空";
        page.putField("title",title);
        page.putField("url",url);
        page.putField("post_user",post_user);
        page.putField("time",time);
        page.putField("article",article);
        page.putField("source",source_url);
        // Test Hbase:
   //     SaveToHbase(title, time, post_user, article, url, source_url);
}
    /*
    //存储数据：
    public void SaveToHbase(String title, String time, String post_user,String article, String url, String source_url){
         /*存储到hbase 表Tjwechat中：时间-发文人 做主键;
         * 存储 title, time, post_user, content, url, source_url 六项
         * */
//        HTable Tjwechat;
//        Tjwechat = Hbase.tjwechat();
//        String rowkey = time + "-" + post_user;
//        Put put = new Put(rowkey.getBytes());
//        put.add("info".getBytes(), "title".getBytes(), title.getBytes());
//        put.add("info".getBytes(), "time".getBytes(), time.getBytes());
//        put.add("info".getBytes(), "post_user".getBytes(), post_user.getBytes());
//        put.add("info".getBytes(), "content".getBytes(), article.getBytes());
//        put.add("info".getBytes(), "url".getBytes(), url.getBytes());
//        put.add("info".getBytes(), "source_url".getBytes(), source_url.getBytes());
//        try {
//            Tjwechat.put(put);
//        } catch (RetriesExhaustedWithDetailsException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedIOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    @Override
    public Site getSite() {
        return site;
    }

    public  List<String> getUrlList(String filename){
        /**
         * 从文件中读取公众号的历史消息页面url
         */
        List<String> urls = new LinkedList<String>();
        String pattern = "mp\\.weixin\\.qq\\.com\\/mp\\/.*?";   //正则提取历史页面的url
        Pattern r = Pattern.compile(pattern);

        File file = new File(filename);
        try {
            InputStream in = new FileInputStream(file);
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(file));

            String url =null;
            while((url=reader.readLine())!=null){
                Matcher m = r.matcher(url);
                if(m.matches()) {   //将正则提取的历史页面加入url列表中
                    url = "http://" + url;
                    //     System.out.println(url);
                    urls.add(url);
                }
            }
    //        System.out.println(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }

    public List<String> getHistoryArticleUrl(String historyListUrl){
        /**
         * 从历史消息列表页获取历史消息url
         */
        List<String> urls= new LinkedList<String>();    //历史消息页面中所有的链接
        System.setProperty("webdriver.chrome.driver","D://chromedriver.exe");
        WebDriver webdriver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(webdriver,1);
        webdriver.get(historyListUrl);
        String html =null;
        if(webdriver.getTitle().equals("查看历史消息")){

            while(true){//无更多数据,滚动到底层
                ((JavascriptExecutor)webdriver).executeScript("window.scrollTo(0, document.body.scrollHeight)"); //滚动页面
                if(webdriver.findElement(By.xpath("//div[@class='more_wrapper no_more']")).isDisplayed()) break;

            }
            html = webdriver.getPageSource();
            webdriver.close();
            //使用JSoup解析页面得到每篇文章的url
            Document doc = Jsoup.parse(html);
            Elements links = doc.select("a[class=simple_appmsg_link_box redirect]");
            for (Element link:links) {
                urls.add(link.attr("hrefs"));
            }
        }
        return urls;
    }
    public static void main(String[] args) throws JMException {
        wechat wx = new wechat();
        WechatGetSession wgs = new WechatGetSession();
        wgs.getSession("D:/start.bat","D:/stop.bat");
        List<String> urls = wx.getUrlList("D:/Sessions.txt");
//        System.out.println("公众号历史url:");
//        for(String url : urls){
//            wx.getHistoryArticleUrl(url);
//            System.out.println("历史文章列表："+wx.getHistoryArticleUrl(url));
//        }
        //        String  startURL = "http://mp.weixin.qq.com/s?__biz=MjM5ODI5Njc2MA==&mid=2655808582&idx=1&sn=cd925b4ffbf94d2ef325b294ec31d67e&chksm=bd7421918a03a887623579d8e3bb71131a9c9e2ead1e6cc55b75405d9ce4b6ee2784da3d4b05&scene=4#wechat_redirect";
        Spider wechat_spider = Spider.create(wx).addPipeline(new ConsolePipeline()).setExitWhenComplete(true).thread(4);
        for(String url : urls){
            wechat_spider.addUrl(url);
        }
        wechat_spider.run();
        wechat_spider.close();

    }
}
