package script.testcase.newPerfDemo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.monitor.Performance;
import topo.toplogies.TopoAndroid;

/**
 * @ClassName PerfOapmSNErr
 * @Description TODO
 * @Author: wangliao 80260910
 * @Date: 2020/4/16 18:40
 */
public class PerfOapmSNErr extends TopoAndroid {
    Phone phone;
    String pkgName = "com.oapm.sample";

    @Override
    protected boolean setUp() throws Exception {
        phone = TOPO.phoneDUT;
        return true;
    }

    @Override
    protected boolean test() throws Exception {
        Common.startApplication(phone, pkgName);
        Performance.startMonitor(phone, pkgName);
        return true;
    }


    @Override
    protected boolean tearDown() throws Exception {
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    public static void main(String[] args) {
        new PerfOapmSNErr().run(args);
    }
}
