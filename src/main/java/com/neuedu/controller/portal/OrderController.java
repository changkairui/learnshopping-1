package com.neuedu.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping(value = "/portal/order/")
public class OrderController {

    @Autowired
    IOrderService orderService;

    /**
     * 创建订单
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping(value = "create.do")
    public ServerResponse create(HttpSession session, Integer shippingId){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        return orderService.create(userInfo.getId(),shippingId);
    }

    /**
     * 取消订单
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "cancel.do")
    public ServerResponse cancel(HttpSession session, Long orderNo ){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        return orderService.cancel(userInfo.getId(),orderNo);
    }
    /**
     * 获取订单的商品信息
     * 把List<Cart>转成Order
     */
    @RequestMapping(value = "get_order_cart_product.do")
    public ServerResponse get_order_cart_product(HttpSession session){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        return orderService.get_order_cart_product(userInfo.getId());
    }

    /**
     * 订单List
     * @param session
     * @return
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        return orderService.list_portal(userInfo.getId(),pageNum,pageSize);
    }
    /**
     * 查询订单详情
     */
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(HttpSession session,Long orderNo){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        return orderService.detail(orderNo);
    }
    /**
     * 支付接口
     */
    @RequestMapping(value = "pay.do")
    public ServerResponse pay(HttpSession session,Long orderNo){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }


        return orderService.pay(userInfo.getId(),orderNo);
    }
    /**
     * 支付宝服务器回调应用服务器接口
     */
    @RequestMapping(value = "alipay_callback.do")
    public ServerResponse callback(HttpServletRequest request){
        System.out.println("======支付宝服务器回调应用服务器接口");

        //保证是支付宝调用该接口

        //获取支付宝传过来的参数
        Map<String,String[]> params = request.getParameterMap();
        //把value的数组的字符串转为用逗号隔开的字符串
        Map<String,String> requestparam = Maps.newHashMap();
       Iterator<String>iterator =  params.keySet().iterator();
       while (iterator.hasNext()){
           String key = iterator.next();
           String[] strArr = params.get(key);
           String value = "";
           for (int i = 0; i <strArr.length ; i++) {
               value =(i==strArr.length-1)?value+strArr[i]: value+strArr[i]+",";

           }
           requestparam.put(key,value);
       }



        //step1:支付宝验签

        try {
           //验签之前
            requestparam.remove("sign_type");

            boolean result = AlipaySignature.rsaCheckV2(requestparam,Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if (!result){
                return ServerResponse.createServerResponseByErrow("非法请求，验证不通过");
            }
            //处理业务逻辑

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderService.alipay_callback(requestparam);
    }

    /**
     * 查询订单的支付状态
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "query_order_pay_status.do")
    public ServerResponse query_order_pay_status(HttpSession session,Long orderNo){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByErrow(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }


        return orderService.query_order_pay_status(orderNo);
    }

}
