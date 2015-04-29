package com.robin.actions;

import com.robin.source.DataSource;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DunmpData {
	public static void main(String[] args) {
		String fileurl="/Users/robinmac/workspace/stormspace/addressbook-master/data/d1";
		String[] keys=new String[]{
				"_id","category","source","name","description","labels",
		};
		DataSource ds = new DataSource();
		try {
			ds.getConnection();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		int line=0;
		List<Map> infos = ds.queryInfo(null, "", "");
		for(Map map:infos){
			String s="";
			Set set=map.keySet();
			for(String o:keys){
				String value=String.valueOf(map.get(o));
				value=value.replaceAll(",", " ");
				value=value.replaceAll("\"", "");
				//remove []
				char[] ss=value.toCharArray();
				if(ss[0]=='[')
					value=value.substring(1,value.length()-1);
				//remove null
				if(value.equals("null"))
					value="";
				//remove ba
				if(o.equals("name")&&map.get("source").equals("btieba"))
					value=value.substring(0,value.length()-1);
				s+=value+",";
			}
			System.out.println(s);
			FileUtility.writeLineToEnd(s+"\n", fileurl);
		}
		System.out.println(infos.size());
		ds.closeConnection();
	}
	

}
