package com.neuedu.common;

/**
 * 定义响应状态码
 */
public enum ResponseCode {

    PARAM_EMPTY(2,"参数为空"),
    EXISTS_USERNAME(3,"用户名已经存在"),
    EXISTS_EMALL(4,"邮箱已经存在"),
    NOT_EXISTS_USERNAME(5,"用户名不存在"),
    USER_NOT_LOGIN(6,"用户未登录")
    ;

    private int status;
    private String msg;

    ResponseCode(){}
    ResponseCode(int status,String msg){
        this.status=status;
        this.msg=msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public enum CartCheckedEnum{
        PRODUCT_CHECKED(1,"已勾选"),
        PRODUCT_UNCHECKED(0,"未勾选"),

        ;
        private int code;
        private  String desc;
        CartCheckedEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
