package com.neuedu.service.imp;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

   @Autowired
    UserInfoMapper userInfoMapper;
    @Override  //int 数据插到数据库之后，影响到数据库的行数
    /*public int register(UserInfo userInfo) {

        return userInfoMapper.insert(userInfo);
    }*/
    public ServerResponse register(UserInfo userInfo) {

        //step1:参数非空校验
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow("参数必须");
        }
        //step2:校验用户名
        int result_username = userInfoMapper.checkUsername(userInfo.getUsername());
        if (result_username>0){//用户名已经存在
            return ServerResponse.createServerResponseByErrow("用户名已经存在");
        }
        //step3:校验邮箱
        int result_email = userInfoMapper.checkEmail(userInfo.getEmail());
        if (result_email>0){//邮箱已经存在
            return ServerResponse.createServerResponseByErrow("邮箱已经存在");
        }
        //step4:注册
        userInfo.setRole(Const.RoleEnum.ROLE_CUSTPMER.getCode());
        int count = userInfoMapper.insert(userInfo);
        if (count>0){
            return ServerResponse.createServerResponseBySucess("注册成功");
        }
        //step5:返回结果
        return ServerResponse.createServerResponseByErrow("注册失败");

    }

    @Override
    public ServerResponse login(String username, String password) {
        //step1：参数的非空校验
       /* if (username==null || username.equals("")){*/
        if (StringUtils.isBlank(username)){
            return ServerResponse.createServerResponseByErrow("用户名不能为空");
        }
        /*if (password==null || password.equals("")){*/
        if (StringUtils.isBlank(password)){
            return ServerResponse.createServerResponseByErrow("密码不能为空");
        }
        //step2：检查username是否存在
        int result = userInfoMapper.checkUsername(username);
        if (result<=0){//用户名不存在
            return ServerResponse.createServerResponseByErrow("用户名不存在");
        }
        //step3：根据用户名和密码查询
       UserInfo userInfo = userInfoMapper.selectUserByUsernameAndPassword(username,password);
        if (userInfo==null){//密码错误
           return ServerResponse.createServerResponseByErrow("密码错误");
        }
        //step4：处理结果并返回
        userInfo.setPassword("");
        return ServerResponse.createServerResponseBySucess(null,userInfo);
    }
}
