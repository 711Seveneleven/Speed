package script.testcase.demo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.constant.ClickType;
import aw.phenix.constant.Direction;
import aw.phenix.widget.Widget;
import topo.toplogies.TopoAndroid;

/**
 * @ClassName CCTStartUpTest
 * @Description TODO
 * @Author: wangliao 80260910
 * @Date: 2020/5/14 20:52
 */
public class CCTStartUpTest extends TopoAndroid {
    Phone phone;
    String pkgName = "com.oapm.sample";

    @Override
    protected boolean setUp() throws Exception {
        phone = TOPO.phoneDUT;
        Common.forceStopApplication(phone, pkgName);
        Common.wait(phone, 3);
        // home
        Common.goHome(phone);
        // 左滑，查看某个页面是否存在应用
        int i = 0;
        while (i<20) {
            if (Widget.checkWidgetExistByText(phone, "性能测试DEMO", 0, true)) {
                System.out.println("find 性能测试DEMO");
                return true;
            }
            Common.swipe(phone, Direction.LEFT, 30);
            i++;
        }
        return false;
    }

    @Override
    protected boolean test() throws Exception {
        for (int i=0; i<20; i++) {
            Widget.clickByText(phone, "性能测试DEMO", ClickType.NORMAL);
            Common.wait(phone, 10);
            Common.goBack(phone);
            Common.wait(phone, 10);
        }
        return true;
    }


    @Override
    protected boolean tearDown() throws Exception {
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    public static void main(String[] args) {
        new CCTStartUpTest().run(args);
    }
}
