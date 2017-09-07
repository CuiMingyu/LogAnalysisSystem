package Servlet;

import Service.CityRateDataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import model.CityRateData;
import util.*;

/**
 * Created by yxy on 9/7/17.
 */
public class CityRateDataDateServlet extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException ,IOException{
        doPost(request,response);
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
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
