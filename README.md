# 项目说明
[![Build Status](https://travis-ci.org/tlyong1992/My-Project.svg?branch=master)](https://travis-ci.org/tlyong1992/My-Project)

## 基础框架
开箱即用的springboot框架

## 编码规约

 + 数据库约定  
   约定数据库里所有表必须包含名为id主键字段。
   可能有人会说，正常来说不是每张表里都应该有id主键吗？但是，我们项目中由于之前开发不严谨，部分表没有id主键，或者不为id的主键。这里我们采用分布式的全球唯一码来作为id。

 + api出参约定  
   约定所有出参里含list，且其他请求会用到这组list，则list里所有对象必须含id唯一标识。
  
 + 入参约定
   约定token身份认证统一传入参数模式，后端采用aop切面编程识别用户身份。其他参数一律为json。
  
## resultfull接口约定

请求类型|用途                      
:----|:----:                  
post| 创建资源                   
get| 获取资源
put| 全量更新资源
patch| 局部更新资源
delete| 删除资源

