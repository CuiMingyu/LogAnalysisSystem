package main.java.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;

/**
 * Created by root on 9/13/17.
 */
public class Spider {
    public static Document getDocument(String url)
            throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)")
                .get();
        return doc;
    }

    public static String getTitle(String url) {
        String title = null;
        try {
            Document doc = getDocument(url);
            title = doc.title();
            System.out.println(url + " succeed");
        } catch (Exception e) {
            System.out.println("When connecting " + url);
            e.printStackTrace();
        }
        return title;
    }
    public static void HandleFile(String inputFile,String outputFile){
        BufferedReader br=null;
        BufferedWriter bw=null;
        try {
            br=new BufferedReader(new FileReader(inputFile));
            bw=new BufferedWriter(new FileWriter(outputFile));
            String line;
            for (int i = 1; (line = br.readLine()) != null; i++) {
                String url=line.split("\t")[8];
                String title=getTitle(url);
                if(title!=null)
                    bw.write(title+"\t"+url+"\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (br != null) br.close();
                if (bw !=null){bw.flush();
                    bw.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        HandleFile("log.txt","20170101title");
        HandleFile("20170102.txt","20170102title");
        HandleFile("20170103.txt","20170103title");
        HandleFile("20170104.txt","20170104title");
        HandleFile("20170105.txt","20170105title");
        HandleFile("20170106.txt","20170106title");
        HandleFile("20170107.txt","20170107title");
    }
}
