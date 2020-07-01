package script.testcase.newPerfDemo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.logger.Log;
import aw.phenix.monitor.Performance;
import topo.toplogies.TopoAndroid;

/**
 * @ClassName PerfMtnApi
 * @Description TODO
 * @Author: wangliao 80260910
 * @Date: 2020/4/16 18:45
 */
public class PerfMtnApi extends TopoAndroid {
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
        Performance.startMtnMonitor(phone, pkgName, pkgName,"com.oapm.sample:oapm");
        Common.startApplication(phone, pkgName);
        Log.info("sleep 150...");
        Common.wait(phone, 150);
        Performance.stopMtnMonitor(phone);
        return true;
    }


    @Override
    protected boolean tearDown() throws Exception {
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    public static void main(String[] args) {
        new PerfMtnApi().run(args);
    }
}