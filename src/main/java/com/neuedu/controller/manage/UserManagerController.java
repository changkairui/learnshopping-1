package com.neuedu.controller.manage;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/user/")
public class UserManagerController {


    @Autowired
    IUserService iUserService;

    /**
     * 管理员登录
     * @param session
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login.do")
    public ServerResponse login(HttpSession session, String username, String password){
        ServerResponse serverResponse = iUserService.login(username, password);
        if (serverResponse.isSuccess()){
            UserInfo userInfo = (UserInfo)serverResponse.getData();
            if (userInfo.getRole()==Const.RoleEnum.ROLE_CUSTPMER.getCode()){
                return ServerResponse.createServerResponseByErrow("无权限登录");
            }
            session.setAttribute(Const.CURRENTUSER,serverResponse.getData());

        }
        return serverResponse;
    }

}
