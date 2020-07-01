package script.testcase.demo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.logger.Log;
import aw.phenix.monitor.Performance;
import topo.toplogies.TopoAndroid;


public class MonitorTestPartTow extends TopoAndroid {
    @Override
    protected boolean setUp() throws Exception {
        return true;
    }

    @Override
    protected boolean test() throws Exception {

        Phone phone = TOPO.phoneDUT;
//        String pkgName = "com.oapm.sample.oppo";
//        Performance.startMonitor(phone, pkgName);
//        Log.info("sleep 150...");
//        Common.wait(phone, 30);
        Performance.stopMinitor(phone);
        return true;
    }


    @Override
    protected boolean tearDown() throws Exception {
        return true;
    }

    public static void main(String[] args) {
        new MonitorTestPartTow().run(args);
    }
}
