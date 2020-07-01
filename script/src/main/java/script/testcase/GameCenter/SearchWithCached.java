package script.testcase.GameCenter;

import aw.phenix.base.Phone;
import aw.phenix.common.Common;
import aw.phenix.constant.ClickType;
import aw.phenix.monitor.Bean.IgnoreAreasBean;
import aw.phenix.monitor.PageSpeed;
import aw.phenix.widget.Widget;
import oppo.autotest.phenix.log.PhenixLog;
import topo.toplogies.TopoAndroid;

import java.util.ArrayList;
import java.util.List;
/*
搜索有缓存（手动链接/断开网络）
 */

public class SearchWithCached extends TopoAndroid {

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

        //先把app启动放在后台
        Widget.clickByText(phone,"游戏中心", ClickType.NORMAL);
        Common.wait(phone,6);
        //new Widget().id("com.nearme.gamecenter:id/normalLable").text("排行");
        Widget.clickByText(phone,"排行",ClickType.NORMAL);
        //Common.wait(phone,10);


        // 定义工程路径，用于获取依赖的工具
        String mtnPath = "E:\\MTN";
        PageSpeed.setMtnPath(mtnPath);
        // 方案三：获取已有模型
        String modelFile="E:\\MTN\\tools\\timemachine-project\\videos\\Search.h5";
        // 开始阶段设置：开始帧属于第几个非稳定阶段，若从桌面图标启动，设置为0即可。详解：第0个非稳定阶段--icon被点击后变暗瞬间
        String startStage = "0";
        // 设置结束帧属于哪个阶段，最终会取该阶段的第一帧作为结束帧
        String endStage = "3";
        // 设置相邻图片相似度（默认0.98）
        //  参数设置说明：
        //      1.启动过程存在渐变画面的建议设置为0.999，如：游戏中心轮播图
        //      2.启动过程不存在渐变画面的建议设置为0.98，如：拨号盘
        PageSpeed.setThreshold("0.999");
        PageSpeed.setModelFile(modelFile);
        PageSpeed.setStartStage(startStage);
        PageSpeed.setEndStage(endStage);
        // IgnoreAreasBean参数分别为：忽略区域的左上方点坐标x/y  忽略区域的width/height
        //      两组参数取值说明：
        //          比例方式：new IgnoreAreasBean(0,0,1,0.25)
        //          指定数字：new IgnoreAreasBean(100,100,200,300)
        //      x/y采用相同方式取值；width/height采用相同方式取值
        IgnoreAreasBean igA1 = new IgnoreAreasBean(0,0,1,0.10);
        //IgnoreAreasBean igA2 = new IgnoreAreasBean(0,0,0.25,1);
        List<IgnoreAreasBean> ignoreAreasList = new ArrayList<>();
        ignoreAreasList.add(igA1);
        //ignoreAreasList.add(igA2);
        PageSpeed.setIgnoreAreasList(ignoreAreasList);
        // 页面切换/加载速度不需要获取oapm数据
        PageSpeed.setRequestOapmData(false);


        // 进行10次测试
        for (int i=0; i<10; i++) {
            PhenixLog.info(String.format("第%d次",i+1));
            PhenixLog.info("--------等待2s------");
            Common.wait(phone,2);
            // 开始录制视频，并检查录制结果
            boolean record = PageSpeed.startRecord(phone, pkgName);
            if (!record) return false;
            // 启动软件，这里采用phenix已有接口，也可以自定义启动方式，如点击icon
            //boolean startApp = Common.startApplication(phone, pkgName);
            //根据text找到icon，进行点击
            Widget.clickById(phone,"com.nearme.gamecenter:id/tv_recommend_key",ClickType.NORMAL);
            // 等待进入稳定阶段（根据每个APP到达稳定的时间决定是否需要等待），等待5s
            Common.wait(phone,5);
            // 结束视频录制（接口内部会进行视频分析与单次测试报告生成）
            boolean stop = PageSpeed.stopRecord(phone);
            if (!stop) return false;
            // 【可不调用】获取单次耗时，用户获取后可自行处理
            System.err.println(PageSpeed.getCostTime());
            //退出软件（ 保留在后台）
            Widget.clickById(phone,"com.nearme.gamecenter:id/iv_actionbar_back_icon",ClickType.NORMAL);
            Common.wait(phone,1);
        }
        // 【可不调用】获取总报告数据，用户获取后可自行处理
        System.err.println(PageSpeed.getTotalReportData());
        // 生成总报告
        PageSpeed.generateTotalReport();

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
        new SearchWithCached().run(args);
    }
}
