package servlet;
import model.MostUV;
import model.MostUV;
import service.MostUVService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MostUVServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws  ServletException , IOException{
        request.setCharacterEncoding("utf-8");

        int num=Integer.parseInt(request.getParameter("number"));

        List<MostUV> mUVlist=null;
        try{
            MostUVService muv=new MostUVService();
            mUVlist=muv.GetMostUV(num);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
