package edu.purdue.cs.vw.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TabHost;
import android.widget.TabWidget;
import edu.purdue.cs.vw.Tabs;

public class TabsTest extends ActivityInstrumentationTestCase2<Tabs> {
    private static final String MT = "TabsTest";
    private static final int VOTE_TAB = 1;
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
	for (int i = 0; i < tabWidget.getTabCount(); i++) {
	    TouchUtils.clickView(this, tabWidget.getChildTabViewAt(i));
	}
    }
    
    public void testVotes() {
	TouchUtils.clickView(this, tabWidget.getChildTabViewAt(VOTE_TAB));
	sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
	for (int i = 0; i < 5; i++)
	    sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
	sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
    }
}
