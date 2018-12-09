package com.neuedu.service.imp;

import com.google.common.collect.Sets;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;


    /**
     * 获取品类子节点（平级）
     */
    @Override
    public ServerResponse get_category(Integer categoryId) {
        //step1：非空校验
        if (categoryId==null){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }
        //step2：根据categoryId查询类别
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category==null){
            return ServerResponse.createServerResponseByErrow("此类别不存在");
        }
        //step3：查询子类别
        List<Category> categoryList = categoryMapper.findChildCategory(categoryId);
        //step4：返回结果
        return ServerResponse.createServerResponseBySucess(null,categoryList);
    }
    /**
     * 增加节点
     */
    @Override
    public ServerResponse add_category(Integer parentId, String categoryName) {
        //step1：非空校验（parentId有默认值，可以不用进行非空判断）
         if (StringUtils.isBlank(categoryName)){
             return ServerResponse.createServerResponseBySucess("类别名称不能为空");
         }
        //step2：添加节点
        //insert的话，就要先new一个category对象
        Category category = new Category();
         category.setParentId(parentId);
         category.setName(categoryName);
         category.setStatus(1);
        int result_addcategoryid = categoryMapper.insert(category);
        //step3：返回结果
        if (result_addcategoryid>0){
            //添加成功
            return ServerResponse.createServerResponseBySucess("节点添加成功");
        }
        return ServerResponse.createServerResponseByErrow("节点添加失败");
    }
    /**
     * 修改节点
     */
    @Override
    public ServerResponse set_category_name(Integer categoryId, String categoryName) {

        //step1：非空校验
        if (categoryId==null || categoryId.equals("")){
           return ServerResponse.createServerResponseByErrow("类别id不能为空");
        }
        if (StringUtils.isBlank(categoryName)){
            return ServerResponse.createServerResponseByErrow("类别名称不能为空");
        }
        //step2：根据categoryId查询类别
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category==null){
            return ServerResponse.createServerResponseByErrow("要修改的类别不存在");
        }
        //step3：修改类别
         //值修改类别名称就可以了
        category.setName(categoryName);
        int result_update = categoryMapper.updateByPrimaryKey(category);
        //step4：返回结果
        if (result_update>0){
            return ServerResponse.createServerResponseBySucess("节点修改成功");
        }
        return ServerResponse.createServerResponseByErrow("节点修改失败");
    }
    /**
     * 获取当前分类id及递归子节点categoryId
     */
    @Override
    public ServerResponse get_deep_category(Integer categoryId) {

        //step1:参数的非空校验
        if (categoryId==null || categoryId.equals("")){
            return ServerResponse.createServerResponseByErrow("类别id不能为空");
        }
        //step2:查询
        Set<Category>categorySet = Sets.newHashSet();
        categorySet = findAllChildCategory(categorySet,categoryId);
        //要获取id，要遍历这个集合
        Set<Integer> integerSet = Sets.newHashSet();
        Iterator<Category> categoryIterator = categorySet.iterator();
        while (categoryIterator.hasNext()){
            Category category = categoryIterator.next();
            integerSet.add(category.getId());
        }

        return ServerResponse.createServerResponseBySucess(null,integerSet);
    }
    /**
     * 先定义一个递归的方法
     */
    private Set<Category>findAllChildCategory(Set<Category> categorySet,Integer categoryId){
        //先查找本节点
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category!=null){  //把当前类别添加到这个集合里
            categorySet.add(category);//set里面的集合不可重复，通过类别id判断是不是重复，要重写类别对象的equals方法，在重写equals方法前先重写hashcode方法
        }
        //查找categoryId下的子节点（平级）
        List<Category>categoryList = categoryMapper.findChildCategory(categoryId);
        if (categoryList!=null&&categoryList.size()>0){//遍历这个集合拿到这个每个子节点
            for (Category cate:categoryList) {//对集合的每个节点调用当前这个递归方法
                findAllChildCategory(categorySet,cate.getId());
            }
        }
        //递归的结束语句就是 categoryList==null&&categoryList.size()<=0
        return categorySet;
    }
}
