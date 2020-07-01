package script.testcase.demo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.constant.ClickType;
import aw.phenix.widget.Widget;
import topo.toplogies.TopoAndroid;

/**
 * @ClassName CCTDebugTest
 * @Description TODO
 * @Author: wangliao 80260910
 * @Date: 2020/5/6 16:02
 */
public class CCTDebugTest extends TopoAndroid {
    Phone phone;
    String pkgName = "com.oapm.sample";
//    String startOapmBc = "am broadcast -a oapm.info -n com.oapm.sample/com.oapm.perftest.receiver.PerfReceiver --es sn \"%s\" --ei orderId 123456  --ei orderState 0  --ei platformType 100";
//    String stopOapmBc = "am broadcast -a oapm.info -n com.oapm.sample/com.oapm.perftest.receiver.PerfReceiver --es sn \"%s\" --ei orderId 123456  --ei orderState 1  --ei platformType 100";

    @Override
    protected boolean setUp() throws Exception {
        phone = TOPO.phoneDUT;
        Common.forceStopApplication(phone, pkgName);
        Common.wait(phone,3);
        Common.startApplication(phone, pkgName);
        Common.wait(phone,3);
//        Common.executeAdbShellCommand(phone, String.format(startOapmBc, phone.getPhone().getSerial()));
        return true;
    }

    @Override
    protected boolean test() throws Exception {
        Widget.clickByText(phone, "模拟卡顿监控", ClickType.NORMAL);
        Common.wait(phone, 1);
        Widget.clickByText(phone, "主线程文件读写", ClickType.NORMAL);
        Common.wait(phone, 5);
        Widget.clickByText(phone, "主线程DB读写", ClickType.NORMAL);
        Common.wait(phone, 5);
        Widget.clickByText(phone, "递归调用", ClickType.NORMAL);
        Common.wait(phone, 5);

        Common.goBack(phone);
        Widget.clickByText(phone, "模拟IO监控", ClickType.NORMAL);
        Common.wait(phone, 1);
        Widget.clickByText(phone, "触发主线程IO的场景", ClickType.NORMAL);
        Common.wait(phone, 5);
        Widget.clickByText(phone, "触发BUFFER太小的场景", ClickType.NORMAL);
        Common.wait(phone, 5);

        Common.goBack(phone);
        Widget.clickByText(phone, "模拟内存泄漏监控", ClickType.NORMAL);
        Common.wait(phone, 1);
        Widget.clickByText(phone, "点我再等5s就内存泄漏", ClickType.NORMAL);
        Common.wait(phone, 20);

        return true;
    }


    @Override
    protected boolean tearDown() throws Exception {
//        Common.executeAdbShellCommand(phone, String.format(stopOapmBc, phone.getPhone().getSerial()));
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    public static void main(String[] args) {
        new CCTDebugTest().run(args);
    }
}
