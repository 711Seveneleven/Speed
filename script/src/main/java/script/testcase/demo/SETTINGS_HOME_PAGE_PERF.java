package script.testcase.demo;

import aw.phenix.monitor.Performance;
import script.bl.demo.SettingsBase;
import script.model.demo.Settings;

/*************************************************************************
 * @ 项目名称 : 设置
 * @ 用例 ID:   SETTINGS_HOME_PAGE_PERF
 * @ 编写人 :   周波 80213472
 * @ 创建时间 : 2019-05-14
 * @ 修改时间 : 2019-05-14
 * @ 修改说明 : 无
 * @ 脚本描述 : 打开设置，监控滑动性能
 * @ 前置条件 :
 *      1. 返回桌面；
 *      2. 打开设置。
 * @ 测试步骤 :
 *      1. 上下滑动设置页面100次；
 *      2. dump滑动后的对内存。
 * @ 预期结果 : 监控滑动的性能指标
 ************************************************************************/
public class SETTINGS_HOME_PAGE_PERF extends SettingsBase {
    @Override
    protected boolean setUp() throws Exception {
        logger.info("预置条件：开启性能监控");
        Performance.startMonitor(TOPO.phoneDUT, Settings.MONITOR_INERVAL, Settings.PACKAGE_NAME);
        return super.setUp();
    }
    @Override
    protected boolean test() throws Exception {
        logger.info("步骤1：上下滑动100次");
        swipeUpAndDown(TOPO.phoneDUT, 100, 10);

        logger.info("步骤2：dump堆内存");
        Performance.dumpHeap(TOPO.phoneDUT, Settings.PACKAGE_NAME);
        return true;
    }
    @Override
    protected boolean tearDown() throws Exception {
        logger.info("收尾：关闭性能监控");
        Performance.stopMinitor(TOPO.phoneDUT);
        return super.tearDown();
    }
    public static void main(String[] args) {
        new SETTINGS_HOME_PAGE_PERF().run(args);
    }
}
