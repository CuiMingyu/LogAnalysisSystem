package main.java.servlet;
import main.java.model.MostPV;
import main.java.service.MostPVService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MostPVServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws  ServletException , IOException{
        request.setCharacterEncoding("utf-8");

        int num=Integer.parseInt(request.getParameter("number"));

        List<MostPV> mPVlist=null;
        try{
            MostPVService mpv=new MostPVService();
            mPVlist=mpv.GetMostPV(num);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
