Readme
---

我在github上备份了这个项目，并且使用travis持续集成


18/3/17

实现了注册用户的demo

后端要做的：
把model绑定一个id，传给view，这样前端才能将用户的输入填进model返回给后端。
看UserController的visitRegisterPage方法，通过model.addAttribute把“user”这个id和User对象绑定在一起，然后返回相应的view。

前端要做的：
参见addUser.html。使用thymeleaf的命名空间（line2），创建表单，把这个表单与id为“user”的对象绑定，同时把表单和url为/user/register的post方法绑定（line9），
接下来就是把输入框和对象的属性一一对应即可。
当表单提交时，后端可以通过@ModelAttribute取得前端传回的对象。

这只是个demo，因此前端要做的就是学习thymeleaf，重写register页面。后端也要把相应的get方法改好。
