package edu.purdue.cs.vw.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.purdue.cs.vw.Server;
import edu.purdue.cs.vw.Voting;

public class VotingTest extends ActivityInstrumentationTestCase2<Voting> {
    private Voting activity;
    private ListView view;
    private ListAdapter adapter;
    private Server server;

    private static final String PREFIX = "Number of votes: ";
    private String[] channels = { "ABC", "CNN", "ESPN", "Nickelodeon", "Comedy Central", "Sci Fi", "Weather Channel",
	    "National Geographic" };

    public VotingTest() {
	super(Voting.class);
	Log.d("VotingTest", "call to constructor");
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	Log.d("VotingTest", "call to setup");

	java.util.Arrays.sort(channels);

	setActivityInitialTouchMode(false);
	activity = this.getActivity();
    }

    public void testMockServer() {
	server = new MockServer();
	assertTrue(true);
    }

    public void testPreconditions() {
	adapter = activity.getListAdapter();

	// TODO: HACK!! Ensures that any server delays or pop-up toast delays don't prevent
	// this code from completing ahead of the client, causing a failure at onPause() time.
	try {
	    Thread.sleep(500);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	server = activity.getServer();
	server.waitForData();
	assertEquals(channels.length, adapter.getCount());
    }

    public void testVote() {
	server = activity.getServer();
	view = activity.getListView();
	adapter = activity.getListAdapter();

	// Vote on each available channel.

	server.waitForData();
	for (int i = 0; i < view.getCount(); i++) {
	    LinearLayout llListItem;
	    int countBefore, countAfter;

	    llListItem = getListItemAndCheckChannel(i);
	    countBefore = getVoteCount(llListItem);

	    TouchUtils.clickView(this, llListItem);

	    // TODO: HACK!! Ensures that any server delays or pop-up toast delays don't prevent
	    // this code from completing ahead of the client, causing a failure at onPause() time.
	    try {
		Thread.sleep(500);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	    llListItem = getListItemAndCheckChannel(i);
	    countAfter = getVoteCount(llListItem);
	    assertEquals(countBefore + 1, countAfter);
	}

	// TODO: OK to remove. Use if keyboard navigation works and can be tested.
	// this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
    }

    private int getVoteCount(LinearLayout llListItem) {
	TextView tv = (TextView) llListItem.findViewById(edu.purdue.cs.vw.R.id.detail);
	String detail = tv.getText().toString();
	assertTrue(detail.matches(PREFIX + "[0-9]+"));
	return Integer.parseInt(detail.substring(PREFIX.length()));
    }

    private LinearLayout getListItemAndCheckChannel(int i) {
	Log.d("VotingTest", "list item " + i + "; channel " + channels[i]);
	LinearLayout ll = (LinearLayout) view.getChildAt(i);
	TextView tv = (TextView) ll.findViewById(edu.purdue.cs.vw.R.id.title);
	String channel = tv.getText().toString();
	assertTrue(i < channels.length);
	assertEquals(channels[i], channel);
	return ll;
    }
}
