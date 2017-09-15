package main.java.servlet;

import main.java.service.TimeRateDataService;
import com.alibaba.fastjson.JSON;
import main.java.model.TimeRateData;
import main.java.util.DateUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * Created by yxy on 9/10/17.
 * 给出日期，返回该日期下的所有时间段的PV，UV
 */
public class TimeRateDataDateServlet extends HttpServlet{

    protected void getJson(HttpServletRequest request, HttpServletResponse response, Object object){
        //response.setContentType("text/html;charset=UTF-8");
        //禁用缓存，确保网页信息是最新数据
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", -10);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String jsonStr= JSON.toJSONString(object);
            out.print(jsonStr);
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            out.close();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws  ServletException , IOException{
        request.setCharacterEncoding("utf-8");

        String dateString=request.getParameter("date");

        List<TimeRateData> trdlist=null;
        try{
            TimeRateDataService crds=new TimeRateDataService();
            Date date= DateUtils.parseDate(dateString,"yyyy-MM-dd");
            trdlist=crds.SelectByDate(date);
        }catch(Exception e){
            e.printStackTrace();
        }
        getJson(request,response,trdlist);
    }

}
