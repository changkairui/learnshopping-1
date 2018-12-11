package com.neuedu.controller.manage;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IProductService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/product/")
public class ProductManagerController {

    @Autowired
    IProductService productService;
    /**
     * 新增OR更新产品
     * @param session
     * @param product
     * @return
     */
    @RequestMapping(value = "save.do")
    public ServerResponse save(HttpSession session, Product product){
       UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
       if (userInfo==null){
           return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NEED_LOGIN.getCode(),Const.ResponseCodeEnum.NEED_LOGIN.getDesc());

       }
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
           return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return productService.saveOrUpdate(product);
    }
    /**
     * 商品的上下架
     */
    @RequestMapping(value = "set_sale_status.do")
    public ServerResponse set_sale_status(HttpSession session,Integer productId,Integer status){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NEED_LOGIN.getCode(),Const.ResponseCodeEnum.NEED_LOGIN.getDesc());

        }
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return productService.set_sale_status(productId,status);
    }
    /**
     * 产品详情
     */
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(HttpSession session,Integer productId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NEED_LOGIN.getCode(),Const.ResponseCodeEnum.NEED_LOGIN.getDesc());

        }
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return productService.detail(productId);
    }

    /**
     * 查看商品列表
     * @param session
     * @param pageNum  页数
     * @param pageSize 每页默认有几条数据
     * @return
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NEED_LOGIN.getCode(),Const.ResponseCodeEnum.NEED_LOGIN.getDesc());

        }
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return productService.list(pageNum,pageSize);
    }

    /**
     * 产品搜索
     * @param session
     * @param productId
     * @param productName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "search.do")
    public ServerResponse search(HttpSession session,
                                 @RequestParam(value = "pageNum",required = false)Integer productId,
                                 @RequestParam(value = "pageSize",required = false)String productName,
                                 @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NEED_LOGIN.getCode(),Const.ResponseCodeEnum.NEED_LOGIN.getDesc());

        }
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByErrow(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return productService.search(productId,productName,pageNum,pageSize);
    }

}
