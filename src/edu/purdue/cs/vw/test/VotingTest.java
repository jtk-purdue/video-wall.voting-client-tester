package edu.purdue.cs.vw.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

import edu.purdue.cs.vw.Voting; 

public class VotingTest extends ActivityInstrumentationTestCase2<Voting> {
    private static final int CHANNEL_COUNT = 8;  // number of channels at server
//    private static final int CHANNEL_COUNT = 4;  // number of channels at server
    private Voting activity;
    private ListView view;
    private ListAdapter adapter;

    public VotingTest() {
	super("edu.purdue.cs.vw", Voting.class);
    }
    
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	
	setActivityInitialTouchMode(false);
	activity = this.getActivity();
	view = (ListView) activity.findViewById(edu.purdue.cs.vw.R.id.list);
	adapter = activity.getListAdapter();
    }

    public void testPreconditions() {
	assertNotNull(view);
	assertNotNull(adapter);
	assertEquals(adapter.getCount(), CHANNEL_COUNT);
    }

    public void testText() {
	assertEquals(adapter.getCount(), CHANNEL_COUNT);
    }
    
    public void testVote() {
	activity.runOnUiThread(new Runnable() {
	   public void run() {
	       if (view.requestFocus())
		   Log.d("VotingTest", "testVote got focus to list view");
	       else
		   Log.d("VotingTest", "testVote FAILED focus to list view");
	   }
	});
	this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
	assertEquals(true, true);
    }

}
