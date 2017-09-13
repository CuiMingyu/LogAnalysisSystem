package main.java.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by root on 9/13/17.
 */
public class Spider {
    public static Document getDocument(String url)
            throws IOException {
        Document doc= Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)")
                .get();
        return doc;
    }
    public static String getTitle(String url){
        String title=null;
        try{
            Document doc=getDocument(url);
            title=doc.title();
        }catch(Exception e){
            e.printStackTrace();
        }
        return title;
    }
    public static void main(String[]args){
        System.out.println(getTitle("https://www.baidu.com"));
    }
}
