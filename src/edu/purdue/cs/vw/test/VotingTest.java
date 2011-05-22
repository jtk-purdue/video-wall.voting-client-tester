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
    private ListView view;
    private ListAdapter adapter;

    static final String PREFIX = "Number of votes: ";
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
	activity = getActivity();
	view = activity.getListView();
	adapter = activity.getListAdapter();
    }

    public void testPreconditions() {
	assertNotNull(view);
	assertNotNull(adapter);
	assertEquals(adapter.getCount(), channels.length);
    }

    public void testVote() {
	// TODO: OK to remove this runOnUiThread if no longer needed.  Here for example only.
	Log.d("VotingTest", "PRE runOnUiThread");
	activity.runOnUiThread(new Runnable() {
	    public void run() {
		Log.d("VotingTest", "IN runOnUiThread");
	    }
	});
	Log.d("VotingTest", "POST runOnUiThread");

	// Vote on each available channel.
	
	for (int i = 0; i < view.getCount(); i++) {
	    LinearLayout llListItem;
	    int countBefore, countAfter;

	    llListItem = getListItemAndCheckChannel(i);
	    countBefore = getVoteCount(llListItem);

	    TouchUtils.clickView(this, llListItem);
	    llListItem = getListItemAndCheckChannel(i);
	    countAfter = getVoteCount(llListItem);
	    assertEquals(countAfter, countBefore + 1);
	}

	// TODO: OK to remove.  Use if keyboard navigation works and can be tested.
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
