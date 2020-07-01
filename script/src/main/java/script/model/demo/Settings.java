package script.model.demo;

import aw.phenix.widget.Button;
import aw.phenix.widget.TextView;
import aw.phenix.widget.Widget;

public class Settings {
    public static final String PACKAGE_NAME = "com.android.settings";
    public static final Integer GO_HOME_BACK_TIMES = 3;
    public static final Integer SECOND = 1;
    public static final Integer MONITOR_INERVAL = 1; //监控时间间隔1s
    public static final boolean YES = true;
    public static final boolean NO = true;
    public static class WLAN{
        public static final Widget TITLE = new TextView("android:id/title").text("WLAN");
        public static final Widget SCAN = new Button().desc("扫描");
    }
}
