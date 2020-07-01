package script.testcase.demo;

import aw.phenix.base.Phone;
import aw.phenix.cv.ScreenshotCheck;
import aw.phenix.webview.AutoWebView;
import aw.phenix.webview.H5Operation;
import oppo.autotest.phenix.log.PhenixLog;
import script.bl.demo.TestWebViewBase;
import java.net.URISyntaxException;


public class WebView_DEMO extends TestWebViewBase {
    @Override
    protected boolean test() throws URISyntaxException {
        /*
            20190910：屏幕截图一次性检测接口demo
         */
        Phone phone = TOPO.phoneDUT;
        //初始化对象
        ScreenshotCheck screenshotCheck = new ScreenshotCheck(phone);
        //设置是否开启白屏检测接口（默认开启）
        ScreenshotCheck.setWhiteCheck(true);
        //设置是否开启黑屏检测接口（默认不开启）
        ScreenshotCheck.setBlackCheck(true);
        //设置是否开启花屏检测接口（默认不开启）
        ScreenshotCheck.setBlackAndWhiteCheck(true);
        //检测，返回true/false
        screenshotCheck.checkScreenshotWithImageRecognition(5);



        /*
            20190827：屏幕截图持续检测接口demo
         */
        //初始化对象
        ScreenshotCheck screenshotCheckAlive = new ScreenshotCheck(phone);
        //设置是否开启白屏检测接口（默认开启）
        ScreenshotCheck.setWhiteCheck(true);
        //设置是否开启黑屏检测接口（默认不开启）
        ScreenshotCheck.setBlackCheck(true);
        //设置是否开启花屏检测接口（默认不开启）
        ScreenshotCheck.setBlackAndWhiteCheck(true);
        //开始进行持续检测
        screenshotCheckAlive.startScreenshotCheck();
        //持续20s（一般进行H5的操作）
        try {
            Thread.sleep(20000);
        } catch (Exception e) {
            PhenixLog.error("sleep error" + e);
        }
        //停止持续检测
        screenshotCheckAlive.stopScreenshotCheck();
        //获取持续检测结果
        screenshotCheckAlive.getCheckResult();



        /*
            20190820：H5白屏检测与性能接口demo
         */
        //APP进程名称
        String androidProcess = "com.example.testwebviewlow";
        //初始化对象
        AutoWebView autoWebView = new AutoWebView(phone, androidProcess);
        //初始化webview
        boolean initResult =  autoWebView.initWebView();
        if (initResult==false){return false;}
        //利用图像识别进行白屏检测（chrome小于60）
        autoWebView.checkH5WhitePageWithImageRecognition(5);
        //利用performance API paint请求进行白屏检测（chrome大于等于60）
        autoWebView.checkH5WhitePageWithPaint(5);
        //自动选择白屏检测方案，一般调用这个接口就可以，不用调用上面两个接口
        autoWebView.checkH5WhitePage(5);
        //获取每个请求的性能数据
        autoWebView.getRequestsPerformance();
        //获取网页整体性能数据
        autoWebView.getPerformance();
        //获取所有request请求耗时
        autoWebView.getRequestTime();



        /*
            20190802：H5操作相关接口
         */
        //获取网页源码
        System.out.println(autoWebView.pageSource());
        //跳转
        System.out.println(autoWebView.get("http://sogou.com"));
        //获取当前页面标题
        System.out.println(autoWebView.currentTitle());
        //获取当前页面URL
        System.out.println(autoWebView.currentUrl());
        //页面向上滚动
        System.out.println(autoWebView.scrollTo(500));
        //刷新
        System.out.println(autoWebView.refreah());
        //返回上一页
        System.out.println(autoWebView.back());
        //前进到下一页
        System.out.println(autoWebView.forward());
        //设置cookie
        System.out.println(autoWebView.setCookie("wl", "wlvalue"));
        //获取某个cookie的value
        System.out.println(autoWebView.getCookie("wl"));
        //获取所有cookies
        System.out.println(autoWebView.getCookies());
        //删除指定cookie
        System.out.println(autoWebView.deleteCookie("wl"));
        //执行JS脚本
        autoWebView.executeScript("window.scrollTo(0,500);");


        autoWebView.get("http://baidu.com");
        //通过id查找
        H5Operation h5Operation = autoWebView.findElementById("index-kw");
        //输入框输入内容
        h5Operation.sendKeys("findElementById");
        //清空输入框
        h5Operation.clear();
        //设置元素属性值
        h5Operation.setAttribute("value", "setAttribute");
        //获取元素属性值
        h5Operation.getAttribute("value");
        h5Operation.getAttribute("outerHTML");
        //通过className查找
        H5Operation h5Operation2 = autoWebView.findElementByClassName("se-input adjust-input");
        h5Operation2.setAttribute("value", "ClassName");
        //通过selector查找
        H5Operation h5Operation3 = autoWebView.findElementBySelector("#index-kw");
        h5Operation3.setAttribute("value", "Selector");
        //通过name查找
        H5Operation h5Operation4 = autoWebView.findElementByName("word");
        h5Operation4.setAttribute("value", "Name");
        //通过tagName查找
        H5Operation h5Operation5 = autoWebView.findElementByTagName("input");
        h5Operation5.setAttribute("value", "TagName");

        autoWebView.get("http://www.divcss5.com/yanshi/checkbox.html");
        H5Operation h5Operation6 = autoWebView.findElementsByName("Fruit").get(0);
        //CheckBox勾选
        h5Operation6.check();
        //CheckBox检查是否勾选
        h5Operation6.isChecked();
        //获取所有可勾选元素的属性值
        h5Operation6.getCheckableOptions("value");
        //获取已勾选元素的属性值
        h5Operation6.getCheckedOptions("value");


        autoWebView.get("http://au.9you.com/event/nineyearsold/quickreghtml/");
        H5Operation h5Operation112 = autoWebView.findElementById("mreg_area");
        //通过索引选择
        h5Operation112.selectByIndex(1);
        //通过value选择
        h5Operation112.selectByValue("12");
        //通过text选择
        h5Operation112.selectByText("山东万佳(河北网通)");
        h5Operation112.getSelectableOptions("innerText");
        h5Operation112.getSelectedOption("innerText");

        autoWebView.get("http://www.divcss5.com/fanli/form-select.html?_d_id=79dc9d5183dfa4e38d512180666b77");
        //通过xpath查找
        H5Operation h5Operation7 = autoWebView.findElementByXpath("//*[@id=\"jumpMenu\"]");
        //下拉框通过text选择
        h5Operation7.selectByText("DIVCSS5");
        //获取下拉框所有元素指定属性值
        h5Operation7.getSelectableOptions("text");
        //获取下拉框已选择元素指定属性值
        h5Operation7.getSelectedOption("text");
        //通过完整链接查找
        H5Operation h5Operation8 = autoWebView.findElementByLinkText("http://www.divcss5.com/html/h336.shtml");
        //获取元素的innerText
        h5Operation8.getText();

        autoWebView.get("http://baidu.com");
        H5Operation h5Operation9 = autoWebView.findElementByTagName("input");
        h5Operation9.setAttribute("value","TagName");

        H5Operation h5Operation10 = autoWebView.findElementsByTagName("input").get(0);
        h5Operation10.setAttribute("value","TagName1");

        autoWebView.get("https://m.runoob.com/python/python-tutorial.html");
        //通过链接部分字符串查找
        H5Operation h5Operation11 = autoWebView.findElementByPartialLinkText("/python3/python3-tutorial.html");
        h5Operation11.getText();

        autoWebView.get("http://baidu.com");
        H5Operation h5Operation00 = autoWebView.findElementById("index-kw");
        h5Operation00.sendKeys("test send keys");
        H5Operation h5Operation0 = autoWebView.findElementById("index-form");
        //表单提交
        h5Operation0.submit();

        autoWebView.get("http://baidu.com");
        H5Operation h5Operation888 = autoWebView.findElementById("index-bn");
        //长按
        h5Operation888.longPress(5000);
        //单击
        h5Operation888.click();

        return true;
    }

    public static void main(String[] args) {
        new WebView_DEMO().run(args);
    }
}
