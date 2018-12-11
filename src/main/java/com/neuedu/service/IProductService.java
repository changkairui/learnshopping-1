package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {

    /**
     * 新增OR更新产品
     */
    ServerResponse saveOrUpdate(Product product);

    /**
     * 产品的上下架
     * @param productId 商品id
     * @param status    商品状态
     * @return
     */
    ServerResponse set_sale_status(Integer productId,Integer status);
    /**
     * 产品详情
     */
    ServerResponse detail(Integer productId);
    /**
     * 产品列表
     */
    ServerResponse list(Integer pageNum,Integer pageSize);

    /**
     * 产品搜索
     * @param productId
     * @param productName
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse search(Integer productId,String productName,Integer pageNum,Integer pageSize);
    /**
     * 前台商品信息
     */
    ServerResponse detail_portal(Integer productId);
    /**
     * 图片上传
     */
    ServerResponse upload(MultipartFile file,String path);

    /**
     * 前台的商品搜索
     * @param categoryId
     * @param keyword 关键字
     * @param pageNum
     * @param pageSize
     * @param orderBy 排序字段
     * @return
     */
    ServerResponse list_portal(Integer categoryId,String keyword,Integer pageNum,Integer pageSize,String orderBy);

}
