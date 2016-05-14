package com.murraystudio.touchfortwitter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.List;

/**
 * Created by sushi_000 on 5/13/2016.
 */
public class Timeline extends Fragment {

    private View view;

    private TwitterSession session;

    private TextView tweetEx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.timeline, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Timeline");

        tweetEx = (TextView) view.findViewById(R.id.tweet1);

        session = Twitter.getSessionManager().getActiveSession();

        if(session != null){
            setUpViewsForTweetComposer(); //example tweet
        }



        return view;
    }

    private void setUpViewsForTweetComposer() {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
        StatusesService statusesService = twitterApiClient.getStatusesService();
        statusesService.show(723978692242722816L, null, null, null, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                Log.d("result: ", result.toString());
                //Toast.makeText(getContext(), result.data.text, Toast.LENGTH_LONG).show();

                //tweetEx.setText(result.data.text);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("fail result: ", exception.toString());
            }
        });

        statusesService.homeTimeline(5, null, null, null, false, false, false, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                for(int i = 0; i < result.data.size(); i++) {
                    String msg = tweetEx.getText().toString();
                    tweetEx.setText(msg + result.data.get(i).text + '\n');
                }
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }
}
