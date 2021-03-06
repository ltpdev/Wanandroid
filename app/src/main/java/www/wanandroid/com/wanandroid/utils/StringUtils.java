package www.wanandroid.com.wanandroid.utils;

public class StringUtils {
    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "\r\n");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "\r\n");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        // 还原HTML
        // content = HTMLDecoder.decode(content);
        //&ldquo;&quot;&nbsp;
        content = content.replaceAll("&.dquo;", "\"");
        content = content.replaceAll("&nbsp;", " ");
        return content;
    }


}
