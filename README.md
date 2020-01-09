# WXCallbackFix
解决微信回调的包名路径匹配问题。[![JitPack](https://jitpack.io/v/ParfoisMeng/WXCallbackFix.svg)](https://jitpack.io/#ParfoisMeng/WXCallbackFix)

- - - - -

### 情景
首先，我司也算是做 SaaS 的，所以除了通用平台 App 外，还有很多客户定制需求。比如改改 App Icon / App Name 什么的都是常见的定制需求。  
当然也需要改包名 applicationId，然后就要修改微信等第三方工具的 AppKey / AppSecret 之类。  
然而这些都可以通过一个配置文件解决，Jenkins 直接可以搞定。  
但是！微信！！就是 TMD 巨坑！！！  
如果你想要分享结果回调，或者需要微信登录功能，你就必须在指定包名路径 (applicationId) 下新建 wxapi 目录，并在该目录下新建 WXEntryActivity 类，[详见微信 Android 接入指南官方文档](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Access_Guide/Android.html)。  
问题就在这里了，每次 Jenkins 搞完，还得手动去修改定制代码里的 WXEntryActivity 包路径。  
本库就是为了解决这个问题而实现的。  
我参考了 ButterKnife 的实现方式，查阅了很多 AbstractProcessor 的教程，还有一系列其他类似实现，最终折腾出来这个库。  
可能是姿势不对，相关资料并不好找，本库折腾出来也算是不容易。  

- - - - -

### 使用
 - 引用类库 *请将last-version替换为最新版本号 [![](https://jitpack.io/v/ParfoisMeng/WXCallbackFix.svg)](https://jitpack.io/#ParfoisMeng/WXCallbackFix)  
```
    // 1.添加jitpack仓库
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    // 2.添加项目依赖（last-version替换为最新版本号）
    dependencies {
        implementation 'com.github.ParfoisMeng.WXCallbackFix:annotation:last-version'
        kapt 'com.github.ParfoisMeng:WXCallbackFix:last-version'
    }
```

- 代码  
```
    // 1. 依然需要你先按微信的要求，在通用的包名路径下新建 wxapi 目录，并在该目录下新建 WXEntryActivity
    //    此段示例代码使用的是 Umeng, 所以继承的是 WXCallbackActivity

    // 2. 在 WXEntryActivity 上添加注解 @WXCallbackFix("包名路径")，示例如下：
    @WXCallbackFix(BuildConfig.APPLICATION_ID + ".wxapi")
    public class WXEntryActivity extends WXCallbackActivity {
    }

    // 3. Build -> ReBuild Project
    //    等待 Build 完成，对应 Module 的 build/generated/source/kapt 路径下将生成新的符合指定包名路径的 WXEntryActivity.
```

- 其他  
[分享到支付宝](https://docs.open.alipay.com/215/105104/)、[分享到钉钉](https://ding-doc.dingtalk.com/doc#/native/oguxo2/8ebdfe57) 也是同理哦。  
举一反三，搞代码千万不要太死板~

### 更新
* 添加判断条件，如果已有，就不再创建 - 1.0.1
* 初版发布 - 1.0.0

### 计划
* 暂无

### 支持
劳烦各位大佬给个Star让我出去好装B行嘛！

### 其他
已使用<b>996 License</b>，为程序员发声，为自己发声。

[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)
