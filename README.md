# ***【说明】***
## 0. 脚本化分层设计及脚本编写规范
[《脚本化分层设计及脚本编写规范》](http://10.13.33.221/autotest/doc/script-standard.pdf)

## 1. AW接口文档
[Phenix AW文档](http://10.13.33.221/autotest/doc/phenix-aw/ "Phenix-AW")

## 2. Idea批量执行配置
![runconfig][config]

[config]: res/runconfig.png

## 3. FAQ
### 3.1 Common.getFileCountByFileType()方法编译报错

![faq-1][faq-maven]

[faq-maven]: res/faq-maven.png

    请把C:\Users\<User>\.m2\com\oppo\autotest\topologies\目录删除（默认下载地址，自定义仓库请删除对应目录），然后再刷新

### 3.2 "phone is not installed server." 

![faq-2][faq-not-install-server]

[faq-not-install-server]: res/phone_not_install-server.png

    请扫描一下二维码，安装“ATFM”工具（注意：按照不同项目代号手机对应不同的安装包）。

![avatar][install]

[install]: res/install.png
    

## 4. 脚本commit信息
    【提交类型】: （BUG/新功能/需求修改/版本制作/代码整理/解决编译不过/阶段性递交/追加递交）
    【问题描述】: （对应提交类型的问题描述）
    【上传人员】: （<姓名> <工号>） 
    【修改内容】: （代码修改点描述）
    
            1. xxxx；
    
            2. xxxx；
    
    【相关单号】:（<JIRA问题单号/需求单号>）
    【需要测试】:是 
