package com.neuedu.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 封装返回前端的高复用对象
 * @param <T>
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//在转json字符串的时候把空字段过滤掉
//当对这个对象ServerResponse进行转成一个字符的时候，那些非空字段是不会进行转化的
public class ServerResponse<T> {

    //状态码
    private int status;
    //返回接口数据
    private  T  data;
    //接口提示信息
    private String msg;

    /*构造方法写成私有方法是不能被其他类所创建*/
    private ServerResponse(){}
    private ServerResponse(int status){
        this.status=status;
    }
    private ServerResponse(int status,String msg){
        this.status=status;
        this.msg=msg;
    }
    private ServerResponse(int status,String msg,T data){
        this.status=status;
        this.msg=msg;
        this.data=data;
    }
    //对外提供一个调用ServerResponse的方法

    /**
     * 成功
     * @return
     */
    public static ServerResponse createServerResponseBySucess(){
        return new ServerResponse(Const.SUCCESS_CODE);
    }
    public static ServerResponse createServerResponseBySucess(String msg){
        return new ServerResponse(Const.SUCCESS_CODE,msg);
    }
    public static <T> ServerResponse createServerResponseBySucess(String msg,T data){
        return new ServerResponse(Const.SUCCESS_CODE,msg,data);
        //如果只想取status和data值，则可以将msg值置空
    }

    /**
     * 判断接口是否调用成功
     * @return
     */
    @JsonIgnore
    //ServerResponse转json的时候把success这个字段忽略掉
    public boolean isSuccess(){
        return this.status==Const.SUCCESS_CODE;
    }

    /**
     * 失败
     * @return
     */
    public static ServerResponse createServerResponseByErrow(){

        return new ServerResponse(Const.ERROW_CODE);
    }
    public static ServerResponse createServerResponseByErrow(String msg){

        return new ServerResponse(Const.ERROW_CODE,msg);
    }

    //自定义状态码
    public static ServerResponse createServerResponseByErrow(int status){

        return new ServerResponse(status);
    }
    public static ServerResponse createServerResponseByErrow(int status,String msg){

        return new ServerResponse(status,msg);
    }









    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
