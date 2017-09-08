package Servlet;

import Service.CityRateDataService;
import model.CityRateData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yxy on 9/7/17.
 * servlet for select by city name
 */
public class CityRateDataNameServlet extends HttpServlet{

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

        
    }
}
