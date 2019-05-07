package jni;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @author Idealeer idealeer521@gmail.com: 
* @version CreateDate: 2017年11月19日 下午5:58:07 
* @ClassName 类名称
* @Description 类描述 
*/
public class ResultType{  
    private Map<String, String> name = new HashMap<String, String>(); // 字段名
    private Map<String, String> type = new HashMap<String, String>(); // 字段类型
    private List<Map<String, String>> value = new ArrayList<Map<String, String>>(); // 字段值
    
    public  ResultType() {}
	
    public  ResultType(Map<String, String> name, Map<String, String> type, List<Map<String, String>> value) {
    	this.name = name;
    	this.type = type;
    	this.value = value;
    }
    
    public  Map<String, String> getNameMap() { return name; }
    public  Map<String, String> getTypeMap() { return type; }
    public  List<Map<String, String>> getValueList() { return value; }
}
