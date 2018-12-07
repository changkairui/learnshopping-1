package com.neuedu.service.imp;


import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IUserService {

   @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 注册
     * @param userInfo
     * @return
     */
   @Override  //int 数据插到数据库之后，影响到数据库的行数
    /*public int register(UserInfo userInfo) {

        return userInfoMapper.insert(userInfo);
    }*/
    public ServerResponse register(UserInfo userInfo) {

        //step1:参数非空校验
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        //step2:校验用户名是否存在
        int result_username = userInfoMapper.checkUsername(userInfo.getUsername());
        /*if (result_username>0){//用户名已经存在
            return ServerResponse.createServerResponseByErrow(ResponseCode.EXISTS_USERNAME.getStatus(),ResponseCode.EXISTS_USERNAME.getMsg());
        }*/
        ServerResponse serverResponse = check_valid(userInfo.getUsername(),Const.USERNAME);
        if (!serverResponse.isSuccess()){
            return ServerResponse.createServerResponseByErrow(ResponseCode.EXISTS_USERNAME.getStatus(),ResponseCode.EXISTS_USERNAME.getMsg());
        }
        //step3:校验邮箱
        int result_email = userInfoMapper.checkEmail(userInfo.getEmail());
       /* if (result_email>0){//邮箱已经存在
            return ServerResponse.createServerResponseByErrow(ResponseCode.EXISTS_EMALL.getStatus(),ResponseCode.EXISTS_EMALL.getMsg());
        }*/
        ServerResponse email_serverResponse = check_valid(userInfo.getEmail(),Const.EMAIL);
        if (!email_serverResponse.isSuccess()){
            return ServerResponse.createServerResponseByErrow(ResponseCode.EXISTS_EMALL.getStatus(),ResponseCode.EXISTS_EMALL.getMsg());
        }
        //step4:注册
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        userInfo.setRole(Const.RoleEnum.ROLE_CUSTPMER.getCode());
        int count = userInfoMapper.insert(userInfo);
        if (count>0){
            return ServerResponse.createServerResponseBySucess("注册成功");
        }
        //step5:返回结果
        return ServerResponse.createServerResponseByErrow("注册失败");

    }

    /**
     * 校验用户名和邮箱
     * @param str
     * @param type
     * @return
     */

    @Override
    public ServerResponse check_valid(String str, String type) {
        //1:参数的非空校验
        if(StringUtils.isBlank(str)||StringUtils.isBlank(type)){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }
        //2:判断用户名或者邮箱是否存在
        if (type.equals(Const.USERNAME)){
            int username_result = userInfoMapper.checkUsername(str);
             if(username_result>0){
                return ServerResponse.createServerResponseByErrow(ResponseCode.EXISTS_USERNAME.getStatus(),ResponseCode.EXISTS_USERNAME.getMsg());
             }
            return ServerResponse.createServerResponseBySucess("成功");
        }else if (type.equals(Const.EMAIL)){
            int email_result = userInfoMapper.checkEmail(str);
            if (email_result>0){
                return  ServerResponse.createServerResponseByErrow(ResponseCode.EXISTS_EMALL.getStatus(),ResponseCode.EXISTS_EMALL.getMsg());
            }
            return ServerResponse.createServerResponseBySucess("成功");
        }
        //3:返回结果

        return ServerResponse.createServerResponseByErrow("type参数传递有误");
    }

    /**
     * 提交问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
       //step1:参数校验
        if(StringUtils.isBlank(username)||StringUtils.isBlank(question)|| StringUtils.isBlank(answer)){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }
        // step2:根据username,question,answer查询
        int result= userInfoMapper.selectByUsernameAndQuestionAndAnswer(username, question, answer);
        if(result==0){
            return ServerResponse.createServerResponseByErrow("答案错误");
        }
        //step3:服务端生成一个token保存并将token返回给客户端
        String forgetToken=UUID.randomUUID().toString();
        //guava cache
        return null;
    }

    /**
     * 忘记密码
     * @param username
     * @return
     */

    @Override
    public ServerResponse forget_reset_password(String username) {
        //step1:参数的非空校验
        if (StringUtils.isBlank(username)){
            return ServerResponse.createServerResponseByErrow(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }


        //step2:校验username
        ServerResponse serverResponse = check_valid(username,Const.USERNAME);
        if (serverResponse.isSuccess()){
            return ServerResponse.createServerResponseByErrow(ResponseCode.NOT_EXISTS_USERNAME.getStatus(),ResponseCode.NOT_EXISTS_USERNAME.getMsg());
        }
        //step3:查找密保问题

        String question = userInfoMapper.selectQuestionByUsername(username);
        if (StringUtils.isBlank(username)){
            return ServerResponse.createServerResponseByErrow("密保问题为空");
        }


        return ServerResponse.createServerResponseBySucess(question);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
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
        /*int result = userInfoMapper.checkUsername(username);
        if (result<=0){//用户名不存在
            return ServerResponse.createServerResponseByErrow("用户名不存在");
        }*/
        ServerResponse serverResponse = check_valid(username,Const.USERNAME);
        if (serverResponse.isSuccess()){
            return ServerResponse.createServerResponseByErrow(ResponseCode.NOT_EXISTS_USERNAME.getStatus(),ResponseCode.NOT_EXISTS_USERNAME.getMsg());
        }
        //step3：根据用户名和密码查询
       UserInfo userInfo = userInfoMapper.selectUserByUsernameAndPassword(username,MD5Utils.getMD5Code(password));
        if (userInfo==null){//密码错误
           return ServerResponse.createServerResponseByErrow("密码错误");
        }
        //step4：处理结果并返回
        userInfo.setPassword("");
        return ServerResponse.createServerResponseBySucess(null,userInfo);

        //登录成功后userInfo保存在session当中



    }
}
