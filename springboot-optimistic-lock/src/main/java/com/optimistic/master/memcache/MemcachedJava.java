package com.optimistic.master.memcache;

import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;


public class MemcachedJava {
   public static void main(String[] args) {
      try{
         // 本地连接 Memcached 服务
         MemcachedClient client = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
         System.out.println("Connection to server sucessful.");

         CASValue<Object> tgoodsinfo = client.gets("10003");
         System.out.println("tgoodsinfo.getCas() = " + tgoodsinfo.getCas());
         System.out.println("tgoodsinfo.getValue() = " + tgoodsinfo.getValue());

         // 关闭连接
         client.shutdown();

      }catch(Exception ex){
         System.out.println( ex.getMessage() );
      }
   }
}
