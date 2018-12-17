package com.neuedu.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IAddressService;
import com.neuedu.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class AddressServiceImp implements IAddressService {
    /**
     * 添加地址
     */
    @Autowired
    ShippingMapper shippingMapper;
    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        //step1：参数校验
        if (shipping==null){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }
        //step2：添加
        shipping.setUserId(userId);
        shippingMapper.insert(shipping);
        //step3：返回结果
        Map<String,Integer> map = Maps.newHashMap();
        map.put("shippingId",shipping.getId());



        return ServerResponse.createServerResponseBySucess(null,map);
    }
    /**
     * 删除地址
     */
    @Override
    public ServerResponse del(Integer userId, Integer shippingId) {
        //step1：参数非空校验
        if (shippingId==null){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }
        //step2：删除
        int result_delete = shippingMapper.deleteByUserIdAndShippingId(userId, shippingId);
        //step3：返回结果
        if (result_delete>0){
            return ServerResponse.createServerResponseBySucess("删除成功");
        }
        return ServerResponse.createServerResponseByErrow("删除失败");
    }

    /**
     * 更新地址
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse update(Shipping shipping) {

        //step1:非空判断
        if (shipping==null){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }
        //step2:更新

        int result_update = shippingMapper.updateBySelectiveKey(shipping);
        //step2:返回结果
        if (result_update>0){
            return ServerResponse.createServerResponseBySucess("更新成功");
        }
        return ServerResponse.createServerResponseByErrow("更新失败");
    }

    /**
     * 选中查看具体地址
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse select(Integer userId, Integer shippingId) {

        //step1:非空判断
        if (shippingId==null){
            return ServerResponse.createServerResponseByErrow("参数不能为空");
        }
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        return ServerResponse.createServerResponseBySucess(null,shipping);

    }

    /**
     * 地址列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse list(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping>shippingList = shippingMapper.selectAll();
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createServerResponseBySucess(null,pageInfo);
    }
}
