package com.neuedu.service.imp;

import com.google.common.collect.Lists;
import com.neuedu.VO.CartProductVO;
import com.neuedu.VO.CartVO;
import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CartMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICartService;
import com.neuedu.utils.BigDecimalUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ICartServiceImpl implements ICartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    @Override
    public ServerResponse add(Integer userId,Integer productId, Integer count) {
        //step1:参数非空校验
        if(productId==null || count==null){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }
        //step2：根据productId userId 查询购物车信息
       Cart cart= cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart==null){
            //添加
            Cart cart1=new Cart();
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setQuantity(count);
            //一个默认选中状态
            cart1.setChecked(ResponseCode.CartCheckedEnum.PRODUCT_CHECKED.getCode());
            cartMapper.insert(cart1);
        }else {
            //更新
            Cart cart1=new Cart();
            cart1.setId(cart.getId());
            cart1.setProductId(productId);
            cart1.setUserId(userId);
            cart1.setQuantity(count);
            cart1.setChecked(cart.getChecked());
            cartMapper.updateByPrimaryKey(cart1);
        }
        //step3：
        CartVO cartVO=getCartVOLimit(userId);
        return ServerResponse.createServerResponseBySucess(null,cartVO);
    }

    //获取cartVO  往前端返回的高复用模型
    private CartVO getCartVOLimit(Integer userId) {
        CartVO cartVO = new CartVO();
        //step1:根据userId 查询购物信息--->List<Cart>
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        //step2:List<Cart>-->List<CartProductVO>
        List<CartProductVO> cartProductVOList = Lists.newArrayList();
        //购物车总价格 执行step3 先定义,默认为0
        BigDecimal carttotalprice = new BigDecimal("0");
        if (cartList != null && cartList.size() > 0) {
            for (Cart c : cartList) {
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(c.getId());
                cartProductVO.setQuantity(c.getQuantity());
                cartProductVO.setUserId(userId);
                cartProductVO.setProductChecked(c.getChecked());
                //通过产品id查询商品
                Product product = productMapper.selectByPrimaryKey(c.getProductId());
                if (product != null) {
                    cartProductVO.setProductId(c.getProductId());
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductStock(product.getStock());
                    cartProductVO.setProductSubtitle(product.getSubtitle());//标题
                    int stock = product.getStock();
                    int limitProductCount = 0;
                    if (stock > c.getQuantity()) {
                        limitProductCount = c.getQuantity();
                        //字符串型
                        cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");
                    } else {//商品库存不足 购买的最大数量是库存数
                        limitProductCount = stock;
                        //更新一下购物车中商品的数量
                        Cart cart = new Cart();
                        cart.setId(cart.getId());
                        cart.setQuantity(stock);
                        cart.setProductId(cart.getProductId());
                        cart.setChecked(cart.getChecked());
                        cart.setUserId(userId);
                        cartMapper.updateByPrimaryKey(cart);
                        cartProductVO.setLimitQuantity("LIMIT_NUM_FAIL");
                    }
                    cartProductVO.setQuantity(limitProductCount);
                    //getQuantity  Integer 类型转化Double类型用Double.valueOf  总价=单价*数量

                    if (cartProductVO.getProductChecked()==ResponseCode.CartCheckedEnum.PRODUCT_CHECKED.getCode()) {
                        cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(), Double.valueOf(cartProductVO.getQuantity())));

                    }
                    }
                    carttotalprice = BigDecimalUtils.add(carttotalprice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
                    cartProductVOList.add(cartProductVO);
                }
            }
            //购物车信息
            cartVO.setCartProductVoList(cartProductVOList);
            //step3:计算总价格购物车
            cartVO.setCarttotalprice(carttotalprice);
            //step4:判断购物车是否全选
            int count = cartMapper.isCheckedAll(userId);
            if (count > 0) {
                cartVO.setIsallchecked(false);
            } else {
                cartVO.setIsallchecked(true);
            }

            //step5:返回结果
            return cartVO;
        }

    /**
     * 购物车List列表
     */
    @Override
    public ServerResponse list(Integer userId) {

        CartVO cartVO=getCartVOLimit(userId);
        return ServerResponse.createServerResponseBySucess(null,cartVO);
    }
    /**
     * 更新购物车某个产品数量
     */
    @Override
    public ServerResponse update(Integer userId, Integer productId, Integer count) {
        //step1：参数的校验
        if(productId==null || count==null){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }
        //step2：查询购物车中商品
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart!=null){
            //step3：更新数量
            cart.setQuantity(count+cart.getQuantity());
            cartMapper.updateByPrimaryKey(cart);
        }

        //step4：返回结果
        return ServerResponse.createServerResponseBySucess(null,getCartVOLimit(userId));
    }
    /**
     * 移除购物车某个产品
     */
    @Override
    public ServerResponse delete_product(Integer userId, String productIds) {
        //step1：参数的非空校验
        if (StringUtils.isBlank(productIds)){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }
        //step2：productIds --> List<Integer>
        List<Integer>productIdList = Lists.newArrayList();
        String [] productIdArr = productIds.split(",");
        if (productIdArr!=null&&productIdArr.length>0){
            for (String productStr:productIdArr)  {
              Integer productId = Integer.parseInt(productStr);
              productIdList.add(productId);

            }
        }
        //step3：调用dao
        cartMapper.deleteByUserAndProductIds(userId,productIdList);
        //step4：返回结果
        return ServerResponse.createServerResponseBySucess(null,getCartVOLimit(userId));
    }
    /**
     * 购物车取消选中某个产品
     * @param userId
     * @param productId
     * @return
     */
    @Override
    public ServerResponse select(Integer userId, Integer productId,Integer check) {

        //step1：非空校验
        /*if (productId==null){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }*/
        //step2：dao接口
        cartMapper.selectOrUnselectProduct(userId,productId,check);
        //step3：返回结果
        return ServerResponse.createServerResponseBySucess(null,getCartVOLimit(userId));
    }
    /**
     * 统计用户购物车中的数量
     *
     * @param userId
     * @return
     */
    @Override
    public ServerResponse get_cart_product_count(Integer userId) {
        int quantity = cartMapper.get_cart_product_count(userId);
        return ServerResponse.createServerResponseBySucess(null,quantity);
    }
}
