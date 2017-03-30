# JeeSite 新平江分销快速开发平台

## 平台项目简介

JeeSite是基于多个优秀的开源项目，高度整合封装而成的高效，高性能，强安全性的**开源**Java EE快速开发平台。

JeeSite是您快速完成项目的最佳基础平台解决方案，JeeSite是您想学习Java平台的最佳学习案例，JeeSite还是接私活的最佳助手。

JeeSite是在Spring Framework基础上搭建的一个Java基础开发平台，以Spring MVC为模型视图控制器，MyBatis为数据访问层，
Apache Shiro为权限授权层，Ehcahe对常用数据进行缓存，Activit为工作流引擎。是JavaEE界的最佳整合。

JeeSite主要定位于企业信息化领域，已内置企业信息化系统的基础功能和高效的**代码生成**工具，
包括：系统权限组件、数据权限组件、数据字典组件、核心工具组件、视图操作组件、工作流组件、代码生成等。
前端界面风格采用了结构简单、性能优良、页面美观大气的Twitter Bootstrap页面展示框架。
采用分层设计、双重验证、提交数据安全编码、密码加密、访问验证、数据权限验证。
使用Maven做项目管理，提高项目的易开发性、扩展性。

JeeSite目前包括以下四大模块，系统管理（SYS）模块、
内容管理（CMS）模块、在线办公（OA）模块、代码生成（GEN）模块。 **系统管理模块** ，包括企业组织架构（用户管理、机构管理、区域管理）、
菜单管理、角色权限管理、字典管理等功能； **内容管理模块** ，包括内容管理（文章、链接），栏目管理、站点管理、
公共留言、文件管理、前端网站展示等功能； **在线办公模块** ，提供简单的请假流程实例；**代码生成模块** ，完成重复的工作。

JeeSite 提供了常用工具进行封装，包括日志工具、缓存工具、服务器端验证、数据字典、当前组织机构数据
（用户、机构、区域）以及其它常用小工具等。另外还提供一个强大的在线 **代码生成** 工具，
此工具提供简单的单表、一对多、树结构功能的生成，如果对外观要求不是很高，生成的功能就可以用了。
如果你使用了JeeSite基础框架，就可以很高效的快速开发出，优秀的信息管理系统。

## 内置功能

1.	用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.	机构管理：配置系统组织机构（公司、部门、小组），树结构展现，可随意调整上下级。
3.	区域管理：系统城市区域模型，如：国家、省市、地市、区县的维护。
4.	菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.	角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.	字典管理：对系统中经常使用的一些较为固定的数据进行维护，如：是否、男女、类别、级别等。
7.	操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
8.	连接池监视：监视当期系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。
9.	工作流引擎：实现业务工单流转、在线流程设计器。


## 技术选型

1、后端

* 核心框架：Spring Framework 4.1
* 安全框架：Apache Shiro 1.2
* 视图框架：Spring MVC 4.1
* 服务端验证：Hibernate Validator 5.2
* 布局框架：SiteMesh 2.4
* 工作流引擎：Activiti 5.21
* 任务调度：Spring Task 4.1
* 持久层框架：MyBatis 3.2
* 数据库连接池：Alibaba Druid 1.0
* 缓存框架：Ehcache 2.6、Redis
* 日志管理：SLF4J 1.7、Log4j
* 工具类：Apache Commons、Jackson 2.2、Xstream 1.4、Dozer 5.3、POI 3.9

2、前端

* JS框架：jQuery 1.9。
* CSS框架：Twitter Bootstrap 2.3.1（稳定是后台，UI方面根据需求自己升级改造吧）。
* 客户端验证：JQuery Validation Plugin 1.11。
* 富文本在线编辑：CKEditor
* 在线文件管理：CKFinder
* 动态页签：Jerichotab
* 手机端框架：Jingle
* 数据表格：jqGrid
* 对话框：jQuery jBox
* 下拉选择框：jQuery Select2
* 树结构控件：jQuery zTree
* 日期控件： My97DatePicker


## 安全考虑

1. 开发语言：系统采用Java 语言开发，具有卓越的通用性、高效性、平台移植性和安全性。
2. 分层设计：（数据库层，数据访问层，业务逻辑层，展示层）层次清楚，低耦合，各层必须通过接口才能接入并进行参数校验（如：在展示层不可直接操作数据库），保证数据操作的安全。
3. 双重验证：用户表单提交双验证：包括服务器端验证及客户端验证，防止用户通过浏览器恶意修改（如不可写文本域、隐藏变量篡改、上传非法文件等），跳过客户端验证操作数据库。
4. 安全编码：用户表单提交所有数据，在服务器端都进行安全编码，防止用户提交非法脚本及SQL注入获取敏感数据等，确保数据安全。
5. 密码加密：登录用户密码进行SHA1散列加密，此加密方法是不可逆的。保证密文泄露后的安全问题。
6. 强制访问：系统对所有管理端链接都进行用户身份权限验证，防止用户直接填写url进行访问。


## 常见问题

1. 用一段时间提示内存溢出，请修改JVM参数：-Xmx512m -XX:MaxPermSize=256m
2. 有时出现文字乱码：修改Tomcat的server.xml文件的Connector项，增加URIEncoding="UTF-8"
3. 为什么新建菜单后看不到新建的菜单？因为授权问题，菜单管理只允许最高管理员账号管理（最高管理员默认账号：admin 密码：admin）。






