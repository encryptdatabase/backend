package com.hello.jni;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import dao.UserinfoDao;

import jni.client;
@CrossOrigin(origins = {"http://localhost:8000", "null"})
@RestController

public class Hello {
	private UserinfoDao user= new UserinfoDao();

	@RequestMapping("/login")
	public String test() {
		String host = "127.0.0.1";
    	String user = "root";
    	String pwd = "letmein";
    	String dbname = "xinan";
    	String master_key = "2392834";
    	System.out.println(client.EncryptineConnect(host, user, pwd, dbname, master_key));
    	return "hello";
    	// return (client.EncryptineConnect(host, user, pwd, dbname, master_key));
//		System.out.println(client.RewriteQuery("select * from user").get("0"));
	}
	@RequestMapping("/getAll")
	@ResponseBody
	public List<ArrayList> getall() {
		System.out.println("****");
		UserinfoDao dao = new UserinfoDao();  
	    List<ArrayList> listdata = dao.findAll();
	    ArrayList listplain = listdata.get(0); 
	    ArrayList listcipher = listdata.get(1);
    	return listdata;
    	// return (client.EncryptineConnect(host, user, pwd, dbname, master_key));
//		System.out.println(client.RewriteQuery("select * from user").get("0"));
	}
	@RequestMapping("/order")
	@ResponseBody
	public List<ArrayList> order(@RequestParam int type,@RequestParam int dire) {
		System.out.println("****");
		UserinfoDao dao = new UserinfoDao();  
	    List<ArrayList> listdata = dao.orderby(type, dire);

    	return listdata;
    	// return (client.EncryptineConnect(host, user, pwd, dbname, master_key));
//		System.out.println(client.RewriteQuery("select * from user").get("0"));
	}
	@RequestMapping("/select")
	@ResponseBody
	public List<ArrayList> select (@RequestParam String label1,@RequestParam String label2,@RequestParam String value) {
		System.out.println("****");
		System.out.println(label1);
		System.out.println(value);
		//System.out.println(label[1]);
		UserinfoDao dao = new UserinfoDao();  
	    List<ArrayList> listdata =dao.select(label1, label2, value);

    	return listdata;
    	// return (client.EncryptineConnect(host, user, pwd, dbname, master_key));
//		System.out.println(client.RewriteQuery("select * from user").get("0"));
	}


}
