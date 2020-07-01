/**
 * Description:
 * File Name: Runner
 * Author: libo-a@oppo.com
 * Date: 2019-04-16
 * Time: 19:05
 */

package script;

import com.oppo.phenix.framework.utils.TimeUtil;
import oppo.autotest.phenix.PhenixPhones;
import oppo.autotest.phenix.listeners.ITaskListener;
import oppo.autotest.phenix.log.PhenixLog;
import oppo.autotest.phenix.runner.CaseResult;
import oppo.autotest.phenix.runner.ProjectConfig;
import org.dom4j.Element;
import topo.toplogies.TopoBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: Runner
 * @Description:
 * @Author: libo-a@oppo.com
 * @Date: 2019/4/16 19:05
 */
public class Runner {
    private static final String JAVA = ".java";
    private List<TopoBase> testcases = new ArrayList<TopoBase>();
    private String serial = null;
    private String failSend = "false";
    private String emailAddress = null;
    private String reportPath = null;

    private static List<String> LISTENERS = Arrays.asList("oppo.autotest.phenix.listeners.TaskReportListener");
    private List<ITaskListener> listeners = new ArrayList<ITaskListener>();

    public void exec(String[] args) throws Throwable {
        //等同命令行运行，Debug调试（当PhenixRunner运行时出错）
        /*args = new String[8];
        args[0] = "-tc";
        args[1] = "C:\\Users\\g\\Desktop\\RunningProject\\PhenixRunningProject\\src\\com\\oppo\\globalsearch50\\basetest\\Contact_CheckUI.java";
        args[2] = "-aw";
        args[3] = "C:\\Users\\g\\Desktop\\RunningProject\\PhenixTestAW_ColorOS5.0\\PhenixTestAW_ColorOS5.0";
        args[4] = "-serial";
        args[5] = "f326aac4";
        args[4] = "-email";
        args[5] = "xxxxxxxxxxxxxxxx@oppo.com";
        args[6] = "-failsend";
        args[7] = "false";
        args[8] = "-reportPath";
        args[9] = "D:\phenix_report\0003";
        // jdk的地址
        System.setProperty("java.home", "C:\\Program Files\\Java\\jdk1.8.0_152");
        compiler = ToolProvider.getSystemJavaCompiler();
        // jre的地址
        System.setProperty("java.home", "C:\\Program Files\\Java\\jdk1.8.0_152\\jre");*/
        PhenixLog.info("Start run task");
        String[] t_args = this.parseOption(args);

        this.initListener();
        Date startTime = new Date();

        String reportDir;
        if (null != reportPath) {
            reportDir = reportPath;
        } else {
            reportDir = System.getProperty("user.dir") + File.separator + "report" + File.separator
                    + TimeUtil.dateFormat(startTime, "yyyyMMddHHmmssSSS");
        }

        PhenixPhones.getInstance(t_args).setReportDir(reportDir);
        for (ITaskListener listener : this.listeners) {
            listener.taskStarted(startTime);
        }
        this.runTestCases(t_args, testcases);
        for (ITaskListener listener : this.listeners) {
            listener.taskEnded(this.emailAddress, this.failSend);
        }

        PhenixLog.info("Report directory: " + reportDir);
    }

    /**
     * 运行用例列表
     *
     * @param t_args
     * @param cases
     * @throws MalformedURLException
     */
    private void runTestCases(String[] t_args, List<TopoBase> cases) throws MalformedURLException {
        for (TopoBase testcase : cases) {
            for (ITaskListener listener : this.listeners) {
                listener.caseStarted(testcase.getClass().getName());
            }

            CaseResult result = this.runTestCase(t_args, testcase);
            for (ITaskListener listener : this.listeners) {
                listener.caseEnded(result);
            }
        }
    }

    /**
     * 运行单个用例
     *
     * @param args
     * @param caseClass
     * @return 用例运行结果
     */
    private CaseResult runTestCase(String[] args, TopoBase caseClass) {
        if (this.serial != null) {
            args = new String[]{"serial", this.serial, Integer.toString((int) (Math.random() * 20000)), "0"};
        }
        caseClass.run(true, args);
        return caseClass.caseResult;
    }

    /**
     * 任务监听初始化
     */
    private void initListener() {
        Element element = ProjectConfig.getInstance().getSettings("tasklisteners");
        List<String> taskListeners = new ArrayList<String>();
        if (null == element) {
            taskListeners.addAll(LISTENERS);
        } else {
            @SuppressWarnings("unchecked")
            List<Element> listener = element.elements("listener");
            if (listener.size() == 0) {
                taskListeners.addAll(LISTENERS);
            } else {
                for (Element ele : listener) {
                    taskListeners.add(ele.getStringValue().trim());
                }
            }
        }

        for (String lis : taskListeners) {
            try {
                Class<?> cls = this.getClass().getClassLoader().loadClass(lis);
                this.listeners.add((ITaskListener) cls.newInstance());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析测试用例,AW路径等参数
     *
     * @param args
     * @return
     * @throws Throwable
     */
    private String[] parseOption(String[] args) throws Throwable {
        ArrayList<String> result = new ArrayList<String>();
        String testCase = null;
        String testFile = null;
        for (int i = 0; i != args.length; i++) {
            String arg = args[i];
            if ("-tc".equals(arg)) {
                i++;
                testCase = args[i];
            } else if ("-tf".equals(arg)) {
                i++;
                testFile = args[i];
            } else if ("-aw".equals(arg)) {
                i++;
//                this.awPath = args[i];
            } else if ("-config".equals(arg)) {
                i++;
                ProjectConfig.CONFIG_PATH = args[i];
            } else if ("-email".equals(arg)) {
                i++;
                this.emailAddress = args[i];
            } else if ("-serial".equals(arg)) {
                i++;
                this.serial = args[i].split(";")[0];
                PhenixLog.info("串号只支持单设备运行，;号后面的设备不执行");
            } else if ("-failsend".equals(arg)) {
                i++;
                this.failSend = args[i];
            } else if ("-reportPath".equals(arg)) {
                i++;
                this.reportPath = args[i];
            } else {
                result.add(arg);
            }
        }

        parseTest(testCase, testFile);

        String[] strings = new String[result.size()];
        return result.toArray(strings);
    }

    private void parseTest(String testCase, String testFile) throws Throwable {
        List<String> testCaseNameList = new ArrayList<>();
        if (testCase != null) {
            testCaseNameList.addAll(Arrays.asList(testCase.split(";")));
        } else if (testFile != null) {
            File file = new File(testFile);
            if (!file.exists()) {
                throw new Throwable(String.format("Test file %s is not exits!", testFile));
            }

//            String sourcePath;
//            if (null == this.getClass().getResource(File.separator)) {
//                sourcePath = String.format("%s/src/", file.getParentFile().getAbsolutePath());
//            } else {
//                sourcePath = this.getClass().getResource(File.separator).getPath()
//                        .split("PhenixTestAW_ColorOS5.0")[0] + "PhenixRunningProject/src/";
//            }
//            PhenixLog.info("sourcePath: " + sourcePath);

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String tempString = reader.readLine();
                do {
                    testCaseNameList.add(tempString);
                } while (null != (tempString = reader.readLine()));
            } catch (Exception e) {
                PhenixLog.error(e.getMessage());
                e.printStackTrace();
                throw new Throwable("Read test file failed.");
            }
        }

        for (String testCaseName : testCaseNameList) {
            try {
                Class clazz = Class.forName(testCaseName);
                TopoBase testCaseIns = (TopoBase) clazz.newInstance();
                this.testcases.add(testCaseIns);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Throwable {
        new Runner().exec(args);
    }
}
