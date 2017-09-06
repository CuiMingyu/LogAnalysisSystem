import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

public class Sqldb {
    public static Connection getConnection(){
        Connection conn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn=DriverManager.getConnection("jdbc:mysql:///city","root","yuan2292605");
            if(!conn.isClosed())
                System.out.println("Successfully connected to MySQL server...");
        }catch(Exception e){
            System.err.println("Exception:"+e.getMessage());
        }
        return conn;
    }

    public static void Free(ResultSet rs,Connection conn,Statement stmt){
        try{
            if(rs!=null)
                rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                try{
                    if(stmt!=null)
                        stmt.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void addRateInfo(Date date,String city,int PV,int UV,double rate){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try{
            con=Sqldb.getConnection();
            String sql="INSERT INTO rateinfo(date,city,PV,UV,rate) values(?,?,?,?,?)";
            ps=con.prepareStatement(sql);
            ps.setDate(1,date);
            ps.setString(2,city);
            ps.setInt(3,PV);
            ps.setInt(4,UV);
            ps.setDouble(5,rate);
            ps.executeUpdate();
            System.out.println("Insert successfully");
            System.out.println("Date:"+date+" city:"+city+" PV:"+PV+" UV:"+UV+" rate:"+rate);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Sqldb.Free(rs, con, ps);
        }
    }

    public int getrateid(CityData cd){
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        int num=0;
        try{
            con=Sqldb.getConnection();
            st=con.createStatement();
            rs=st.executeQuery("select * from rateinfo");
            while(rs.next()){
                if (rs.getDate("date").equals(cd.getDate()) && rs.getString("city").equals(cd.getName())
                        && rs.getInt("PV")==cd.getPV() && rs.getInt("UV")==cd.getUV()
                        && rs.getDouble("rate")==cd.getRate()){
                    num=rs.getInt("id");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.Free(rs, con, st);
        }
        return num;
    }

    public List<CityData> SelectByCityName(String cityName){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<CityData> cdList=new ArrayList<CityData>();
        try{
            con=Sqldb.getConnection();
            ps=con.prepareStatement("select * from rateinfo where city= ?");
            ps.setString(1,cityName);
            rs=ps.executeQuery();

            while(rs.next()){
                CityData cd=new CityData();
                cd.setCityData(rs.getDate("date"),rs.getString("city"),rs.getInt("PV"),rs.getInt("UV"),
                        rs.getDouble("rate"));
                cdList.add(cd);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.Free(rs, con, ps);
        }
        return cdList;
    }

    public List<CityData> SelectByDate(Date date){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<CityData> cdList=new ArrayList<CityData>();
        try{
            con=Sqldb.getConnection();
            ps=con.prepareStatement("select * from rateinfo where date= ?");
            ps.setDate(1,date);
            rs=ps.executeQuery();

            while(rs.next()){
                CityData cd=new CityData();
                cd.setCityData(rs.getDate("date"),rs.getString("city"),rs.getInt("PV"),rs.getInt("UV"),
                        rs.getDouble("rate"));
                cdList.add(cd);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.Free(rs, con, ps);
        }
        return cdList;
    }

    public String SelectCityByID(String id){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String city=null;
        try{
            con=Sqldb.getConnection();
            ps=con.prepareStatement("select * from cityid where id= ?");
            ps.setString(1,id);
            rs=ps.executeQuery();

            while(rs.next()){
                city=rs.getString("city");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.Free(rs, con, ps);
        }
        return city;
    }

    public static void main(String args[]){
        Sqldb sqldb=new Sqldb();
        Date date=Date.valueOf("2010-12-01");
        CityData cd=new CityData();
        cd.setCityData(date,"南京",10,20,0.5);
        //System.out.println(sqldb.getrateid(cd));
        List<CityData> ls=sqldb.SelectByCityName("南京");
        for(int i=0;i<ls.size();i++){
            System.out.println(ls.get(i));
        }
        List<CityData> ds=sqldb.SelectByDate(Date.valueOf("2010-12-01"));
        for(int i=0;i<ds.size();i++){
            System.out.println(ds.get(i));
        }
        System.out.println(sqldb.SelectCityByID("10"));
    }
}
