package com.cmpe.two81.dao;

import java.net.UnknownHostException;
import java.util.Date;

import com.cmpe.two81.viewobject.Store;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class Audit {	
	public static void audit_order(String orderJson) {
        //User Logged In.
        MongoClient mongo = null;
		try {
			mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("audit");
			DBCollection table = db.getCollection("order");
			
			BasicDBObject document = new BasicDBObject();
			DBObject bson = ( DBObject ) JSON.parse( orderJson );
			
			document.putAll(bson);
			table.insert(document);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if( mongo != null) {
				mongo.close();
			}
		}
	}
	
	public static void audit_login(String username, Store store) {
        //User Logged In.
        MongoClient mongo = null;
		try {
			mongo = new MongoClient("localhost", 27017);
	        DB db = mongo.getDB("audit");
	        DBCollection table = db.getCollection("user_login");
	
	        BasicDBObject document = new BasicDBObject();
	        document.put("username", username);
	        document.put("store", store.getName());
	        document.put("loginDate", new Date());
	    	table.insert(document);
		} catch( Exception e) {
			e.printStackTrace();
		} finally {
			mongo.close();
		}
	}
}
