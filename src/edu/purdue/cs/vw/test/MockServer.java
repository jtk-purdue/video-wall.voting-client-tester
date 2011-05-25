package edu.purdue.cs.vw.test;

import java.util.ArrayList;

import android.util.Log;
import edu.purdue.cs.vw.Server;

public class MockServer extends Server {
    private static final int NUM_CHANNELS = 100;
    ArrayList<String> voteList;
    ArrayList<String> votes;
    String[] channels;

    MockServer() {
	channels = new String[NUM_CHANNELS];
	voteList = new ArrayList<String>();
	votes = new ArrayList<String>();

	for (int i = 0; i < NUM_CHANNELS; i++) {
	    String channel = "Channel " + i;
	    channels[i] = channel;
	    voteList.add(channel);
	    votes.add("0");
	}
    }

    public String[] getChannels() {
	return channels;
    }

    public int getNumChannels() {
	return NUM_CHANNELS;
    }

    @Override
    public void vote(String name) {
	Log.d("MockServerTest", "vote " + name);
	for (int i = 0; i < channels.length; i++)
	    if (channels[i].equals(name)) {
		votes.set(i, String.valueOf(Integer.parseInt(votes.get(i)) + 1));
		break;
	    }
    }

    @Override
    public ArrayList<String> getList() {
	Log.d("MockServerTest", "get");
	return voteList;
    }

    @Override
    public ArrayList<String> getCount() {
	Log.d("MockServerTest", "getCount");
	return votes;
    }
}
