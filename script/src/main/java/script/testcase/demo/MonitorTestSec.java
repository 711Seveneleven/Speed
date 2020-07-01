package script.testcase.demo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.logger.Log;
import aw.phenix.monitor.Performance;
import topo.toplogies.TopoAndroid;


public class MonitorTestSec extends TopoAndroid {
    @Override
    protected boolean setUp() throws Exception {
        return true;
    }

    @Override
    protected boolean test() throws Exception {

        Phone phone = TOPO.phoneDUT;
        String pkgName1 = "com.tencent.mm";
        String pkgName2 = "com.example.testwebview";
        Performance.startMonitor(phone, pkgName1, pkgName2);
        Log.info("sleep 30...");
        Common.wait(phone, 30);
        Performance.stopMinitor(phone);
        return true;
    }


    @Override
    protected boolean tearDown() throws Exception {
        return true;
    }

    public static void main(String[] args) {
        new MonitorTestSec().run(args);
    }
}
