package script.bl.demo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.constant.ClickType;
import aw.phenix.constant.Direction;
import aw.phenix.monitor.Performance;
import aw.phenix.widget.Button;
import script.model.demo.PerfDemo;
import topo.toplogies.TopoAndroid;


public abstract class PerfDemoBase extends TopoAndroid {
    @Override
    protected boolean setUp() throws Exception {
        assertTrue(initPerfDemo(TOPO.phoneDUT));

        logger.info("预置条件：开启性能监控");
        Performance.startMonitor(TOPO.phoneDUT, 1, PerfDemo.PACKAGE_NAME);
        return true;
    }

    @Override
    protected boolean tearDown() throws Exception {
        logger.info("清理：停止性能监控");
        Performance.stopMinitor(TOPO.phoneDUT);

        Common.wait(TOPO.phoneDUT, 5);

        return Common.forceStopApplication(TOPO.phoneDUT, PerfDemo.PACKAGE_NAME);
    }

    /**
     * 打开性能DemoApk
     *
     * @return 成功-true，失败-false
     */
    public static boolean initPerfDemo(Phone phone) throws Exception {
        logger.info("预置条件：点亮屏幕");
        assertTrue(Common.setLightUpScreenEnable(phone, true));

        logger.info("预置条件：解锁进入桌面");
        assertTrue(Common.swipe(phone, Direction.UP, 5));

        logger.info("预置条件：返回桌面");
        Common.goBackHome(phone, 1);

        logger.info("预置条件：打开设置");
        return Common.startApplication(phone, PerfDemo.PACKAGE_NAME);
    }

    /**
     * 打开性能DemoApk
     *
     * @return 成功-true，失败-false
     */
    public static boolean testPerf(Phone phone, int times) throws Exception {
        for(int i = 0; i < times; i++) {
            Common.wait(phone, 10);

            logger.info("步骤：点击测试卡顿");
            assertTrue(Button.click(phone, PerfDemo.BLOCK_TEST, ClickType.NORMAL));

            Common.wait(phone, 10);

            logger.info("步骤：测试内存泄漏");
            assertTrue(Button.click(phone, PerfDemo.LEAK_TEST, ClickType.NORMAL));

            logger.info("步骤：屏幕旋转90度");
            assertTrue(Common.setRotation(phone, PerfDemo.ROTATION_90));

            Common.wait(phone, 1);

            logger.info("步骤：屏幕旋转0度");
            assertTrue(Common.setRotation(phone, PerfDemo.ROTATION_0));

            Common.wait(phone, 20);

            logger.info("步骤：点击测试ANR");
            assertTrue(Button.click(phone, PerfDemo.ANR_TEST, ClickType.NORMAL));


            Common.wait(phone, 10);

            logger.info("步骤：点击测试Crash");
            assertTrue(Button.click(phone, PerfDemo.CRASH_TEST, ClickType.NORMAL));

            Common.wait(phone, 10);
        }
        return true;
    }

}
