package com.robin.source;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaultDataSource {
	MongoClient mongo;
	DB mongodb;
	DBCollection bfenleiTable;

	public void getConnection() throws UnknownHostException {
		mongo = new MongoClient("localhost", 27017);
		mongodb = mongo.getDB("logic_monitor");
		bfenleiTable = mongodb.getCollection("finfos");
	}

	public void closeConnection() {
		mongodb.cleanCursors(false);
		mongo.close();
	}
	public void saveInfo(HashMap<String, String> map) {
		// 更新一条记录

		BasicDBObject updatedinfo = new BasicDBObject();
		for (String key : map.keySet()) {
			if(key.equals("_id"))
				continue;
			String value = map.get(key);
			updatedinfo.put(key, value);
		}
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", updatedinfo);
		bfenleiTable.save(updatedinfo);
	}

	public void updateInfo(DBCollection table, String name,
			HashMap<String, String> map) {
		if (table == null)
			table = bfenleiTable;
		// 更新一条记录
		System.out.println("Calling update Message......"+map);
		BasicDBObject query = new BasicDBObject();
		query.put("name", name);

		BasicDBObject updatedinfo = new BasicDBObject();
		for (String key : map.keySet()) {
			if(key.equals("_id"))
				continue;
			String value = map.get(key);
			updatedinfo.put(key, value);
		}
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", updatedinfo);
		table.update(query, updateObj);
	}

	public List<Map> queryInfo(DBCollection table, String key, String value) {
		if (table == null)
			table = bfenleiTable;
		ArrayList<Map> reslist = new ArrayList<Map>();
		BasicDBObject query = new BasicDBObject();
		query.put(key, value);
		DBCursor cursor = null;
		if (key == null || key.length() == 0)
			cursor = table.find();
		else
			cursor = table.find(query);
		while (cursor.hasNext()) {
			DBObject o = cursor.next();
			reslist.add(o.toMap());
		}
		return reslist;
	}

	public static void main(String[] args) {
		FaultDataSource ds = new FaultDataSource();
		try {
//			ds.testConnection();
			ds.getConnection();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("haha", "hehe");
//		ArrayList<String> typs=new ArrayList<String>();
//		typs.add("yanyuan");
//		typs.add("geshou");
//		typs.add("mingxing");
//		typs.add("daoyan");
//		typs.add("zhuchiren");
//		typs.add("mote");
//		typs.add("dianying");
//		typs.add("yule");
//		List<Map> infos = ds.queryInfo(null, "", "");
//		System.out.println(infos.size());
//		ds.closeConnection();
	}

}
