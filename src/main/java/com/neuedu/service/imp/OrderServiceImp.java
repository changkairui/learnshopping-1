package com.neuedu.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.neuedu.VO.CartOrderItemVO;
import com.neuedu.VO.OrderItemVO;
import com.neuedu.VO.OrderVO;
import com.neuedu.VO.ShippingVO;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.*;
import com.neuedu.pojo.*;
import com.neuedu.service.IOrderService;
import com.neuedu.utils.BigDecimalUtils;
import com.neuedu.utils.DateUtils;
import com.neuedu.utils.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class OrderServiceImp implements IOrderService {


    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ShippingMapper shippingMapper;

    /**
     * 创建订单
     */
    @Override
    public ServerResponse create(Integer userId, Integer shippingId) {
        //step1：参数非空校验
        if (shippingId==null){
            return ServerResponse.createServerResponseByErrow("地址参数不能为空");
        }
        //step2：根据userId查询购物车中已选中的商品 --> List<Cart>

         List<Cart> cartList = cartMapper.findCartListByUserIdAndChecked(userId);

        //step3：List<Cart> --> List<OrderItem>
        ServerResponse serverResponse = getCartOrderItem(userId,cartList);
        if (!serverResponse.isSuccess()){
            return serverResponse;//如果不成功返回错误信息
        }
        //step4：创建订单order并将其保存到数据库中
          //计算订单的价格
        BigDecimal orderTotalPrice = new BigDecimal("0");
        List<OrderItem>orderItemList = (List<OrderItem>)serverResponse.getData();//???????
        if(orderItemList==null||orderItemList.size()==0){
            return ServerResponse.createServerResponseByErrow("购物车为空");
        }
        getOrderPrice(orderItemList);
        Order order = create(userId, shippingId,orderTotalPrice);
        if (order == null){
            return ServerResponse.createServerResponseByErrow("订单创建失败");
        }
        //step5：将List<OrderItem>保存到数据库中
        for (OrderItem orderItem:orderItemList){
            orderItem.setOrderNo(order.getOrderNo());//这儿怎么体现一对多，为什么一个订单可以有多个订单编号
        }
        //批量插入
        orderItemMapper.insertBatch(orderItemList);
        //step6：扣库存
          reduceProductStock(orderItemList);
        //step7：购物车中清空已下单的商品

        cleanCart(cartList);
        //step8：返回，OrderVO

        OrderVO orderVO = assembleOrderVO(order,orderItemList,shippingId);

        return ServerResponse.createServerResponseBySucess(null,orderVO) ;
    }
    /**
     * 取消订单
     */
    @Override
    public ServerResponse cancel(Integer userId, Long orderNo) {
        //step1：参数非空校验
        if(orderNo==null){
            return ServerResponse.createServerResponseBySucess("参数不能为空");
        }
        //step2：根据userId和orderNo查询订单
        Order order = orderMapper.finfOrderByUserIdAndOrderNo(userId,orderNo);
        if (order==null){
            return ServerResponse.createServerResponseByErrow("查询的订单不存在");
        }
        //step3：判断订单状态并取消
        if (order.getStatus()!=Const.OrderStatusEnum.ORDER_UN_PAY.getCode()){

            return ServerResponse.createServerResponseByErrow("订单不能取消");
        }
        //step4：返回结果
        order.setStatus(Const.OrderStatusEnum.ORDER_CANCELED.getCode());
        int result = orderMapper.updateByPrimaryKey(order);
        if (result>0){
            return ServerResponse.createServerResponseBySucess("订单取消成功");
        }
        return ServerResponse.createServerResponseByErrow("订单取消失败");
    }

    /**
     * 获取购物车中的订单明细
     * @param userId
     * @return
     */
    @Override
    public ServerResponse get_order_cart_product(Integer userId) {
        //step1：查询购物车
        List<Cart> cartList = cartMapper.findCartListByUserIdAndChecked(userId);
        //step2：List<Cart>-->List<OrderItem>
        ServerResponse serverResponse = getCartOrderItem(userId,cartList);
        if (!serverResponse.isSuccess()){
            return serverResponse;
        }
        //step3：组装VO
        CartOrderItemVO cartOrderItemVO = new CartOrderItemVO();
        cartOrderItemVO.setImageHost(PropertiesUtils.readByKey("imageHost"));
        List<OrderItem> orderItemList = (List<OrderItem>)serverResponse.getData();
        List<OrderItemVO> orderItemVOList = Lists.newArrayList();
        if (orderItemList==null||orderItemList.size()==0){
            return ServerResponse.createServerResponseByErrow("购物车为空");
        }
        for (OrderItem orderItem:orderItemList){
            orderItemVOList.add(assembleOrderItemVO(orderItem));

        }
        cartOrderItemVO.setOrderItemVOList(orderItemVOList);

        cartOrderItemVO.setTotalPrice(getOrderPrice(orderItemList));

        //step4：返回结果
        return ServerResponse.createServerResponseBySucess(null,cartOrderItemVO);
    }

    /**
     * 前台--订单列表
     * @param userId
     * @return
     */
    @Override
    public ServerResponse list_portal(Integer userId,Integer pageNum,Integer pageSize) {
        //如果按用户查询订单是前台用户订单列表，不按这个查就是管理员后台订单列表
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList=Lists.newArrayList();

            //查询当前用户
            orderList = orderMapper.findOrderByUserId(userId);

        if (orderList==null||orderList.size()==0){
            return ServerResponse.createServerResponseByErrow("未查询到订单信息");
        }
        List<OrderVO> orderVOList = Lists.newArrayList();
        for (Order order:orderList){
           List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderNo(order.getOrderNo());
            OrderVO orderVO =  assembleOrderVO(order,orderItemList,order.getShippingId());
            orderVOList.add(orderVO);
        }
        PageInfo pageInfo = new PageInfo(orderVOList);
        return ServerResponse.createServerResponseBySucess(null,pageInfo);
    }
    /**
     * 后台--订单列表
     * @return
     */
    @Override
    public ServerResponse list_manager(Integer pageNum,Integer pageSize) {
        //如果按用户查询订单是前台用户订单列表，不按这个查就是管理员后台订单列表
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList=Lists.newArrayList();

        //查询所有
        orderList = orderMapper.selectAll();

        if (orderList==null||orderList.size()==0){
            return ServerResponse.createServerResponseByErrow("未查询到订单信息");
        }
        List<OrderVO> orderVOList = Lists.newArrayList();
        for (Order order:orderList){
            List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderNo(order.getOrderNo());
            OrderVO orderVO =  assembleOrderVO(order,orderItemList,order.getShippingId());
            orderVOList.add(orderVO);
        }
        PageInfo pageInfo = new PageInfo(orderVOList);
        return ServerResponse.createServerResponseBySucess(null,pageInfo);
    }
    /**
     * 查询订单详情
     */
    @Override
    public ServerResponse detail(Long orderNo) {
        //step1：参数非空校验
        if(orderNo==null){
            return ServerResponse.createServerResponseBySucess("参数不能为空");
        }

        //step2：查询订单
        Order order = orderMapper.finfOrderByOrderNo(orderNo);
        if (order==null){
            return ServerResponse.createServerResponseByErrow("查询的订单不存在");
        }
        //step3：获取orderVO
        List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderNo(order.getOrderNo());
        OrderVO orderVO = assembleOrderVO(order,orderItemList,order.getShippingId());
        //step4：返回结果
        return ServerResponse.createServerResponseBySucess(null,orderVO);
    }

    /**
     * 构建OrderVO
     */
    private OrderVO assembleOrderVO(Order order,List<OrderItem> orderItemList,Integer shippingId){
        OrderVO orderVO = new OrderVO();
        List<OrderItemVO> orderItemVOList = Lists.newArrayList();
        //遍历orderItem集合
        for (OrderItem orderItem:orderItemList){
            OrderItemVO orderItemVO = assembleOrderItemVO(orderItem);
        }
        orderVO.setOrderItemVOList(orderItemVOList);
        orderVO.setImageHost(PropertiesUtils.readByKey("imageHost"));
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if (shipping!=null){
            orderVO.setShippingId(shippingId);
            ShippingVO shippingVO = assmbleShippingVO(shipping);
            orderVO.setShippingVo(shippingVO);
            orderVO.setReceiverName(shipping.getReceiverName());
        }
        orderVO.setStatus(order.getStatus());
        Const.OrderStatusEnum orderStatusEnum = Const.OrderStatusEnum.codeOf(order.getStatus());
        if (orderStatusEnum!=null){
            orderVO.setStatusDesc(orderStatusEnum.getDesc());
        }
        orderVO.setPostage(0);
        orderVO.setPayment(order.getPayment());
        orderVO.setPaymentType(order.getPaymentType());
        Const.PaymentEnum paymentEnum = Const.PaymentEnum.codeOf(order.getPaymentType());
        if (paymentEnum!=null){
            orderVO.setPaymentTypeDesc(paymentEnum.getDesc());
        }
        orderVO.setOrderNo(order.getOrderNo());


        return orderVO;
    }

    /**
     * shippingVO
     */
    private ShippingVO assmbleShippingVO(Shipping shipping){
        ShippingVO shippingVO = new ShippingVO();
        if (shippingVO!=null){
            shippingVO.setReceiverAddress(shipping.getReceiverAddress());
            shippingVO.setReceiverCity(shipping.getReceiverCity());
            shippingVO.setReceiverDistrict(shipping.getReceiverDistrict());
            shippingVO.setReceiverMobile(shipping.getReceiverMobile());
            shippingVO.setReceiverName(shipping.getReceiverName());
            shippingVO.setReceiverPhone(shipping.getReceiverPhone());
            shippingVO.setReceiverProvince(shipping.getReceiverProvince());
            shippingVO.setReceiverZip(shipping.getReceiverZip());
        }
        return shippingVO;
    }
    /**
     * 转换成OrderItemVO
     */
    private OrderItemVO assembleOrderItemVO(OrderItem orderItem){
        OrderItemVO orderItemVO = new OrderItemVO();
        if (orderItem!=null){
            orderItemVO.setQuantity(orderItem.getQuantity());
            orderItemVO.setCreateTime(DateUtils.dateToStr(orderItem.getCreateTime()));
            orderItemVO.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
            orderItemVO.setOrderNo(orderItem.getOrderNo());
            orderItemVO.setProductId(orderItem.getProductId());
            orderItemVO.setProductImage(orderItem.getProductImage());
            orderItemVO.setProductName(orderItem.getProductName());
            orderItemVO.setTotalPrice(orderItem.getTotalPrice());
        }
        return orderItemVO;
    }
    /**
     * 清空购物车中已选中的商品
     */
    private void cleanCart(List<Cart> cartList){

        if (cartList!=null&&cartList.size()>0){
            cartMapper.batchDelete(cartList);
        }
    }
    /**
     * 扣库存
     */
    private void reduceProductStock(List<OrderItem> orderItemList){
        if (orderItemList!=null&&orderItemList.size()>0){
            for (OrderItem orderItem:orderItemList){
                Integer productId = orderItem.getProductId();
                Integer quantity = orderItem.getQuantity();
                Product product = productMapper.selectByPrimaryKey(productId);
                product.setStock(product.getStock()-quantity);
                productMapper.updateByPrimaryKey(product);
            }
        }
    }
    /**
     * 计算订单的总价格
     */
    private BigDecimal getOrderPrice(List<OrderItem> orderItemList){
        BigDecimal bigDecimal = new BigDecimal("0");

        for (OrderItem orderItem:orderItemList){
            bigDecimal = BigDecimalUtils.add(bigDecimal.doubleValue(),orderItem.getTotalPrice().doubleValue());
        }


        return bigDecimal;
    }

    /**
     * 创建订单
     * @return
     */
    private Order create(Integer userId,Integer shippingId,BigDecimal orderTotalPrice){

        Order order = new Order();
        order.setOrderNo(generateOrderNO());
        order.setUserId(userId);
        order.setShippingId(shippingId);
        order.setStatus(Const.OrderStatusEnum.ORDER_UN_PAY.getCode());//定义一个订单状态的枚举类
        order.setPayment(orderTotalPrice);//订单金额和订单明细有关
        order.setPostage(0);
        order.setPaymentType(Const.PaymentEnum.ONLINE.getCode());//支付类型定义枚举

        //保存订单，放到数据库里
        int result = orderMapper.insert(order);
        if (result>0){
            return order;
        }
        return null;

    }

    /**
     * 生成订单编号
     * */
    private long generateOrderNO(){
        //保证订单编号的唯一性，只适用于用户量不是很大的情况下
        //以下单客户的下单时间戳为订单编号,为解决客户肯恩刚在同时下单，则要再加一个100以内的随机数
        return System.currentTimeMillis()+new Random().nextInt(100);
    }
    private ServerResponse getCartOrderItem(Integer userId,List<Cart> cartList){
        if (cartList==null||cartList.size()==0){
            return ServerResponse.createServerResponseByErrow("购物车是空的");
        }
        List<OrderItem> orderItemList = Lists.newArrayList();
        for (Cart cart:cartList){

            OrderItem orderItem = new OrderItem();
            orderItem.setUserId(userId);
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());
            if(product==null){//判断商品是否存在
                return ServerResponse.createServerResponseByErrow("id为"+cart.getProductId()+"的商品不存在");
            }
            if (product.getStatus()!=Const.ProductStatusEnum.PRODUCT_ONLINE.getCode()){//判断商品是否下架
                return ServerResponse.createServerResponseByErrow("id为"+product.getId()+"的商品已经下架了");
            }
            if (product.getStock()<cart.getQuantity()){
                return ServerResponse.createServerResponseByErrow("id为"+product.getId()+"的商品库存不足");
            }
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setCurrentUnitPrice(product.getPrice());//下单时候的金额
            orderItem.setProductId(product.getId());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setProductName(product.getName());
            orderItem.setTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),cart.getQuantity().doubleValue()));

            orderItemList.add(orderItem);
        }
        return ServerResponse.createServerResponseBySucess(null,orderItemList);
    }
}
