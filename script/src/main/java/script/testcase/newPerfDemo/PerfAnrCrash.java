package script.testcase.newPerfDemo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.common.adb.Device;
import aw.phenix.constant.ClickType;
import aw.phenix.monitor.Performance;
import aw.phenix.widget.Widget;
import topo.toplogies.TopoAndroid;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PerfAnrCrash
 * @Description TODO
 * @Author: wangliao 80260910
 * @Date: 2020/4/16 18:48
 */
public class PerfAnrCrash extends TopoAndroid {
    Phone phone;
    String pkgName = "com.oppotest.perf_sample";

    @Override
    protected boolean setUp() throws Exception {
        phone = TOPO.phoneDUT;
        Common.forceStopApplication(phone, pkgName);
        Common.wait(phone, 1);
        return true;
    }

    @Override
    protected boolean test() throws Exception {
        Performance.startMonitorBc(phone, pkgName);

        Common.startApplication(phone, pkgName);
        Common.wait(phone, 5);
        Widget.clickByText(phone, "点我Crash", ClickType.NORMAL);
        Common.wait(phone, 8);

        Performance.stopMonitorBc(phone);
        return true;
    }


    @Override
    protected boolean tearDown() throws Exception {
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    public static void main(String[] args) {
        new PerfAnrCrash().run(args);
    }
}