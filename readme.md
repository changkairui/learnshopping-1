 
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
  `user_id`      int(11)  not null  comment '用户id',(冗余字段：用的频率很高，但是通过订单编号也能查到，但是需要用它做查询，又必须存在，增加查询速率)
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
        jdbc.password=ljll617.
        jdbc.driver=com.mysql.jdbc.Driver
        jdbc.url=jdbc:mysql://localhost:3306/illearnshopping 
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
     1.pom.xml
     2.添加配置文件
       spring配置文件
       springMVC配置文件
       mybatis配置文件
       web.xml
     3.使用框架  
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
 
 
 ## ===============20181205===============
 ##### 集成druid
    将c3p0的数据库连接池换成druid的数据库连接池
    将之前spring.xml文件中的数据源换成druid
 ##### 测试四层架构
     控制层依赖service层，service层依赖dao层
     1.创建service类接口，接口特性：方法的定义
     2.创建service实体类
        userInfoMapper本身是没有值，直接用报空指针异常
        bean交给springIOC里面了，从容器获取bean，用@Autowired，他是通过类型从容器里找，前提是实现类的实体类bean也得交给容器，service层交给容器管理，用@Service（业务逻辑层的bean）
        mybatis逆向工程生成了dao接口
        用service调用dao层
     3.controller调用service层，注入service接口，调用接口的方法
        1> @RequestParam(value = "username",required = true,defaultValue = "zhang"
        (怎么从前端获取参数，加@RequstParam(value = "username"),从前台传过来的参数username取到值，赋值给后台的username，获取到值后就可以给对象赋值了)
        (@RequestParam(value = )里面的value值要与页面的key值保持一致，与后面形参的值不一样也可以，但如果后面形参的值，与value值相同则可以省略@RequestParam,;)
        (required = true,表示这个值是必须要传递的，参数可传可不传的话就是false)
        (defaultValue = "zhang" 默认值，如果没有赋这个值，直接用默认值)
           public int login(String username, String password, String email, String phone, String question, String answer){
                   UserInfo userInfo = new UserInfo();
                   userInfo.setUsername(username);
                   userInfo.setPassword(password);
                   userInfo.setEmail(email);
                   userInfo.setPhone(phone);
                   userInfo.setQuestion(question);
                   userInfo.setAnswer(answer);
                   userInfo.setRole(1);
                   userInfo.setCreateTime(new Date());
                   userInfo.setUpdateTime(new Date());
           
                   int count = userService.register(userInfo);
                   return count;
               }
        2>用springMVC对象绑定,直接用把参数传给对象，直接把对象插入到方法里面，前台传的参数多的话，用对象传比较，例，用户注册，添加地址，登录等
           public int login(UserInfo userInfo){
            int count = userService.register(userInfo);
                   return count;
               }
 ### 用户模块
 #### 项目中的问题
     横向越权，纵向越权安全漏洞
     横向越权：攻击者尝试访问与他拥有相同权限的用户的资源
     纵向越权：低级别攻击者尝试访问高级别用户的资源
     MD5明文加密
     guava缓存的使用
     高服用服务对象的设计思想及抽象封装
     

 #### 封装返回前端的高服用对象
      class ServerResponse<T>{
        int status;//接口返回状态码
        T data;//封装了接口的返回数据
        String msg;//封装错误信息
      }
       服务端响应前端的高复用对象
       json返回类型{（int）status,data,msg}
       成功返回{status,data} 失败返回{status,msg}
       data的返回类型可以是字符串，数组，对象，数字，所以他的返回类型不确定，就用泛型
       
       @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
       在转json字符串的时候把空字段过滤掉
       当对这个对象ServerResponse进行转成一个字符的时候，那些非空字段是不会进行转化的
       
       @JsonIgnore
        ServerResponse转json的时候把success这个字段忽略掉
  ### 业务逻辑      
  #### 登录
       step1：参数的非空校验
         <dependency>
             <groupId>commons-lang</groupId>
             <artifactId>commons-lang</artifactId>
             <version>2.6</version>
           </dependency>
           这个依赖提供了一些常用的校验方法
           StringUtils.isblank();
           public static boolean isBlank(String str) {
                   int strLen;
                   if (str != null && (strLen = str.length()) != 0) {
                       for(int i = 0; i < strLen; ++i) {
                           if (!Character.isWhitespace(str.charAt(i))) {
                               return false;
                           }
                       }
           
                       return true;
                   } else {
                       return true;
                   }
               }
               StringUtils.isEmpty();
               public static boolean isEmpty(String str) {
                       return str == null || str.length() == 0;
                   }
                   当判断如果是一个“ ”空的字符串带空格的时候，StringUtils.isEmpty();这个方法不能判断
       step2：检查username是否存在
       
       step3：根据用户名和密码查询
            多个参数的时候参数类型parameterType为map
       step4：处理结果并返回  
   
   
 #### 注册
    step1：参数的非空校验
    step2：校验用户名是否存在
    step3：校验邮箱是否存在
    step4：调用dao接口插入用户信息
    step5：返回数据
    时间直接用mysql里面的方法now(),前端在进行注册的时间直接用时间函数就好,不用再传时间了
    枚举：一个变量它的值时有限的，就可以定义为枚举，开关，姓名
    密码密文应该写到注册接口，因为注册接口是往数据库里写数据
    
    数据库中拿到的是经过注册后得密文密码，而我们要用自己注册的明文密码进行登录，e而这时登陆的明文密码和对应的数据库中的密文密码肯定不一样，所以要对登录时的明文密码进行加密
       购物车只有用户登录时候才能用，当要用购物车的时候就需要判断用户是否进行了登录，登录就可以直接用，没登录对他进行提醒，登录后才能使用
       登录后信息保存在session当中
       
       检查用户名是否有效
       在注册时，页面会立刻有个反馈做时时的提示防止有恶意的调用接口，用ajax 异步加载调用接口返回数据
 #### 忘记密码之修改密码

    step1：校验username--->查询找回密码问题
    step2：前端，提交问题答案
    step3：校验答案-->修改密码 
    
    修改密码的时候要考虑到一个越权问题
      横向越权：权限都是一样的，a用户去修改其他用户  
      纵向越权：低级别用户修改高级别用户的权限
      解决：提交答案成功的时候，服务端会给客户端返回一个值（数据），这个数据在客户端服务端都分别保存，当用户去重置密码的时候，用户端必须带上这个数据，只有拿到数据服务端校验合法了才能修改
           所以服务端要给客户端传一个token,服务端客户端都分别保存，然后两个进行校验
           
    @JsonSerialize:json是个键值对往页面传对象的时候是通过，扫描类下的get方法来取值的
           
    UUID生成的是一个唯一的随机生成一个字符串，每次生成都是唯一的
  #### 根据用户名查询密保问题
      step1：参数非空校验
      step2：校验username是否存在
      step3：根据username查询密保问题
      step4：返回结果
  
  ##### 提交问题答案
      step1：参数非空校验
      step2：校验答案：根据username,question,answer查询，看看有没有这条记录
      step3：服务端生成一个token保存并将token返回给客户端
         String user_Token=UUID.randomUUID().toString();
         UUID每次生成的字符串是唯一的，不会重复的
         guava cache
         TokenCache.put(username,user_Token);
         缓存里用key或取，key要保证他的唯一性，key就是用户，key直接用value就可以了
         这样就把token放到服务端的缓存里面了，同时要将token返回到客户端
      step4：返回结果
  ##### 修改密码
      step1：参数的非空校验
      step2：校验token
      step3：更新密码
      step4：返回结果
  ### 登录状态下修改密码
      step1：参数的非空校验
      step2：校验旧密码是否正确,根据用户名和旧密码查询这个用户
      step3：修改密码
      step4：返回结果
      在控制层中要先判断是否登录
      
  ### 类别模块    
  #### 1：功能介绍
       获取品类子节点（平级）
       增加节点
       修改节点
       获取当前分类id及递归子节点categoryId
   #### 2：学习目标
        如何设计界封装无限层级的树状数据结构
        递归算法的设计思想
          自己调用自己
          递归一定要有一个结束条件，否则就成了一个死循环
        如何处理复杂对象重排
        重写hashcode和equals的注意事项  
   #### 获取品类子节点（平级）
        step1：非空校验
        step2：根据categoryId查询类别
        step3：查询子类别
        step4：返回结果
   #### 增加节点
        step1：非空校验
        step2：添加节点
        step3：返回结果
   #### 修改节点
        step1：非空校验
        step2：根据categoryId查询类别
        step3：修改类别
        step4：返回结果
   #### 获取当前分类id及递归子节点categoryId
       先定义一个递归的方法
         先查找本节点
             set里面的集合不可重复，通过类别id判断是不是重复，要重写类别对象的equals方法，在重写equals方法前先重写hashcode方法
                 两个类相等这两个类的hashcode一定相等，如果两个类的hashcode相等两个类不一定相等
         查找categoryId下的子节点（平级）
             遍历这个集合拿到这个每个子节点
             对集合的每个节点调用当前这个递归方法
         递归的结束语句就是 categoryList==null&&categoryList.size()<=0
       step1：参数的非空校验
       step2：查询
   ### 商品模块
   #### 前台功能
        产品搜索（前台关键字搜索，对应后台是模糊查询；根据类别搜索产品，要把所有这个类别下的商品都要查询出来，这时就要用到递归查询子类别，查询这个类别下的产品）
        动态排序（按照价格从高往低或从低往高显示）把产品搜索和动态排序放到一个接口
        商品详情（在商品详情页面可以加入购物车）
   #### 后台功能
        商品列表（查询在售和下架）
        商品搜索
        图片上传（SpringMVC）
        富文本上传（图文上传，前台会有个富文本编译器）
        商品详情
        商品上下架
        增加商品
        更新商品
   #### 学习目标
        FTP服务的对接(图片上传，图片通过FTP服务器保存到单独的图片服务器)
        SpringMVC文件上传
        流读取properties配置文件（读取配置文件的一个封装类）
        抽象POJO（数据库对应一个实体类）、BO（business object业务逻辑层的实体类）、VO（view object视图层的实体类）对象之间的转换关系及解决思路
            如果数据库的实体类满足不了往前端视图层显示的要求，会在视图层创建VO对象，会将POJO对象转成VO对象
        joda-time快速入门（时间处理的工具包）
        静态块
        Mybatis-PageHelper高效准确的分页及动态排序
        Mybatis对List遍历的实现方法
        Mybatis对where语句动态拼接
        POJO、BO、business、object、VO、view、object
        POJO、VO
   #### 新增OR更新产品
   ##### controller层
         step1：判断是否登录
         step2：判断用户权限是否为管理员
         step3：上传数据
   ##### service层
         step1：参数非空校验
         step2：设置商品主图 sub_images --> 1.jpg,2.jpg,3.png 
                           取出第一张，它是一个字符串，
         step3：商品save or update
         step4：返回结果 
         
         
   #### 配置虚拟路径
      Tomcat文件夹目录下的conf --> server.xml --> 
                       <Context path="/uploadpic" docBase="D:\面试强化\ftpfile" reloadable="true" />
                       path="/uploadpic" 虚拟路径
                       docBase="D:\面试强化\ftpfile" 存放图片的地址
                       reloadable="true"  自动加载
                       
      bigDecimall要用他的字符串构造方法，否则也不准确
   
   
   ### 购物车添加
       step1：参数非空校验
       step2：根据productId和userId查询购物信息，看是否存在，如果查到信息，说明已经添加到购物车了，只需要更新数量
       
   ### 查询购物车产品的数量
       如果不存在这个用户，name购物车会返回null,此时如果返回int类型就不对，应该用Integer类型，如果非要返回int,就用mysql里边的一个函数ifnull
    
   ### 收货地址
       插入地址，返回的是受影响的行数并不是返回的id，如果要返回id，要是用主键useGenerateKeys=true,生成的主键的这个值赋给shipping里面的id，keyProperties="id"
       
                              
           
     
         
