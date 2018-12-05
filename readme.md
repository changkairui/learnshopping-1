 
 ## ============== 20181203 ===============
 # Git笔记
 ## git 配置
 #### 1.配置用户名(提交时会引用)
 ###### git config --global user.name "用户名"
 ##### 2.配置邮箱
 ###### git config --global user.email "邮箱"
 ##### 3.编码配置
 ###### 避免git gui中的中文乱码
 ##### git config --global gui.encoding utf-8
 ###### 避免git status显示的中文文件名乱码
 ##### git config --global core.quotepath off
 ##### 4.其他
 ##### git config --global core.ignorecase false
 ## git ssh key pair 配置
 ##### 1.在git bash命令行窗口中输入：
 ###### ssh-keygen -t rsa -C "邮箱"
 ##### 2.然后一路回车，不要输入任何密码之类，生成
 ###### ssh key pair 
 ##### 3,在用户目录下生成.ssh文件夹，找到公钥和私钥
 ###### id_rsa id_rsa.pub
 ##### 4.将公钥的内容复制
 ##### 5.进入github网站，将公钥添加进去
 ## git验证
 ##### 6.执行git --version，出现版本信息，安装成功。
 ## git工作原理
 ##### 工作区 git add--->暂存区 git commit--->本地仓库
 ##### 可以将多个文件多次提交到暂存区，在暂存区通过git commit（清空暂存区）一次性提交到本地仓库,然后再提交到远程仓库GitHub
 ##### git init:先在本地初始化一个仓库
 ##### git add README.md：将我的工作区里的文件提交到暂存区
 ##### git commit -m "本次提交的一个描述" :将暂存区的内容提交到本地仓库
 ##### git remote add origin git@github.com:lll865457981/learnshopping.git:将本地仓库和远程仓库做一个关联
 ##### git push -u origin master：将本地仓库里的内容提交到远程仓库
 
 ## git常用命令
 ##### git init：创建本地仓库；在工作区
 ###### 文件前缀如果是点是隐藏文件
 ##### git add：添加到暂存区
 ##### git commit -m（必须要加这个参数） "本次提交的一个描述" ：提交到本地仓库
 ###### 注意：不能从工作区直接git commit 到本地仓库
 ##### git status:查看工作区的状态
 ##### On branch master：在master分支上进行管理；默认在master主分支上进行管理
 ###### 在创建远程仓库之前先进行ssh的配置
 ##### git push -u origin master:提交到远程仓库
 ##### git log：查看提交committed
 ##### git reset --hard committid：日记的唯一标识，提交版本回退
 ##### git branch：查看分支（前面待着*，表示当前指向的分支）
 ##### git branch 新分支名：创建分支
 ##### git checkout -b dev：创建并切换到dev分支
 ##### 切换分支：git checkout 分支名
 ##### git pull：拉取代码
 ##### git push -u(修改的时候可以省略) origin master:向master分支提交，默认提交master分支
 ##### git merge 分支名：分支合并（切换到master分支后对其他分支进行合并）
 ##### git remote add origin "远程仓库地址"：关联远程仓库
 ##### git push -u origin master:第一次向远程仓库推送
 ##### git push origin master:以后提交到远程仓库
 ###### 出现上传不了的时候，是因为远程仓库的东西没有拉下来，可在提交命令的后面加上“-f”,进行强制提交
 #### 远程分支合并dev
 ##### git checkout dev   切换到要合并的分支
 ##### git pull origin dev  拉取分支的数据
 ##### git checkout master  切换到master分支
 ##### git merge dev        将dev分支合并到master分支
 ##### git push origin master   再进行文件的远程提交
 ## 企业项目开发模式
 ##### 项目采用：
   ##### 分支开发，主干发布
   ##### 将本地分支合并到远程分支：git push origin HEAD -u
 
 ### .gitignore文件
 ##### 作用：告诉git哪些文件不需要添加到版本管理中
 #### 忽略规则
 ###### #此为注释 - 将被git忽略
 ##### *.a ：忽略所有.a结尾的文件
 ##### !lib.a ： 但 lib.a 除外
 ##### /TODO ：仅仅忽略项目根目录下的 TODO 文件，不包括 subdir/TODO
 ##### build/ ： 忽略 build/ 目录下的所有文件
 ##### doc/*.txt ： 会忽略 doc/notes.txt 但不包括 doc/server/arch.txt
 
 ### 删除远程仓库的文件
 ##### 1.git pull origin master
 ##### 2.git rm -r --cached "文件名"
 ##### 3.git comment -m "信息"
 ##### 4.git push -u origin master
 
 
 # 电商项目-需求分析
 
 ### 核心-购买
 #### 一、用户模块
 ##### 登录
 ##### 注册
 ##### 忘记密码
 ##### 获取用户信息
 ##### 修改密码
 ##### 登出
 ### 二、商品模块
 ##### 后台
 ##### 添加商品
 ##### 修改商品
 ##### 删除商品
 ##### 商品上下架
 ##### 查看商品
 ##### 前台（门户）
 ##### 搜索商品
 ##### 查看商品详情
 ### 三、类别模块
 ##### 添加类别
 ##### 修改类别
 ##### 删除类别
 ##### 查看类别
 ##### 查看子类
 ##### 查看后代类别
 ### 四、购物车模块
 ##### 添加到购物车
 ##### 改购物车中某个商品的数量
 ##### 删除购物车商品
 ##### 全选/取消全选 
 ##### 单选/取消单选 
 ##### 看购物车中商品数量
 ### 五、地址模块
 ##### 添加地址
 ##### 修改地址
 ##### 删除地址
 ##### 查看地址
 ### 六、订单模块
 ##### 前台
 ##### 下订单
 ##### 订单列表
 ##### 取消订单
 ##### 订单详情
 ##### 后台
 ##### 订单列表
 ##### 订单详情
 ##### 发货
 ### 七、支付模块
 ##### 支付宝支付
 ##### 支付
 ##### 支付回调
 ##### 查看支付状态
 ### 八、线上部署
 ##### 阿里云部署
  
 ## =========== 20181204 ============
 
 ### 数据库设计
 #### 创建数据库
 ```
 create database illearnshopping;
 use illearnshopping
 ```
 #### 用户表
 ```
 create table usertable(
 `id`             int(11)          not null     auto_increment    comment '用户id',
 `username`       varchar(50)      not null                       comment '用户名',
 `password`       varchar(50)      not null                       comment '密码',
 `email`          varchar(50)      not null                       comment '邮箱',
 `phone`          varchar(11)      not null                       comment '联系方式',
 `question`       varchar(100)     not null                       comment '密保问题',
 `answer`         varchar(200)     not null                       comment '答案',
 `role`           int(4)           not null      default 0        comment '用户角色',
 `create_time`    datetime                                        comment '创建时间',
 `update_time`    datetime                                        comment '修改时间',
  primary key (`id`),
  unique key `user_name_index`(`username`) USING BTREE
  )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
  ```
 #### 类别表
 ```
 create table category(
  `id`             int(11)          not null     auto_increment    comment '类别id', 
 `parent_id`       int(11)          not null     default 0         comment '父类id',
 `name`            varchar(50)      not null                       comment '类别名',
 `status`            int(4)                      default 1         comment '类别状态 1：正常，2：废弃',             
 `create_time`     datetime         comment '创建时间',
 `update_time`     datetime         comment '修改时间',
 primary key(`id`)
 )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
 查询电子产品的商品--递归
 ```
 #### 商品表
 ```
 create table product(
 `id`             int(11)          not null     auto_increment    comment '类别id', 
 `category_id`    int(11)          not null     comment '商品所属的类别id,值引用类别表id',
 `name`           varchar(100)     not null     comment '商品名称',
 `detail`         text             comment '商品详情',
 `subtitle`       varchar(200)     comment '商品副标题',
 `main_image(1.png)`           varchar(100)      comment '商品主图',   
 `sub_image(1.png,2.png)`      varchar(200)      comment '商品子图',
 `price`          decimal(20,2)    not null      comment '商品价格，总共20位，小数2位，正数18位',    
 `stock`          int(11)          comment '商品库存',
 `status`         int(4)           default 1     comment '商品状态 1：在售，2：下架，3：删除',
 `create_time`     datetime        comment '创建时间',
 `update_time`     datetime        comment '修改时间',
  primary key(`id`)
  )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
  ```
 #### 购物车表
 ```
 create table cart(
 `id`               int(11)          not null     auto_increment    comment '类别id', 
 `user_id`          int(11)          not null     comment '用户id',
 `product_id`       int(11)          not null     comment '商品id',
 `quantity`         int(11)          not null     comment '购买数量',
 `checked`          int(4)           default 1    comment '1:选中，0：未选中',
 `create_time`      datetime        comment '创建时间',
 `update_time`      datetime        comment '修改时间',
  primary key(`id`),
  经常用user_id来进行查询，所以给他加一个索引,加快查询速度
  key `user_id_index`(`user_id`) USING BTREE
  )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
  ```
 #### 订单表
 ```
 create table _order(
  `id`           int(11)    not null  auto_increment comment '订单id,主键',
  `order_no`     bigint(20) not null  comment '订单编号',
  `user_id`      int(11)  not null  comment '用户id',
  `payment`      decimal(20,2) not null comment '付款总金额，单位元，保留两位小数',
  `payment_type` int(4) not null default 1 comment '支付方式 1:线上支付 ',
  `status`       int(10) not null  comment '订单状态 0-已取消  10-未付款 20-已付款 30-已发货 40-已完成  50-已关闭',   
  `shipping_id`  int(11) not null comment '收货地址id',
  `postage`      int(10) not null default 0 comment '运费', 
  `payment_time` datetime  default null  comment '已付款时间',
  `send_time`      datetime  default null  comment '已发货时间',
  `close_time`     datetime  default null  comment '已关闭时间',
  `end_time`      datetime  default null  comment '已结束时间',
  `create_time`    datetime  default null  comment '已创建时间',
  `update_time`    datetime  default null  comment '更新时间',
   PRIMARY KEY(`id`),
   UNIQUE KEY `order_no_index`(`order_no`) USING BTREE
 )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
 ```
 #### 订单明细表
 ```
 create table order_item(
  `id`           int(11)    not null  auto_increment comment '订单明细id,主键',
  `order_no`     bigint(20) not null  comment '订单编号',
  `user_id`      int(11)  not null  comment '用户id',
  `product_id`   int(11)  not null comment '商品id',
  `product_name` varchar(100)  not null comment '商品名称',
  `product_image`  varchar(100)  comment '商品主图', 
  `current_unit_price` decimal(20,2) not null comment '下单时商品的价格，元为单位，保留两位小数',
  `quantity`     int(10)  not null comment '商品的购买数量',
  `total_price`  decimal(20,2) not null comment '商品的总价格，元为单位，保留两位小数',
  `create_time`    datetime  default null  comment '已创建时间',
  `update_time`    datetime  default null  comment '更新时间',
   PRIMARY KEY(`id`),
   KEY `order_no_index`(`order_no`) USING BTREE,
   KEY `order_no_user_id_index`(`order_no`,`user_id`) USING BTREE
 )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
 ```
 #### 支付表
 ```
 create table payinfo(
  `id`           int(11)    not null  auto_increment comment '主键',
  `order_no`     bigint(20) not null  comment '订单编号',
  `user_id`      int(11)  not null  comment '用户id',
  `pay_platform` int(4)  not null default 1  comment '1:支付宝 2:微信', 
  `platform_status`  varchar(50) comment '支付状态', 
  `platform_number`  varchar(100) comment '流水号',
  `create_time`    datetime  default null  comment '已创建时间',
  `update_time`    datetime  default null  comment '更新时间',
   PRIMARY KEY(`id`)
  )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
  ```
 #### 地址表 
 ```
 create table shipping(
 `id`       int(11)      not null  auto_increment,
 `user_id`       int(11)      not  null  ,
 `receiver_name`       varchar(20)      default   null  COMMENT '收货姓名' ,
 `receiver_phone`       varchar(20)      default   null  COMMENT '收货固定电话' ,
 `receiver_mobile`       varchar(20)      default   null  COMMENT '收货移动电话' ,
 `receiver_province`       varchar(20)      default   null  COMMENT '省份' ,
 `receiver_city`       varchar(20)      default   null  COMMENT '城市' ,
 `receiver_district`       varchar(20)      default   null  COMMENT '区/县' ,
 `receiver_address`       varchar(200)      default   null  COMMENT '详细地址' ,
 `receiver_zip`       varchar(6)      default   null  COMMENT '邮编' ,
 `create_time`       datetime      not null   comment '创建时间',
 `update_time`       datetime      not null   comment '最后一次更新时间',
  PRIMARY KEY(`id`)
 )ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
 ```
 
 ### 项目架构--四层架构（上层依赖下层）
 ##### 视图层
 ##### 表单进行数据的提交，提交到servlet或者是springMVC的controller层
 #### 控制层controller
 ##### 接收视图层传的数据，同时负责调用业务逻辑层，将业务逻辑层返回的值通过控制层返回到视图层
 #### 业务逻辑层service
 ##### 业务逻辑层是负责具体的业务逻辑的，业务逻辑层调用DAO层进行数据的处理
 ###### 接口和实现类
 #### DAO层
 ##### 主要是和数据层进行交互的，对数据库的增删改查
 
 #### common放常量和枚举之类的
 #### 工具包，读写之类的，时间等，封装的一些方法
 
 ### Mybatis-generator插件
 ##### 它可以一键生成dao接口，实体类，映射文件，sql文件
 
 ##### properties文件中的这些值，不加前缀也可以，但是username默认加载操作系统的用户名，防止插件默认记载系统名，要加前缀
 #### 步骤
 ##### 配置pom.xml，导入各种依赖，引入MySQL包和mybatis-generator依赖，引入org.mybatis.generator
 ##### 创建db.properties文件，输入名字密码网址驱动
        jdbc.username=root
        jdbc.password=345513
        jdbc.driver=com.mysql.jdbc.Driver
        jdbc.url=jdbc:mysql://localhost:3306/ilearnshopping 
 ##### 在generatorConfig.xml 中配置db.properties
 ##### 配置MySQL依赖包，输入jar包具体路径
 ##### 配置当前文件下的各种数据。配置数据库用${}
 ##### 配置实体类，SQL文件，Dao接口
     com.neuedu.pojo         src/main/java
     com.neuedu.mapper       src/main/resources
     com.neuedu.dao          src/main/java
 ##### 配数据表
 ##### 最后 右边栏的Maven Projects 里 pluging 里 mybatis-generator 里 mybatis-generator:generate 双击生成实体类、dao、mapper映射xml文件
 
 ### 搭建ssm框架
 ##### springMVC框架其实就是管理controller，所以扫描注解的时候只扫描controller包中的就可以了
 
 ##### 导入依赖
 ##### 统一版本号 <spring.version>4.2.0.RELEASE</spring.version>
 ##### 导入spring.xml springmvc.xml mybatis-config.xml文件
 #### spring.xml
 ##### 开启注解扫包。核对更改数据源的名字
    configLocation 全局配置文件 classpath 类路径
    mapperLocations 映射文件 用/分割地址 *mapper.xml 任意mapper.xml文件
 ##### 配置mybatis Dao接口的代理实现类，动态生成代理实现类，很重要
 ##### mybatis-config.xml不用修改
 ##### springmvc.xml
 ###### 开启注解，扫描包com.neuedu.controller ，也可以com.neuedu
 ##### 配置视图解析器、文件上传、拦截器（一期项目不用）
 ##### web.xml更换老师的
 ##### 加载spring配置文件 contextConfigLocation
 ##### 加载监听器
 ##### 加载DispacherServlet
 ##### /为缺省路径 访问 /login.do 有servlet处理login.do就交给对应的servlet处理。没有的话就交给/处理，就是dispacherservlet处理
 ##### 创建测试类 Testcontroller
 ##### @RestController 注解，往前端返回的数据是json格式
 ##### @RequestMapping （value="/login.do"） 映射的网址，也就可以加在类上，多层级访问
 ##### 配置tomcat 启动输入网址http://localhost:8080/login.do ,出现json数据 完成测试