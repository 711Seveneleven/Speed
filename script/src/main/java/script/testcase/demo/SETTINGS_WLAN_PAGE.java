package script.testcase.demo;

import aw.phenix.common.Common;
import aw.phenix.constant.ClickType;
import aw.phenix.widget.Button;
import aw.phenix.widget.TextView;
import script.bl.demo.SettingsBase;
import script.model.demo.Settings;

import static script.model.demo.Settings.YES;

/*************************************************************************
 * @ 项目名称 : 设置
 * @ 用例 ID:   SETTINGS_WLAN_PAGE
 * @ 编写人 :   周波 80214027
 * @ 创建时间 : 2019-05-14
 * @ 修改时间 : 2019-05-14
 * @ 修改说明 : 无
 * @ 脚本描述 : 打开WIFI，WLAN界面打开成功
 * @ 前置条件 :
 *      1. 返回桌面；
 *      2. 打开设置。
 * @ 测试步骤 :
 *      1. 进入WLAN页面。
 * @ 预期结果 : 成功进入WLAN页面
 ************************************************************************/
public class SETTINGS_WLAN_PAGE extends SettingsBase {

    @Override
    protected boolean test() throws Exception {
        logger.info("步骤1：点击WLAN");
        assertTrue(TextView.click(TOPO.phoneDUT, Settings.WLAN.TITLE, ClickType.NORMAL));

        logger.info("步骤2：等待3秒");
        Common.wait(TOPO.phoneDUT, Settings.SECOND * 3);

        logger.info("检查点：检查页面是否打开");
        return Button.checkIfWidgetExist(TOPO.phoneDUT, Settings.WLAN.SCAN, YES);
    }

    public static void main(String[] args) {
        new SETTINGS_WLAN_PAGE().run(args);
    }
}
