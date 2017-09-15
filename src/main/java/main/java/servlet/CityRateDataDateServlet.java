package main.java.servlet;

import main.java.service.CityRateDataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.*;
import com.alibaba.fastjson.annotation.JSONField;
import main.java.model.CityRateData;
import main.java.util.*;

/**
 * Created by yxy on 9/7/17.
 */
public class CityRateDataDateServlet extends HttpServlet{


    protected void getJson(HttpServletRequest request,HttpServletResponse response,Object object){

        PrintWriter out = null;
        try {
            out = response.getWriter();
            String jsonStr= JSON.toJSONString(object);
             System.out.println(jsonStr);
            out.print(jsonStr);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException ,IOException{
        doPost(request,response);
        //response.getWriter().write("111");
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws  ServletException , IOException{
        request.setCharacterEncoding("utf-8");

        String dateString=request.getParameter("date");

        List<CityRateData> crdlist=null;
        try{
            CityRateDataService crds=new CityRateDataService();
            Date date=DateUtils.parseDate(dateString,"yyyy-MM-dd");
            crdlist=crds.SelectByDate(date);
            response.setCharacterEncoding("utf-8");
            request.setAttribute("crdlist",crdlist);
        }catch(Exception e){
            e.printStackTrace();
        }

        //过滤器
        response.setCharacterEncoding("utf-8");

        request.getRequestDispatcher("activity2.jsp").forward(request,response);
      //  getJson(request, response, crdlist);
        /*java.sql.Date date= java.sql.Date.valueOf("2010-12-01");
        CityRateData cd=new CityRateData();
        cd.setCityRateData(date,"南京",10,20,0.5);
        crdlist.add(cd);*/

    }


    public static void main(String args[]){
        List<CityRateData> crdlist=null;
        java.sql.Date date= java.sql.Date.valueOf("2017-01-01");
        CityRateDataService crds=new CityRateDataService();
        crdlist=crds.SelectByDate(date);
        String jsonStr= JSON.toJSONString(crdlist);
        System.out.println(jsonStr);
    }
}
