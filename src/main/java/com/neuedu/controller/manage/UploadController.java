package com.neuedu.controller.manage;

import com.neuedu.common.ServerResponse;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/manage/product/")
public class UploadController {

    @Autowired
    IProductService productService;

                  //浏览器的访问页面方式是通过get请求，此方法返回的是get
    @RequestMapping(value = "upload",method = RequestMethod.GET)
    public String upload(){

        return "upload";//逻辑视图   加载资源：前缀（"/WEB-INF/jsp/"）+逻辑视图+后缀（".jsp"）-->/WEB-INF/jsp/upload.jsp这个路径下去加载这个页面
                       //前缀（<property name="prefix" value="/WEB-INF/jsp/"/>）,后缀（<property name="suffix" value=".jsp"/>）

    //通过访问方式的不同来区分springMVC框架，调用对应的方法

    }
    //表单提交方式是post已经把访问方式限制为post
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody //返回的对象就是json格式   MultipartFile用这个格式接收图片，把流封装到这个实体类对象里面了
    public ServerResponse upload2(@RequestParam(value = "upload_file",required = false) MultipartFile file){
        //定义一个路径
        String path = "D:\\facetest\\ftpfile";
        return productService.upload(file,path);
    }

}
