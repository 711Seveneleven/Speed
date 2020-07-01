package script.testcase.newPerfDemo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.monitor.Performance;
import topo.toplogies.TopoAndroid;

/**
 * @ClassName PerfSFSupportErr
 * @Description TODO
 * @Author: wangliao 80260910
 * @Date: 2020/4/16 18:43
 */
public class PerfSFSupportErr extends TopoAndroid {
    Phone phone;
    String pkgName = "com.oapm.sample";

    @Override
    protected boolean setUp() throws Exception {
        phone = TOPO.phoneDUT;
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    @Override
    protected boolean test() throws Exception {
        Performance.setSkippedFrameSwitch(true);
        Performance.startMonitor(phone, pkgName);
        return true;
    }


    @Override
    protected boolean tearDown() throws Exception {
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    public static void main(String[] args) {
        new PerfSFSupportErr().run(args);
    }
}
