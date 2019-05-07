package jni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ConnInfo{
	public int  conninfo_size;
	public int  host_index;
	public int  user_index;
	public int  pwd_index;
	public int  dbname_index;
	public int  masterkey_index;
}

public class client {
	public client() {}
	
	// 建立应用端与加密机的连接
    public native static String EncryptineConnect(String host, String user, String pwd, String dbname, String master_key); 
	// 重写SQL语句
    public native static Map<String, String> RewriteQuery(String query);
	// 解密结果集
    public native static ResultType DecryptResult(ResultType result, String query);
    
    public static ResultType ResultSet2Result(ResultSet resultSet) throws SQLException {
        Map<String, String> nameMap = new HashMap<String, String>();
        Map<String, String> typeMap = new HashMap<String, String>();
		List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
		ResultSetMetaData meta = resultSet.getMetaData();
		String rowName, rowVal;
		int rowType;
    	for(int i = 1; i <= meta.getColumnCount(); i++ ) {
    		rowName = meta.getColumnName(i);
    		rowType = meta.getColumnType(i);
    		if(rowType == -5)
    			rowType += 13;
    		if(rowType == -3)
    			rowType += 256;
    		System.out.println("rowType: " + rowType);
    		nameMap.put(Integer.toString(i - 1), rowName);
    		typeMap.put(Integer.toString(i - 1), Integer.toString(rowType));
    	}
    	while (resultSet.next()) {
    		Map<String, String> rowMap = new HashMap<String, String>();
    		for ( int i = 1; i <= meta.getColumnCount(); i++ ){
    			rowName = meta.getColumnName(i);
    			rowVal  = resultSet.getString(meta.getColumnName(i));			
    			rowMap.put(rowName, rowVal == null ? "" : rowVal);
    		}
				valueList.add(rowMap);
		}
		resultSet.close();
		ResultType result = new ResultType(nameMap, typeMap, valueList);
		return result;
	}
    static {
    	System.load("C://Windows//System32//JNIWin.dll");
    }
}
