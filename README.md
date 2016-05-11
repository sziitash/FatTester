# FatTester
更新默认的android api 版本，重新上传。并更名为FatTester<br />
各种android功能实现的相关demo。<br />
有service配合broadcast实现更好、可定制的持续集成功能；<br />
---------------------------------------------------------------------------<br />
有专项测试实例demo，比如内存泄露实例、函数耗时过长等<br />
再结合sdk相关工具(systrace/traceview/Surfaceflinger)进行专项性能测试示例。<br />
---------------------------------------------------------------------------<br />
## broadcast/service的相关demo:<br />
####broadcast测试demo:<br />
am broadcast -a test.noti;<br />
<br />
####通过发广播，启动service，实现脱usb线运行uiautomator2.0测试:<br /> 
`启动:` am broadcast -a test.StartRunuia --es testpkg com.meizu.nltemtbf --es testclass ApplicationTest --es testcase test007Email<br />
`停止:` am broadcast -a test.StopRunuia<br/>
<br />
####广播启动service统计cpu使用率，存储在/sdcard/目录下的csv文件：<br />
`启动:` am broadcast -a test.cpustart --es pkgName com.meizu.flyme.launcher<br />
`停止:` am broadcast -a test.cpustop<br />






