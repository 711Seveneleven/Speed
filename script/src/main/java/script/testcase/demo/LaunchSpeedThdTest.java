package script.testcase.demo;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.constant.ClickType;
import aw.phenix.monitor.LaunchSpeed;
import aw.phenix.widget.Widget;
import oppo.autotest.phenix.log.PhenixLog;
import topo.toplogies.TopoAndroid;

/**
 * @ClassName LaunchSpeedTest
 * @Description TODO
 * @Author: wangliao 80260910
 * @Date: 2020/1/20 10:18
 */
public class LaunchSpeedThdTest extends TopoAndroid {

    @Override
    protected boolean setUp() throws Exception {
        Phone phone = TOPO.phoneDUT;
        String pkgName = "com.coloros.yoli";
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    @Override
    protected boolean test() throws Exception {
        String pkgName = "com.coloros.yoli";
        Phone phone = TOPO.phoneDUT;

        // 定义工程路径，用于获取依赖的工具
        String mtnPath = "D:\\WORK\\localEA\\release\\LocalPerfTestTool_1.5.2_20200306\\LocalPerfTestTool_1.5.2_20200306";
        LaunchSpeed.setMtnPath(mtnPath);


        // 方案三：通过 已有训练模型 获取启动速度

        // 训练模型路径
        String modelFile = "D:\\WORK\\localEA\\release\\LocalPerfTestTool_1.5.2_20200306\\LocalPerfTestTool_1.5.2_20200306\\tools\\timemachine-project\\videos\\yoli.h5";
        // 开始阶段设置：开始帧属于第几个非稳定阶段，若从桌面图标启动，设置为0即可。详解：第0个非稳定阶段--icon被点击后变暗瞬间
        String startStage = "0";
        // 设置结束帧属于哪个阶段，最终会取该阶段的第一帧作为结束帧
        String endStage = "5";
        // 设置相邻图片相似度（默认0.98）
        //  参数设置说明：
        //      1.启动过程存在渐变画面的建议设置为0.999，如：短视频（预览画面为渐变加载），软件商店（轮播图为渐变加载）
        //      2.启动过程不存在渐变画面的建议设置为0.98，如：拨号盘
        LaunchSpeed.setThreshold("0.999");

        // 设置视频分析模式 1：训练集模式 2：图片标记模式 3：神经网络模式
        LaunchSpeed.setAnalyzeType(3);
        LaunchSpeed.setModelFile(modelFile);
        LaunchSpeed.setStartStage(startStage);
        LaunchSpeed.setEndStage(endStage);
        // 设置平均耗时计算方式 0：去掉最大最小值后计算平均值 1：直接计算平均值，默认0
        LaunchSpeed.setAverageType(0);

        // 进行5次测试
        for (int i=0; i<1; i++) {
            PhenixLog.info(String.format("第%d次",i+1));
            // 开始录制视频，并检查录制结果
            boolean record = LaunchSpeed.startRecord(phone, pkgName);
            if (!record) return false;
            // 启动软件，这里采用phenix已有接口，也可以自定义启动方式，如点击icon
            Widget.clickByText(phone, "视频", ClickType.NORMAL);
            // 等待进入稳定阶段（根据每个APP到达稳定的时间决定是否需要等待）
            Common.wait(phone,5);
            // 结束视频录制（接口内部会进行视频分析与单次测试报告生成）
            boolean stop = LaunchSpeed.stopRecord(phone);
            if (!stop) {
                PhenixLog.error("stop出错了");
            } else {
                // 【可不调用】获取单次耗时，用户获取后可自行处理
                PhenixLog.info(LaunchSpeed.getCostTime());
            }
            // 退出软件（这里采用phenix已有接口，也可以根据场景需要自定义退出方式，如back退出）
            Common.forceStopApplication(phone, pkgName);
            Common.wait(phone,5);
        }
        // 【可不调用】获取总报告数据，用户获取后可自行处理
        System.err.println(LaunchSpeed.getTotalReportData());
        // 生成总报告
        LaunchSpeed.generateTotalReport();
        return true;
    }

    @Override
    protected boolean tearDown() throws Exception {
        Phone phone = TOPO.phoneDUT;
        String pkgName = "com.coloros.yoli";
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    public static void main(String[] args) {
        new LaunchSpeedThdTest().run(args);
    }
}
