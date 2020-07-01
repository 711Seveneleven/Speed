package script.testcase.demo;

import script.bl.demo.PerfDemoBase;

/*************************************************************************
 * @ 项目名称 : 性能测试DEMO
 * @ 用例 ID:   PERF_DEMO_001
 * @ 编写人 :   周波 80213472
 * @ 创建时间 : 2019-06-02
 * @ 修改时间 : 2019-06-02
 * @ 修改说明 : 无
 * @ 脚本描述 : DEMO性能测试
 * @ 前置条件 :
 *      1. 返回桌面
 *      2. 打开性能测试DEMO
 * @ 测试步骤 :
 *      1. 点击测试卡顿
 *      2. 点击测试内存泄漏
 *      3. 点击测试ANR
 *      4. 点击测试CRASH
 * @ 预期结果 : 获取到性能数据
 *************************************************************************/
public class PERF_DEMO_001 extends PerfDemoBase {
    @Override
    protected boolean test() throws Exception {
        return testPerf(TOPO.phoneDUT, 3);
    }

    public static void main(String[] args) {
        new PERF_DEMO_001().run(args);
    }
}
