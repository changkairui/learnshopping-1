package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/portal/shipping/")
public class AddressContorller {


    @Autowired
    IAddressService iAddressService;
    @RequestMapping(value = "add.do")
    public ServerResponse add(HttpSession session, Shipping shipping){
       UserInfo userInfo =(UserInfo)session.getAttribute(Const.CURRENTUSER);
       if (userInfo==null){
           return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
       }

       return iAddressService.add(userInfo.getId(),shipping);

    }

    /**
     * 删除地址
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping(value = "del.do")
    public ServerResponse del(HttpSession session, Integer shippingId){
        UserInfo userInfo =(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }

        return iAddressService.del(userInfo.getId(),shippingId);

    }

    /**
     * 登录状态更新地址
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping(value = "update.do")
    public ServerResponse update(HttpSession session, Shipping shipping){
        UserInfo userInfo =(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }

        shipping.setUserId(userInfo.getId());//??????????
        return iAddressService.update(shipping);

    }
    /**
     * 选中查看具体地址
     */
    @RequestMapping(value = "select.do")
    public ServerResponse select(HttpSession session, Integer shippingId){
        UserInfo userInfo =(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }


        return iAddressService.select(userInfo.getId(),shippingId);

    }

    /**
     * 地址列表
     * @param session
     * @return
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        UserInfo userInfo =(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }


        return iAddressService.list(userInfo.getId(),pageNum,pageSize);

    }
}
