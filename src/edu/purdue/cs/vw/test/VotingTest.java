package edu.purdue.cs.vw.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.purdue.cs.vw.Voting;

public class VotingTest extends ActivityInstrumentationTestCase2<Voting> {
    private Voting activity;
    private ListAdapter adapter;

    private static final String PREFIX = "Number of votes: ";

    public VotingTest() {
	super(Voting.class);
	Log.d("VotingTest", "call to constructor");
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	Log.d("VotingTest", "call to setup");

	setActivityInitialTouchMode(false);
	activity = this.getActivity();
    }

    public void testMockServer() {
	MockServer server = new MockServer();
	activity.setServer(server);
	
	Log.d("VotingTest", "testMockServer start");

	String[] channels = server.getChannels();

	activity.waitForData();
	adapter = activity.getListAdapter();
	assertEquals(server.getNumChannels(), adapter.getCount());
	doVoting(channels);
    }

    public void testRealServer() {
	String[] channels = { "ABC", "CNN", "ESPN", "Nickelodeon", "Comedy Central", "Sci Fi", "Weather Channel",
		"National Geographic" };
	java.util.Arrays.sort(channels);

	activity.waitForData();
	adapter = activity.getListAdapter();
	assertEquals(channels.length, adapter.getCount());
	doVoting(channels);
    }

    private void doVoting(String[] channels) {
	Log.d("VotingTest", "in testVote");
	ListView view = activity.getListView();
	adapter = activity.getListAdapter();

	activity.waitForData();
	assertEquals(channels.length, adapter.getCount());
	this.getInstrumentation().waitForIdleSync();

	// Attempt to ensure the view is updated by scrolling to the bottom
	// and back to the top.
	TouchUtils.scrollToBottom(this, activity, view);
	this.getInstrumentation().waitForIdleSync();
	TouchUtils.scrollToTop(this, activity, view);
	this.getInstrumentation().waitForIdleSync();

	// Vote on each visible channel.
	for (int i = 0; i < view.getChildCount(); i++) {
	    LinearLayout llListItem;
	    int countBefore, countAfter;

	    llListItem = getListItemAndCheckChannel(view, i, channels);
	    countBefore = getVoteCount(llListItem);

	    TouchUtils.clickView(this, llListItem);
	    this.getInstrumentation().waitForIdleSync();
	    activity.waitForData();

	    llListItem = getListItemAndCheckChannel(view, i, channels);
	    countAfter = getVoteCount(llListItem);
	    assertEquals(countBefore + 1, countAfter);
	}
    }

    private int getVoteCount(LinearLayout llListItem) {
	TextView tv = (TextView) llListItem.findViewById(edu.purdue.cs.vw.R.id.detail);
	String detail = tv.getText().toString();
	assertTrue(detail.matches(PREFIX + "[0-9]+"));
	return Integer.parseInt(detail.substring(PREFIX.length()));
    }

    private LinearLayout getListItemAndCheckChannel(ListView view, int i, String[] channels) {
	LinearLayout ll = (LinearLayout) view.getChildAt(i);
	TextView tv = (TextView) ll.findViewById(edu.purdue.cs.vw.R.id.title);
	String channel = tv.getText().toString();
	assertTrue(i < channels.length);
	assertEquals(channels[i], channel);
	return ll;
    }
}
