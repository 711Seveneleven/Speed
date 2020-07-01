package script.model.demo;

import aw.phenix.widget.Button;
import aw.phenix.widget.Widget;

public class PerfDemo {
    public static String PACKAGE_NAME = "com.oppotest.perf_sample";
    public static Widget LEAK_TEST = new Button("com.oppotest.perf_sample:id/async_work");
    public static Widget ANR_TEST = new Button("com.oppotest.perf_sample:id/main_thread_waiting");
    public static Widget BLOCK_TEST = new Button("com.oppotest.perf_sample:id/io_waiting");
    public static Widget CRASH_TEST = new Button("com.oppotest.perf_sample:id/test");
    public static Integer ROTATION_0 = 0;
    public static Integer ROTATION_90 = 1;
    public static Integer ROTATION_180 = 2;
    public static Integer ROTATION_270 = 3;
}
