package jni;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.Statement;  
  
public class Basedao {  
    public static Connection getConnection()throws Exception{
    	System.out.println("in BaseDao getConnection");
        Class.forName("com.mysql.jdbc.Driver");  
        String url = "jdbc:mysql://10.10.10.128:3306/xinan";  
        System.out.println("out BaseDao getConnection");
        return DriverManager.getConnection(url, "root", "letmein");  
    }  
      
    public static void close(ResultSet rs,Statement sta,Connection con)throws Exception{  
        if(rs!=null){  

                rs.close();  
        }  
        if(sta!=null){  

                 sta.close();  
        }  
        if(con!=null){  

                con.close();  
        }  
    }  
}  
