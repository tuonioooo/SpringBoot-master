package com.optimistic.master.utils;

import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;

/**
 * Created by daizhao.
 * User: tony
 * Date: 2018-11-5
 * Time: 16:32
 * info:
 */
public class MemcachedUtils {

    private static MemcachedClient client;

    static {

        try{
            // 本地连接 Memcached 服务
            client = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
            System.out.println("Connection to server sucessful.");
            // 关闭连接
            //mcc.shutdown();
        }catch(Exception ex){
            System.out.println( ex.getMessage() );
        }
    }

    public static MemcachedClient getClient(){
        return client;
    }
}
