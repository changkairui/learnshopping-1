package com.neuedu.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.neuedu.VO.ProductDetailVO;
import com.neuedu.VO.ProductListVO;
import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import com.neuedu.utils.DateUtils;
import com.neuedu.utils.PropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImp implements IProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ICategoryService categoryService;

    /**
     * 新增OR更新产品
     */
    @Override
    public ServerResponse saveOrUpdate(Product product) {
        //step1：参数非空校验
        if (product == null){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }
        //step2：设置商品主图 sub_images --> 1.jpg,2.jpg,3.png
        //                 取出第一张，它是一个字符串，
        String subImages = product.getSubImage();
        if (!StringUtils.isBlank(subImages)){
            String[] subImagesArray = subImages.split(",");
            if (subImagesArray.length>0){
                //设置商品主图
                product.setMainImage(subImagesArray[0]);
            }
        }
        //step3：商品save or update
        if (product.getId()==null){
            //添加
            int resultSave = productMapper.insert(product);
            if(resultSave>0){
                return ServerResponse.createServerResponseBySucess("商品添加成功");
            }
            return ServerResponse.createServerResponseByErrow("商品添加失败");
        }else {
            //更新
            int resultUpdate = productMapper.updateByPrimaryKey(product);
            if (resultUpdate>0){
                return ServerResponse.createServerResponseBySucess("商品修改成功");
            }
            return ServerResponse.createServerResponseByErrow("商品修改失败");

        }

    }
    /**
     * 产品的上下架
     */
    @Override
    public ServerResponse set_sale_status(Integer productId, Integer status) {
        //step1：参数非空校验
        if (productId==null){
            return ServerResponse.createServerResponseByErrow("商品id不能为空");
        }
        if (status==null){
            return ServerResponse.createServerResponseByErrow("商品状态参数不能为空");
        }
        //step2：更新商品的状态
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int resultUpdate = productMapper.updateProductKeySelective(product);
        //step3：返回结果
        if (resultUpdate>0){
            return ServerResponse.createServerResponseBySucess("商品状态修改成功");
        }
        return ServerResponse.createServerResponseByErrow("商品状态修改失败");
    }
    /**
     * 产品详情
     */
    @Override
    public ServerResponse detail(Integer productId) {
        //step1：参数校验
        if (productId==null){
            return ServerResponse.createServerResponseByErrow("商品id不能为空");
        }
        //step2：查询product
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createServerResponseByErrow("商品不存在");
        }
        //step3：product -->productDetailVO
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);
        //step4：返回结果
        return ServerResponse.createServerResponseBySucess(null,productDetailVO);
    }
    //product -->productDetailVO
    private ProductDetailVO assembleProductDetailVO(Product product){
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setImageHost(PropertiesUtils.readByKey("imageHost"));
        productDetailVO.setName(product.getName());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setId(product.getId());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setSubImages(product.getSubImage());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category!=null){
            productDetailVO.setParentCategoryId(category.getParentId());
        }else {
            //默认根节点
            productDetailVO.setParentCategoryId(0);
        }
        return productDetailVO;
    }

    /**
     * 产品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
        //分页插件的原理就是springAOP
        PageHelper.startPage(pageNum,pageSize);
        //分页要写到list之后aop就不起作用了
        //查询商品数据
        List<Product> productList = productMapper.selectAll();
        List<ProductListVO> productListVOList = Lists.newArrayList();
        if (productList!=null&&productList.size()>0){
            for (Product pro:productList) {
                ProductListVO productListVO = assembleProductListVO(pro);
                productListVOList.add(productListVO);
            }
        }
        PageInfo pageInfo = new PageInfo(productListVOList);

        return ServerResponse.createServerResponseBySucess(null,pageInfo);
    }
    private ProductListVO assembleProductListVO(Product product){
        ProductListVO productDetailVO = new ProductListVO();
        productDetailVO.setId(product.getId());
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setName(product.getName());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setSubtitle(product.getSubtitle());
        return productDetailVO;
    }
    /**
     * 产品搜索
     */
    @Override
    public ServerResponse search(Integer productId, String productName, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        if (!StringUtils.isBlank(productName)){
            productName="%"+productName+"%";
        }
        List<Product>productList = productMapper.findProductIdByProductName(productId,productName);
        List<ProductListVO>productListVOList=Lists.newArrayList();
        if (productList!=null&&productList.size()>0){
            for (Product pro:productList) {
                ProductListVO productListVO = assembleProductListVO(pro);
                productListVOList.add(productListVO);
            }
        }

        PageInfo pageInfo = new PageInfo(productListVOList);
        return ServerResponse.createServerResponseBySucess(null,pageInfo);
    }
    /**
     * 前台商品详情
     */
    @Override
    public ServerResponse detail_portal(Integer productId) {
        //step1:参数校验
        if (productId==null){
            return ServerResponse.createServerResponseByErrow("商品id不能为空");
        }
        //step2：查询product
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createServerResponseByErrow("商品不存在或者已下架");
        }
        //step3:校验商品状态
        if (product.getStatus()!=Const.ProductStatusEnum.PRODUCT_ONLINE.getCode()){
            return ServerResponse.createServerResponseByErrow("商品已下架或者删除");

        }

        //step4:获取productDetailVO
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);
        //step5:返回结果

        return ServerResponse.createServerResponseBySucess(null,productDetailVO);
    }
    /**
     * 图片处理
     * 接收到图片之后先把图片保存到服务器里
     */
    @Override
    public ServerResponse upload(MultipartFile file, String path) {
        if (file==null){
            return ServerResponse.createServerResponseByErrow("图片不存在");
        }
        //图片存在要先给图片修改名字，因为有可能不同的人上传的图片是相同的，所以拿到图片之后要给他生成一个唯一的名字
        //step1：获取图片名字
           //获取图片原始名称
        String originalFilename = file.getOriginalFilename();
            //获取图片扩展名
        String exName = originalFilename.substring(originalFilename.lastIndexOf(".")); //.jpg
            //为图片生成新的唯一的名字
        String newFileName = UUID.randomUUID().toString()+exName;

        File pathFile = new File(path);
        if (!pathFile.exists()){//判断这个路径是否存在
            //设置可写
            pathFile.setWritable(true);
            //生成这个目录
            pathFile.mkdir();
        }
         //把新文件写到path路径下
        File file1 = new File(path,newFileName);
        try {
            file.transferTo(file1);//把file写到了path这个路径下的newFileName这个文件下了
            Map<String,String> map = Maps.newHashMap();
            map.put("uri",newFileName);//???????????????????????????????????
            map.put("url",PropertiesUtils.readByKey("imageHost")+"/"+newFileName);
            return ServerResponse.createServerResponseBySucess(null,map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        //最后在前端要返回一个json数据
    }

    /**
     * 前台的商品搜索
     * @param categoryId
     * @param keyword 关键字
     * @param pageNum
     * @param pageSize
     * @param orderBy 排序字段
     * @return
     */
    @Override
    public ServerResponse list_portal(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {

        //step1：参数校验 categoryId和keyword不能同时为空
        if (categoryId==null&&(keyword==null || keyword.equals(""))){
            return ServerResponse.createServerResponseByErrow("参数错误");
        }
        //step2：categoryId
        Set<Integer> integerSet = Sets.newHashSet();
        if (categoryId!=null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category==null&&StringUtils.isBlank(keyword)){
                //说明没有商品数据，此时分页也要有输出，但是list里面没有数据
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVO>productListVOList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVOList);
                return ServerResponse.createServerResponseBySucess(null,pageInfo);
            }
            //不为空就可以查询这个id下的后代子类，用递归
            ServerResponse serverResponse = categoryService.get_deep_category(categoryId);
            if (serverResponse.isSuccess()){
                integerSet = (Set<Integer>)serverResponse.getData();
                //查询出了这个类别下的所有的子类
            }
        }
        //step3：keyword
        if (!StringUtils.isBlank(keyword)){
            keyword = "%"+keyword+"%";//模糊查询
        }
        if (orderBy.equals("")){
            PageHelper.startPage(pageNum,pageSize);
        }else{
            String[] orderByArr = orderBy.split("_");
            if (orderByArr.length>1){
                PageHelper.startPage(pageNum,pageSize,orderByArr[0]+" "+orderByArr[1]);//后面一个参数第一个字段是按什么字段排序，第二个是升序还是降序排序
            }else {
                PageHelper.startPage(pageNum,pageSize);
            }
        }
        //step4：List<Product>-->List<ProcustListVO>
        List<Product>productList = productMapper.searchProduct(integerSet,keyword);
        List<ProductListVO> productListVOList = Lists.newArrayList();
        if (productList!=null&&productList.size()>0){
            for (Product product:productList){
                ProductListVO productListVO =  assembleProductListVO(product);
                productListVOList.add(productListVO);

            }
        }
        //step5：分页
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(productListVOList);
        //step6：返回
        return ServerResponse.createServerResponseBySucess(null,pageInfo);
    }
}
