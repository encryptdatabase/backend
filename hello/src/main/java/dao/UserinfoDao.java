package dao;

import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dto.UserInfo;
import jni.Basedao;
import jni.ResultType;
import jni.client;

  
public class UserinfoDao {
  
//    static {
//    	System.loadLibrary("JNIWin");
//    }
    
    //查询所有（查）  
    public List<ArrayList> findAll(){
    	
    	System.out.println("\n\n\nin UserinfoDao.findAll\n\n\n");
        Connection con = null;  
        PreparedStatement psta = null;  
        ResultSet rs = null;
        List<ArrayList> datalist = new ArrayList<ArrayList>();
        ArrayList list = new ArrayList();
        ArrayList cipherlist = new ArrayList();
        String sql = "select * from user";  

        try{  
            con = Basedao.getConnection();  
            System.out.println("in UserinfoDao.findAll after getConnection"); 
            Map<String, String> new_query_select = client.RewriteQuery(sql); // 重写语句
            String new_query = new_query_select.get("0");
            System.out.println("in UserinfoDao.findAll new_query: " + new_query);
            psta = con.prepareStatement(new_query);  
            rs = psta.executeQuery();  // 执行重写后的语句
            
            ResultType cipherresult = client.ResultSet2Result(rs);
            Map<String, String> nameCipherMap = cipherresult.getNameMap(); // 属性字段
            ResultType plainresult = client.DecryptResult(cipherresult, sql); // 解密结果集
            Map<String, String> nameMap = plainresult.getNameMap(); // 属性字段
            
            
            // 密文
            for(int i = 0; i < cipherresult.getValueList().size(); i++) {
            	Map<String, String> valueRowCipherMap = cipherresult.getValueList().get(i); // 一行数据
                UserInfo objCipher = new UserInfo();
                String idCipher = valueRowCipherMap.get(nameCipherMap.get("0"));
                String nameCipher = valueRowCipherMap.get(nameCipherMap.get("1"));
                String ageCipher = valueRowCipherMap.get(nameCipherMap.get("2"));
                String hobbyCipher = valueRowCipherMap.get(nameCipherMap.get("3"));
                String mtextCipher = valueRowCipherMap.get(nameCipherMap.get("4"));
                
                objCipher.set_id(idCipher); 
                objCipher.setUser_name(nameCipher);  
                objCipher.setUser_age(ageCipher);  
                objCipher.setUser_hobby(hobbyCipher);  
                objCipher.setMtext(mtextCipher);  
                cipherlist.add(objCipher);  
            }
            
            for(int i = 0; i < plainresult.getValueList().size(); i++) {
            	Map<String, String> valueRowMap = plainresult.getValueList().get(i); // 一行数据
                UserInfo obj = new UserInfo();
                String id = valueRowMap.get(nameMap.get("0"));
                String name = valueRowMap.get(nameMap.get("1"));
                String age = valueRowMap.get(nameMap.get("2"));
                String hobby = valueRowMap.get(nameMap.get("3"));
                String mtext = valueRowMap.get(nameMap.get("4"));
                
                obj.set_id(id); 
                obj.setUser_name(name);  
                obj.setUser_age(age);  
                obj.setUser_hobby(hobby);  
                obj.setMtext(mtext);  
                list.add(obj);  
            }
            datalist.add(list); // 明文第一
            datalist.add(cipherlist); // 密文第二
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            try{  
                Basedao.close(rs, psta, con);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return datalist;  
        }  
    }  
      
    //插入方法（增）  
    public boolean save(UserInfo obj){  
        Connection con=null;  
        PreparedStatement psta=null;
        String sql="insert into user values(?,?,?,?,?)";       
       
        boolean flag = false;  
        try{  
            con = Basedao.getConnection();
            psta = con.prepareStatement(sql);           
            psta.setString(1, obj.get_id()); 
            psta.setString(2, obj.getUser_name());  
            psta.setString(3, obj.getUser_age());  
            psta.setString(4, obj.getUser_hobby());
            psta.setString(5, obj.getMtext());    
            System.out.println(sql);
            String sql_excute = "insert into user values ("+obj.get_id()+", '"+obj.getUser_name()+ "',"+obj.getUser_age()+ ",'"+obj.getUser_hobby()+ "','"+obj.getMtext()+ "') ";
            Statement statement = con.createStatement();
            System.out.println(sql_excute);
            Map<String, String> sql_rewrite_map = client.RewriteQuery(sql_excute); // 重写语句
            System.out.println(sql_rewrite_map.get("0"));
            statement.execute(sql_rewrite_map.get("0")); // 执行语句
            
//            flag = psta.executeUpdate() > 0;  
           
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            try{  
                Basedao.close(null, psta, con);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return flag;  
        }  
    }  
      
    //删除方法（删）  
    public boolean remove(String id){  
        Connection con = null;  
        PreparedStatement psta=null;   
        String sql="delete from user where id=?";  
        String sql_excute="delete from user where id = "+id+";";
        System.out.println("in remove:" + sql_excute);
        Statement statement;
		try {
		    con = Basedao.getConnection();
			statement = con.createStatement();
        System.out.println(sql_excute);
        Map<String, String> sql_rewrite_map = client.RewriteQuery(sql_excute); // 重写语句
        System.out.println(sql_rewrite_map.get("0"));
			statement.execute(sql_rewrite_map.get("0"));
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            try{  
                Basedao.close(null, psta, con);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return true;  
        }
    }  
    //通过id修改，为更新做准备的（改）  
            public UserInfo findById(String id){  
                Connection con=null;  
                PreparedStatement psta = null;
                ResultSet rs = null;  
                UserInfo obj = new UserInfo(); 
                String sql = "select * from user where id = "+id+";";
                System.out.println("in UserinfoDao.findById sqls: " + sql);
                try{  
                    con = Basedao.getConnection();   
                    Statement statement = con.createStatement();
                    Map<String, String> new_query_select = client.RewriteQuery(sql); // 重写语句
                    String new_query = new_query_select.get("0");
                    System.out.println("in UserinfoDao.findById new_query: " + new_query);
                    
                    rs = statement.executeQuery(new_query);  // 执行重写后的语句
                    
                    ResultType cipherresult = client.ResultSet2Result(rs);
                    Map<String, String> nameCipherMap = cipherresult.getNameMap(); // 属性字段
                    ResultType plainresult = client.DecryptResult(cipherresult, sql); // 解密结果集
                    Map<String, String> nameMap = plainresult.getNameMap(); // 属性字段
                    
                    for(int i = 0; i < plainresult.getValueList().size(); i++) {
                    	Map<String, String> valueRowMap = plainresult.getValueList().get(i); // 一行数据
                        String name = valueRowMap.get(nameMap.get("1"));
                        String age = valueRowMap.get(nameMap.get("2"));
                        String hobby = valueRowMap.get(nameMap.get("3"));
                        String mtext = valueRowMap.get(nameMap.get("4"));           
                        obj.set_id(id); 
                        obj.setUser_name(name);  
                        obj.setUser_age(age);  
                        obj.setUser_hobby(hobby);  
                        obj.setMtext(mtext);
                    }           
                }catch(Exception e){  
                    e.printStackTrace();  
                }try{  
                        Basedao.close(rs, psta, con);  
                    }catch(Exception e){  
                        e.printStackTrace();  
                }
				return obj;
            }  
      
    //更新方法（修改数据）（改）  
    public boolean update(UserInfo obj){  
        Connection con = null;  
        PreparedStatement psta=null;
        System.out.println("\n\n\nin update");
        String sql="update user set name=?, age=?, hobby=?, mtxt=? where id =?";       
        String sql_update = "update user set name = '"+obj.getUser_name()+ "', age = "+obj.getUser_age()+", hobby = '"+obj.getUser_hobby()+"', mtxt = '"+obj.getMtext()+"' where id = "+obj.get_id()+""; 
        System.out.println("\n\n\nin update.sql_update sql_update: " + sql_update);
        boolean flag = true;  
        try{  
            con = Basedao.getConnection();  
            Statement statement = con.createStatement();
            
            String update = client.RewriteQuery(sql_update).get("0");
            psta = con.prepareStatement(sql);
            psta.setString(1, obj.get_id()); 
            psta.setString(2, obj.getUser_name());    
            psta.setString(3, obj.getUser_age());  
            psta.setString(4, obj.getUser_hobby());   
            psta.setString(5, obj.getMtext());  
            
            statement.execute(update); // 执行更新操作
            
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            try{  
                Basedao.close(null, psta, con);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return flag;  
        }  
    }  
public List<ArrayList> orderby(int type ,int dire){
    	
    	System.out.println("\n\n\nin UserinfoDao.orderby\n\n\n");
        Connection con = null;  
        PreparedStatement psta = null;  
        ResultSet rs = null;
        List<ArrayList> datalist = new ArrayList<ArrayList>();
        ArrayList list = new ArrayList();
        ArrayList cipherlist = new ArrayList();
        String sql = null;
        if(type==1)
        	 sql= "select * from user order by id";  
        else
        	sql="select * from user order by age";
        if(dire==2)
        	sql=sql+" desc";
        try{  
            con = Basedao.getConnection();  
            Map<String, String> new_query_select = client.RewriteQuery(sql); // 重写语句
            String new_query = new_query_select.get("0");
            System.out.println("in UserinfoDao.orderby new_query: " + new_query);
            psta = con.prepareStatement(new_query);  
            rs = psta.executeQuery();  // 执行重写后的语句
            
            ResultType cipherresult = client.ResultSet2Result(rs);
            Map<String, String> nameCipherMap = cipherresult.getNameMap(); // 属性字段
            ResultType plainresult = client.DecryptResult(cipherresult, sql); // 解密结果集
            Map<String, String> nameMap = plainresult.getNameMap(); // 属性字段
            
            // 密文
            for(int i = 0; i < cipherresult.getValueList().size(); i++) {
            	Map<String, String> valueRowCipherMap = cipherresult.getValueList().get(i); // 一行数据
                UserInfo objCipher = new UserInfo();
                String idCipher = valueRowCipherMap.get(nameCipherMap.get("0"));
                String nameCipher = valueRowCipherMap.get(nameCipherMap.get("1"));
                String ageCipher = valueRowCipherMap.get(nameCipherMap.get("2"));
                String hobbyCipher = valueRowCipherMap.get(nameCipherMap.get("3"));
                String mtextCipher = valueRowCipherMap.get(nameCipherMap.get("4"));
                
                objCipher.set_id(idCipher); 
                objCipher.setUser_name(nameCipher);  
                objCipher.setUser_age(ageCipher);  
                objCipher.setUser_hobby(hobbyCipher);  
                objCipher.setMtext(mtextCipher);  
                cipherlist.add(objCipher);  
            }
            
            for(int i = 0; i < plainresult.getValueList().size(); i++) {
            	Map<String, String> valueRowMap = plainresult.getValueList().get(i); // 一行数据
                UserInfo obj = new UserInfo();
                String id = valueRowMap.get(nameMap.get("0"));
                String name = valueRowMap.get(nameMap.get("1"));
                String age = valueRowMap.get(nameMap.get("2"));
                String hobby = valueRowMap.get(nameMap.get("3"));
                String mtext = valueRowMap.get(nameMap.get("4"));
                
                obj.set_id(id); 
                obj.setUser_name(name);  
                obj.setUser_age(age);  
                obj.setUser_hobby(hobby);  
                obj.setMtext(mtext);  
                list.add(obj);  
            }
            datalist.add(list); // 明文第一
            datalist.add(cipherlist); // 密文第二
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            try{  
                Basedao.close(rs, psta, con);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return datalist;  
        }  
    }  
      
      
      
}  