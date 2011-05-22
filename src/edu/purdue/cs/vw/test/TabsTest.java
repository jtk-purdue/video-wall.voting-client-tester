package edu.purdue.cs.vw.test;

import edu.purdue.cs.vw.Tabs;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabWidget;

public class TabsTest extends ActivityInstrumentationTestCase2<Tabs> {
    private static final String MT = "TabsTest";
    private Tabs activity;
    private TabHost tabHost;
    private TabWidget tabWidget;

    public TabsTest() {
	super(Tabs.class);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();

	setActivityInitialTouchMode(false);
	activity = getActivity();
	tabHost = activity.getTabHost();
	tabWidget = tabHost.getTabWidget();
    }

    public void testPreconditions() {
	assertNotNull(activity);
	assertNotNull(tabHost);
	assertNotNull(tabWidget);
    }

    public void testTabs() {
	Log.d(MT, "testTabs, count = " + tabWidget.getTabCount());
	for (int i = 0; i < tabWidget.getTabCount(); i++)
	    TouchUtils.clickView(this, tabWidget.getChildTabViewAt(i));
    }
}
