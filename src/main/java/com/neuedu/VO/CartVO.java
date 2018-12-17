package com.neuedu.VO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 返回到前端的购物车实体类CartVO
 */
public class CartVO implements Serializable {

    //购物信息集合
    private List<CartProductVO> cartProductVoList;
    //是否全选
    private boolean isallchecked;
    //总价格
    private BigDecimal carttotalprice;

    public List<CartProductVO> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVO> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public boolean isIsallchecked() {
        return isallchecked;
    }

    public void setIsallchecked(boolean isallchecked) {
        this.isallchecked = isallchecked;
    }

    public BigDecimal getCarttotalprice() {
        return carttotalprice;
    }

    public void setCarttotalprice(BigDecimal carttotalprice) {
        this.carttotalprice = carttotalprice;
    }
}
