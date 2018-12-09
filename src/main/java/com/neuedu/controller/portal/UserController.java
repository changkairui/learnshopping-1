package com.neuedu.controller.portal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/portal/user/")
public class UserController {

    @Autowired
    IUserService iUserService;

    /**
     * 登录
     * @param session
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login.do")
    public ServerResponse login(HttpSession session, String username, String password){

        //对这两个接口代码的优化和重构
        ServerResponse serverResponse = iUserService.login(username,password);
        if (serverResponse.isSuccess()){//登录成功后才能保存在session当中
           //将用户信息保存，往session里面保存数据       userInfo的获取
            session.setAttribute(Const.CURRENTUSER,serverResponse.getData());
        }
        return serverResponse;
    }

    /**
     * 注册
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "register.do")
    public ServerResponse register(UserInfo userInfo){
        return iUserService.register(userInfo);
    }
    /**
     * 根据username查找密保问题
     * @param username
     * @return
     */
    @RequestMapping(value = "forget_get_question.do")
    public ServerResponse forget_get_question(String username){
        return iUserService.forget_get_question(username);
    }
    /**
     * 提交问题答案
     */
    @RequestMapping(value = "forget_check_answer.do")
    public ServerResponse forget_check_answer(String username,String question,String answer){
        return iUserService.forget_check_answer(username,question,answer);
    }
    /**
     * 校验用户名或邮箱接口
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "check_valid.do")
    public ServerResponse check_valid(String str,String type){
       return iUserService.check_valid(str, type);
    }
    /**
     * 修改密码
     */
    @RequestMapping(value = "forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String passwordNew,String forgetToken){
        return iUserService.forget_reset_password(username,passwordNew,forgetToken);
    }
    /**
     * 获取当前登陆用户的详细信息
     * */
    @RequestMapping(value = "get_information.do")
    public ServerResponse get_information(HttpSession session){
        Object o = session.getAttribute(Const.CURRENTUSER);
        //判断是不是userInfo类型
        if (o!=null && o instanceof UserInfo){
            UserInfo userInfo = (UserInfo)o;
            return ServerResponse.createServerResponseBySucess(null,userInfo);


        }
        return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
    /**
     * 获取登录用户信息
     */
    @RequestMapping(value = "get_user_info.do")
    public ServerResponse get_user_info(HttpSession session){
        Object o = session.getAttribute(Const.CURRENTUSER);
        //判断是不是userInfo类型
        if (o!=null && o instanceof UserInfo){
            UserInfo userInfo = (UserInfo)o;
            //不需要全部信息都返回重新new
            UserInfo responseUserInfo=new UserInfo();
            responseUserInfo.setId(userInfo.getId());
            responseUserInfo.setUsername(userInfo.getUsername());
            responseUserInfo.setEmail(userInfo.getEmail());
            responseUserInfo.setCreateTime(userInfo.getCreateTime());
            responseUserInfo.setUpdateTime(userInfo.getUpdateTime());
            return ServerResponse.createServerResponseBySucess(null,responseUserInfo);


        }
        return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
    /**
     * 退出登录
     */
    @RequestMapping(value = "logout.do")
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENTUSER);
        return ServerResponse.createServerResponseBySucess("退出登录");
    }
    /**
     * 登录状态重置密码
     */
    @RequestMapping(value = "reset_password.do")
    public ServerResponse reset_password(HttpSession session, String passwordOld,String passwordNew){

        Object o = session.getAttribute(Const.CURRENTUSER);
        if (o!=null && o instanceof UserInfo){
            UserInfo userInfo = (UserInfo)o;
            return iUserService.reset_password(userInfo,passwordOld,passwordNew);

        }
        return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
    /**
     * 登录状态更新个人信息
     */
    @RequestMapping(value = "update_information.do")
    public ServerResponse update_information(HttpSession session, UserInfo user){

        Object o = session.getAttribute(Const.CURRENTUSER);
        if (o!=null && o instanceof UserInfo){
            UserInfo userInfo = (UserInfo)o;
            user.setId(userInfo.getId());//可以通过userid来更新用户
           ServerResponse serverResponse = iUserService.update_information(user);

           if (serverResponse.isSuccess()){
            //得到最新的用户,更新session中的用户信息
            UserInfo userInfo1 =  iUserService.findUserInfoByUserid(userInfo.getId());
            session.setAttribute(Const.CURRENTUSER,userInfo1);
           }
        }
        return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
}
