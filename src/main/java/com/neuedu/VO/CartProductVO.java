package com.neuedu.VO;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartProductVO implements Serializable {
    private Integer id;//"id": 1,
    private Integer userId;// "userId": 13,
    private Integer productId;//"productId": 1,
    private Integer quantity;//"quantity": 1,商品数量
    private String  productName;//"productName": "iphone7",
    private String  productSubtitle;// "productSubtitle": "双十一促销",
    private String  productMainImage;// "productMainImage": "mainimage.jpg",
    private BigDecimal productPrice;// "productPrice": 7199.22,
    private Integer productStatus;//   "productStatus": 1,
    private BigDecimal productTotalPrice;//   "productTotalPrice": 7199.22,
    private Integer  productStock;//  "productStock": 86,
    private Integer  productChecked;//  "productChecked": 1,
    private String   limitQuantity;// "limitQuantity": "LIMIT_NUM_SUCCESS",判断诱惑还是没货

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public void setProductMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }
}
