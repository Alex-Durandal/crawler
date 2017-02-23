import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class NewsSubject {
	TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
	private String title;		//新闻标题
	private String content;		//新闻内容
	private Date dateTime;		//发布时间
	private String stringTime;	//发布时间
	private String id;			//新闻ID
	private String url;			//新闻链接
	private String source;		//新闻来源
	private String type;		//新闻类型
	private List<String> keywords;	//关键词
	private List<String> relevances;	//相关新闻链接，使用_连接id
	
    public List<String> getKeywords() {
        return keywords;
    }
    public void setKeyword(List<String> keywords) {
        this.keywords = keywords;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getTitle() {
        return title;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getDateTime() throws ParseException {
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sdf.parse(this.stringTime);
    }
    public void setDateTime(Date newsTime) throws ParseException {
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        this.dateTime = sdf.parse(this.stringTime);
    }
    public String getStringTime() {
        return stringTime;
    }
    public void setStringTime(String newsTime) {
        this.stringTime = newsTime;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getURL() {
        return url;
    }
    public void setURL(String url) {
        this.url = url;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public List<String> getRelevances() {
        return relevances;
    }
    public void setRelevances(List<String> relevances) {
        this.relevances = relevances;
    }
    public String toString(){
    	return "新闻：{" +
    	"标题=" + title +
    	", 时间=" + stringTime +
    	", 链接=" + url +
    	", 类型=" + type +
    	", 来源=" + source +
    	", 内容='" + content +
    	", 关键词='" + keywords +
    	", 相关'" + relevances + '\''+
    	'}';
    	}
    public String toDateJson(){
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("title", title);
    	jsonObject.put("dateTime", dateTime);
    	jsonObject.put("stringTime", stringTime);
    	jsonObject.put("id", id);
    	jsonObject.put("url", url);
    	jsonObject.put("source", source);
    	jsonObject.put("content", content);
    	jsonObject.put("type", type);
    	jsonObject.put("keywords", keywords);
    	jsonObject.put("relevances", relevances);
    	return jsonObject.toString();
    }
    public String toStringJson(){
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("title", title);
    	jsonObject.put("dateTime", dateTime);
    	jsonObject.put("stringTime", stringTime);
    	jsonObject.put("id", id);
    	jsonObject.put("url", url);
    	jsonObject.put("source", source);
    	jsonObject.put("content", content);
    	jsonObject.put("type", type);
    	jsonObject.put("keywords", keywords);
    	jsonObject.put("relevances", relevances);
    	return jsonObject.toString();
    }
}
