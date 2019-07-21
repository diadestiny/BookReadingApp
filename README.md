# BookReadingApp
## 相关技术

* Viewager,fragment 实现主界面滑动和底部导航栏
* 用litepal快速实现登录，注册，修改密码的界面功能
* GridView在书架进行展示

## 采坑点

* AS导入github开源项目跑不起来解决方法:
> 1. AS的gradle构建文件问题,找到项目目录下.gradle,gradle,build.gradle,gradle.properties文件全部替换成你的本地AS项目文件
> 2. 删除.idea文件
> 3. 网络问题,下载依赖包延迟（也可能与外网有关）