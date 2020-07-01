//package script.testcase.demo;
//
//import aw.phenix.base.Phone;
//import aw.phenix.common.Common;
//import aw.phenix.monitor.LaunchSpeed;
//import oppo.autotest.phenix.log.PhenixLog;
//import topo.toplogies.TopoAndroid;
//
///**
// * @ClassName LaunchSpeedTest
// * @Description TODO
// * @Author: wangliao 80260910
// * @Date: 2020/1/20 10:18
// */
//public class LaunchSpeedTest extends TopoAndroid {
//
//    @Override
//    protected boolean setUp() throws Exception {
//        return true;
//    }
//
//    @Override
//    protected boolean test() throws Exception {
//        String pkgName = "com.oapm.sample";
//        Phone phone = TOPO.phoneDUT;
//
//        // 定义工程路径，用于获取依赖的工具
//        String mtnPath = "D:\\WORK\\localEA\\release\\LocalPerfTestTool_1.5.1_20200220\\LocalPerfTestTool_1.5.1_20200220";
//        LaunchSpeed.setMtnPath(mtnPath);
//
//        // 方案一：通过 手动监控的已有训练集 获取启动速度
//        String trainDataHome = "F:\\tmp_files\\t";
//        String startStage = "0";
//        String endStage = "3";
//        // 设置视频分析模式 1：训练集模式 2：图片标记模式 3：神经网络模式
//        LaunchSpeed.setAnalyzeType(1);
//        // 设置训练集路径
////        LaunchSpeed.setTrainDataHome(trainDataHome);
//        // 设置开始帧属于哪个阶段，最终会取该阶段的最后一帧作为开始帧
//        LaunchSpeed.setStartStage(startStage);
//        // 设置结束帧属于哪个阶段，最终会取该阶段的第一帧作为结束帧
//        LaunchSpeed.setEndStage(endStage);
//        // 设置平均耗时计算方式 0：去掉最大最小值后计算平均值 1：直接计算平均值，默认0
//        LaunchSpeed.setAverageType(0);
//
//
//        // 进行5次测试
//        for (int i=0; i<5; i++) {
//            PhenixLog.info(String.format("第%d次",i+1));
////            PhenixLog.info("5s后开始。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
////            Common.wait(phone,5);
//            // 开始录制视频，并检查录制结果
//            boolean record = LaunchSpeed.startRecord(phone, pkgName);
//            if (!record) return false;
//            // 启动软件，这里采用phenix已有接口，也可以自定义启动方式，如点击icon
//            boolean startApp = Common.startApplication(phone, pkgName);
//            if (!startApp) return false;
//            // 等待进入稳定阶段（根据每个APP到达稳定的时间决定是否需要等待）
//            Common.wait(phone,1);
//            // 结束视频录制（接口内部会进行视频分析与单次测试报告生成）
//            boolean stop = LaunchSpeed.stopRecord(phone);
//            if (!stop) return false;
//            // 【可不调用】获取单次耗时，用户获取后可自行处理
//            System.err.println(LaunchSpeed.getCostTime());
//            // 退出软件（这里采用phenix已有接口，也可以根据场景需要自定义退出方式，如back退出）
//            Common.forceStopApplication(phone, pkgName);
//        }
//        // 【可不调用】获取总报告数据，用户获取后可自行处理
//        System.err.println(LaunchSpeed.getTotalReportData());
//        // 生成总报告
//        LaunchSpeed.generateTotalReport();
//
//        return true;
//
//
//
//
//
//
//
//    }
//
//    @Override
//    protected boolean tearDown() throws Exception {
//        Phone phone = TOPO.phoneDUT;
//        String pkgName = "com.oapm.sample";
//        Common.forceStopApplication(phone, pkgName);
//        return true;
//    }
//
//    public static void main(String[] args) {
//        new LaunchSpeedTest().run(args);
//    }
//}
