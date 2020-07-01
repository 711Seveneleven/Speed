package script.testcase.newPerfDemo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.logger.Log;
import aw.phenix.monitor.Performance;
import topo.toplogies.TopoAndroid;

/**
 * @ClassName PerfOld
 * @Description TODO
 * @Author: wangliao 80260910
 * @Date: 2020/4/16 18:26
 */
public class PerfOld extends TopoAndroid {
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
        Performance.startMonitor(phone, pkgName);
        Common.startApplication(phone, pkgName);
        Log.info("sleep 150...");
        Common.wait(phone, 150);
        Performance.stopMinitor(phone);
        return true;
    }


    @Override
    protected boolean tearDown() throws Exception {
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    public static void main(String[] args) {
        new PerfOld().run(args);
    }
}