package edu.purdue.cs.vw.test;

import java.util.ArrayList;

import android.util.Log;
import edu.purdue.cs.vw.Server;

public class MockServer extends Server {
    private String[] channels = { "ABC", "CNN", "ESPN", "Nickelodeon", "Comedy Central", "Sci Fi", "Weather Channel",
    "National Geographic" };

    MockServer() {
    }

    @Override
    public void vote(String name) {
	Log.d("ServerTest", "vote " + name);
    }

    @Override
    public ArrayList<String> getList() {
	Log.d("ServerTest", "get");
	ArrayList<String> voteList = new ArrayList<String>();
	for (int i = 0; i < channels.length; i++) 
	    voteList.add(channels[i]);
	return voteList;
    }

    @Override
    public ArrayList<String> getCount() {
	Log.d("ServerTest", "getCount");
	ArrayList<String> votes = new ArrayList<String>();
	for (int i = 0; i < channels.length; i++)
	    votes.add("0");
	return votes;
    }

    @Override
    public void waitForData() {
	// TODO Auto-generated method stub
	return;
    }

}
