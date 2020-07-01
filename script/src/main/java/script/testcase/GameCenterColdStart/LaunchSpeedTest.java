package script.testcase.GameCenterColdStart;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.constant.ClickType;
import aw.phenix.monitor.LaunchSpeed;
import aw.phenix.widget.Widget;
import oppo.autotest.phenix.log.PhenixLog;
import topo.toplogies.TopoAndroid;


public class LaunchSpeedTest extends TopoAndroid {

    @Override
    protected boolean setUp() throws Exception {
        Phone phone = TOPO.phoneDUT;
        String pkgName = "com.nearme.gamecenter";
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    @Override
    protected boolean test() throws Exception {
        String pkgName = "com.nearme.gamecenter";
        Phone phone = TOPO.phoneDUT;

        // 定义工程路径，用于获取依赖的工具
        String mtnPath = "E:\\MTN";
        LaunchSpeed.setMtnPath(mtnPath);
        // 方案三：通过 已有训练模型 获取启动速度
        String modelFile="E:\\MTN\\tools\\timemachine-project\\videos\\GameCenterColdStart.h5";
        String startStage = "0";
        String endStage = "5";
        //设置相邻图片相似度为0.994
        LaunchSpeed.setThreshold("0.994");
        // 设置视频分析模式 1：训练集模式 2：图片标记模式 3：训练模型模式
        LaunchSpeed.setAnalyzeType(3);
        // 设置训练模型路径
        LaunchSpeed.setModelFile(modelFile);
        // 设置开始帧属于哪个阶段，最终会取该阶段的最后一帧作为开始帧
        LaunchSpeed.setStartStage(startStage);
        // 设置结束帧属于哪个阶段，最终会取该阶段的第一帧作为结束帧
        LaunchSpeed.setEndStage(endStage);
        // 设置平均耗时计算方式 0：去掉最大最小值后计算平均值 1：直接计算平均值，默认0
        LaunchSpeed.setAverageType(0);


        // 进行5次测试
        for (int i=0; i<10; i++) {
            PhenixLog.info(String.format("第%d次",i+1));
            PhenixLog.info("--------等待2s------");
            Common.wait(phone,2);
            // 开始录制视频，并检查录制结果
            boolean record = LaunchSpeed.startRecord(phone, pkgName);
            if (!record) return false;
            // 启动软件，这里采用phenix已有接口，也可以自定义启动方式，如点击icon
            //boolean startApp = Common.startApplication(phone, pkgName);
            //根据text找到icon.进行点击
            Widget.clickByText(phone,"游戏中心", ClickType.NORMAL);
            // 等待进入稳定阶段（根据每个APP到达稳定的时间决定是否需要等待），等待5s
            Common.wait(phone,6);
            // 结束视频录制（接口内部会进行视频分析与单次测试报告生成）
            boolean stop = LaunchSpeed.stopRecord(phone);
            if (!stop) return false;
            // 【可不调用】获取单次耗时，用户获取后可自行处理
            System.err.println(LaunchSpeed.getCostTime());
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
        String pkgName = "com.nearme.gamecenter";
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    public static void main(String[] args) {
        new LaunchSpeedTest().run(args);
    }
}
