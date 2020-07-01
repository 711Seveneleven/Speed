package script.bl.demo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.constant.Direction;
import script.model.demo.Settings;
import topo.toplogies.TopoAndroid;

public abstract class SettingsBase extends TopoAndroid {
    @Override
    protected boolean setUp() throws Exception {
        // 如果有辅测设备可以复用代码，如：enterOtherSettings(TOPO.phoneSUT)
        return enterSettings(TOPO.phoneDUT);
    }

    @Override
    protected boolean tearDown() throws Exception {
        logger.info("清理：关闭应用");
        return Common.forceStopApplication(TOPO.phoneDUT, Settings.PACKAGE_NAME);
    }

    /**
     * 打开设置主页
     *
     * @return 成功-true，失败-false
     */
    public static boolean enterSettings(Phone phone) throws Exception {
        logger.info("预置条件：点亮屏幕");
        assertTrue(Common.setLightUpScreenEnable(phone, Settings.YES));

        logger.info("预置条件：解锁进入桌面");
        assertTrue(Common.swipe(phone, Direction.UP, 3));

        logger.info("预置条件：返回桌面");
        Common.goBackHome(phone, Settings.GO_HOME_BACK_TIMES);

        logger.info("预置条件：打开设置");
        return Common.startApplication(phone, Settings.PACKAGE_NAME);
    }

    /**
     * 上下滑动
     * @param phone 待测设备
     * @param times 滑动次数
     */
    public static void swipeUpAndDown(Phone phone, Integer times, Integer steps) throws Exception{
        for(int count = 0; count < times; count++){
            Common.swipe(phone, Direction.UP, steps);
            Common.swipe(phone, Direction.DOWN, steps);
        }
    }
}
