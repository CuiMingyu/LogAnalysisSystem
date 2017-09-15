package main.java.servlet;

import main.java.service.NewDeviceRecordService;
import com.alibaba.fastjson.JSON;
import main.java.model.NewDeviceMachine;
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
 * 给出两个日期，传递所有品牌的新增数量
 */
public class NewDeviceMachineServlet extends HttpServlet{
    protected void getJson(HttpServletRequest request, HttpServletResponse response, Object object){
        response.setContentType("text/html;charset=UTF-8");
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
            throws ServletException, IOException{
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");

        String dateString1=request.getParameter("date1");
        String dateString2=request.getParameter("date2");

        List<NewDeviceMachine> ndmlist= null;
        try{
            NewDeviceRecordService service=new NewDeviceRecordService();
            Date date1= DateUtils.parseDate(dateString1,"yyyy-MM-dd");
            Date date2= DateUtils.parseDate(dateString2,"yyyy-MM-dd");
            ndmlist=service.GetMachineByDate(date1,date2);
        }catch(Exception e){
            e.printStackTrace();
        }

        getJson(request, response, ndmlist);//retrun a list

    }
}
