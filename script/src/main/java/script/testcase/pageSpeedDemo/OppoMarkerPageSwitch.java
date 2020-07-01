package script.testcase.pageSpeedDemo;

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

/**
 * @ClassName OppoMarkerPageSwitch
 * @Description TODO
 * @Author: wangliao 80260910
 * @Date: 2020/6/4 16:20
 */
public class OppoMarkerPageSwitch extends TopoAndroid {
    Phone phone;
    String pkgName;

    @Override
    protected boolean setUp() throws Exception {
        phone = TOPO.phoneDUT;
        pkgName = "com.oppo.market";
        return true;
    }

    @Override
    protected boolean test() throws Exception {
        // 定义工程路径，用于获取依赖的工具
        String mtnPath = "E:\\my_workspace\\idea_workspace\\localDebugTools_gitlab\\LocalDebugTools\\execute-agent";
        PageSpeed.setMtnPath(mtnPath);

        // 训练模型路径
        String modelFile = "D:\\WORK\\localEA\\release\\LocalPerfTestTool_1.5.2_20200306\\LocalPerfTestTool_1.5.2_20200306\\tools\\timemachine-project\\videos\\gameCenterPage.h5";
        // 开始阶段设置：开始帧属于第几个非稳定阶段，若从桌面图标启动，设置为0即可。详解：第0个非稳定阶段--icon被点击后变暗瞬间
        String startStage = "0";
        // 设置结束帧属于哪个阶段，最终会取该阶段的第一帧作为结束帧
        String endStage = "3";
        // 设置相邻图片相似度（默认0.98）
        //  参数设置说明：
        //      1.启动过程存在渐变画面的建议设置为0.999，如：短视频（预览画面为渐变加载），软件商店（轮播图为渐变加载）
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
        IgnoreAreasBean igA1 = new IgnoreAreasBean(0,0,1,0.25);
        IgnoreAreasBean igA2 = new IgnoreAreasBean(0,0,0.25,1);
        List<IgnoreAreasBean> ignoreAreasList = new ArrayList<>();
        ignoreAreasList.add(igA1);
        ignoreAreasList.add(igA2);
        PageSpeed.setIgnoreAreasList(ignoreAreasList);
        // 页面切换/加载速度不需要获取oapm数据
        PageSpeed.setRequestOapmData(false);


        // 进行i次测试
        for (int i=0; i<1; i++) {
            PhenixLog.info(String.format("第%d次",i+1));
            //打开软件商店
            Common.startApplication(phone, pkgName);
            Common.wait(phone,10);
            // 开始录制视频
            boolean record = PageSpeed.startRecord(phone, pkgName);
            if (!record) return false;
            // 启动软件，这里采用phenix已有接口，也可以自定义启动方式，如点击icon
            Widget.clickByText(phone, "游戏", ClickType.NORMAL);
            // 等待进入稳定阶段（根据每个页面到达稳定的时间调整等待时间）
            Common.wait(phone,4);
            // 结束视频录制（接口内部会进行视频分析与单次测试报告生成）
            boolean stop = PageSpeed.stopRecord(phone);
            if (!stop) {
                PhenixLog.error("stop出错了");
            }
            // 退出软件（这里采用phenix已有接口，也可以根据场景需要自定义退出方式，如back退出）
            Common.forceStopApplication(phone, pkgName);
            Common.wait(phone,5);
        }
        // 生成总报告
        PageSpeed.generateTotalReport();
        return true;
    }

    @Override
    protected boolean tearDown() throws Exception {
        Common.forceStopApplication(phone, pkgName);
        return true;
    }

    public static void main(String[] args) {
        new OppoMarkerPageSwitch().run(args);
    }
}
