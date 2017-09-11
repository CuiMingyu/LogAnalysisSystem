package servlet;

import service.CityRateDataService;
import model.CityRateData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.io.*;
import com.alibaba.fastjson.*;

/**
 * Created by yxy on 9/7/17.
 * servlet for select by city name
 */
public class CityRateDataNameServlet extends HttpServlet{

    protected void getJson(HttpServletRequest request,HttpServletResponse response,Object object){
        response.setContentType("text/html;charset=UTF-8");
        //禁用缓存，确保网页信息是最新数据
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", -10);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String jsonStr=JSON.toJSONString(object);
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
            throws ServletException, IOException{
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");

        String cityName=request.getParameter("cityname");

        CityRateDataService service=new CityRateDataService();
        List<CityRateData> crdlist=service.SelectByCityName(cityName);

        getJson(request, response, crdlist);//retrun a list
    }
}
