package com.robin.process;

import com.robin.source.DataSource;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by robinmac on 15-4-20.
 */
public class CSVProcesser {
    String[] names=new String[]{"class1","class2","company_name","company_ename","brand","brand_e","spokes_man","add_name","add_content","add_channel","add_fee","brand_price"};
    ArrayList<HashMap<String,String>> datas=new ArrayList<HashMap<String,String>>();
    public void prase(){
        final DataSource ds = new DataSource();
        try {
            ds.getConnection();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        FileUtility.processByFile("/Users/robinmac/project/scrapy/csvdata/1.csv", new FileProcess() {
            @Override
            public void onEachFileLine(String line) {
                String[] s=line.split(",");
                HashMap<String,String> map=new HashMap<String, String>();
                int i=0;
                for(String name :names){
                    map.put(name, s[i]);
                    i++;
                    if(i>=s.length)
                        break;
                }
                datas.add(map);
                ds.saveInfo(map);
            }
        });

    }

    public static void main(String[] args) {
        ArrayBlockingQueue d;
        LinkedBlockingDeque l;
        CSVProcesser cvp=new CSVProcesser();
        cvp.prase();

    }

}
