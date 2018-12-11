package com.neuedu.controller.manage;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/category/")
public class CategoryManagerController {


    @Autowired
    ICategoryService categoryService;
    /**
     * 获取品类子节点（平级）
     */
    @RequestMapping(value = "get_category.do")
    //管理员才可以所以要先获取登陆的管理员session里面的信息
    public ServerResponse get_category(HttpSession session,Integer categoryId){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NEED_LOGIN.getCode(),Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限是否为管理员
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return categoryService.get_category(categoryId);
    }
    /**
     * 增加节点
     */
    @RequestMapping(value = "add_category.do")
    //管理员才可以所以要先获取登陆的管理员session里面的信息          Integer parentId,可传可不传,所以要加@RequestParam(required = false,defaultValue = "0")
    public ServerResponse add_category(HttpSession session, @RequestParam(required = false,defaultValue = "0") Integer parentId, String categoryName){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);    //defaultValue = "0" 字符串的 "0" 会自动加为Integer类型
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NEED_LOGIN.getCode(),Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限是否为管理员
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return categoryService.add_category(parentId,categoryName);
    }
    /**
     * 修改节点
     */
    @RequestMapping(value = "set_category_name.do")
    //管理员才可以所以要先获取登陆的管理员session里面的信息          Integer parentId,可传可不传,所以要加@RequestParam(required = false,defaultValue = "0")
    public ServerResponse set_category_name(HttpSession session, Integer categoryId, String categoryName){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NEED_LOGIN.getCode(),Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限是否为管理员
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return categoryService.set_category_name(categoryId,categoryName);
    }
    /**
     * 获取当前分类id及递归子节点categoryId
     */
    @RequestMapping(value = "get_deep_category.do")//查询的是所有后代id，产生的是一个categoryId的集合
    public ServerResponse get_deep_category(HttpSession session,Integer categoryId){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);    //defaultValue = "0" 字符串的 "0" 会自动加为Integer类型
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NEED_LOGIN.getCode(),Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限是否为管理员
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return categoryService.get_deep_category(categoryId);
    }

}
