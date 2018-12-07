package com.neuedu.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 基于谷歌guava cache的类
 */
public class TokenCache {

    //获取缓存对象LoadingCach
    /*LoadingCache是谷歌guava里面的一个类，CacheBuilder也是里面的一个类*/
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder()
            //方法链
            .initialCapacity(1000)//初始化缓存项为1000
            .maximumSize(10000)//设置缓存项最大值不超过10000
            .expireAfterAccess(12,TimeUnit.HOURS)//定时回收：过期回收，超过12个小时就会回收掉
            .build(new CacheLoader<String, String>() {//通过builder来构建对象，CacheLoader可以理解为一个内部类
                //当缓存没有值的时候执行load方法
                //当key不存在时就会调用该方法
                @Override
                public String load(String s) throws Exception {
                    return "null";//利用空字符串，不能直接用null，底下用equals比较的时候会报空指针
                }
            });

    /**
     * 向缓存添加键值对
     * @param key
     * @param value
     */
    public static void put(String key,String value){
        localCache.put(key, value);//用put方法来调用缓存
    }

    /**
     * 从缓存里面通过key值获取value值
     * @param key
     * @return
     */
    public static String get(String key){//根据key来获取value

        try {
           String  value = localCache.get(key);
            if (value.equals("null")){
                return null;
            }
            return value;
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        return null;
    }

}