package script.bl.demo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.constant.Direction;
import aw.phenix.webview.AutoWebView;
import topo.toplogies.TopoAndroid;

public abstract class TestWebViewBase extends TopoAndroid {
    @Override
    protected boolean setUp() throws Exception {
        Phone phone = TOPO.phoneDUT;
        System.out.println(phone);
        //点亮屏幕
        logger.info("预置条件:点亮屏幕");
        assertTrue(Common.setLightUpScreenEnable(phone, true));

        //解锁
        logger.info("预置条件:滑动解锁");
        assertTrue(Common.swipe(phone, Direction.UP, 5));

        //返回桌面
        logger.info("预置条件：返回桌面");
        Common.goBackHome(phone);

        //打开软件
        logger.info("预置条件：打开软件");
        assertTrue(Common.startApplication(phone, "com.example.testwebviewlow"));

        //清理webview映射
        logger.info("预置条件：清理webview映射");
        AutoWebView autoWebView = new AutoWebView(phone);
        assertTrue(autoWebView.closeAllWebViewAdbForward());
        return true;
    }

    @Override
    protected boolean tearDown() {
        Phone phone = TOPO.phoneDUT;
        //清理webview映射
        logger.info("后置条件：清理webview映射");
        AutoWebView autoWebView = new AutoWebView(phone);
        autoWebView.closeAllWebViewAdbForward();
        return true;
    }

}
