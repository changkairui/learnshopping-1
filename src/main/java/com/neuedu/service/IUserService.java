package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;

import javax.servlet.http.HttpSession;

public interface IUserService {
    /**
     * 注册接口
     */
    /* public int register(UserInfo userInfo);*/
    public ServerResponse register(UserInfo userInfo);

    /**
     * 登录
     */
    public ServerResponse login(String username, String password);

    /**
     * 检查用户名或邮箱是否有效
     */
    public ServerResponse check_valid(String str, String type);

    /**
     * 根据用户名查找密保问题
     */
    public ServerResponse forget_get_question(String username);

    /**
     * 校验答案
     */
    public ServerResponse forget_check_answer(String username, String question, String answer);

    /**
     * 修改密码
     */
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken);

    /**
     * 登录状态下修改密码
     *
     * @param userInfo
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    public ServerResponse reset_password(UserInfo userInfo, String passwordOld, String passwordNew);
}