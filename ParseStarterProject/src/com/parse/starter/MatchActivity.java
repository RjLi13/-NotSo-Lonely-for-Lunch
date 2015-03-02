package com.parse.starter;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MatchActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;
    Button findButton;
    ArrayList<ParseUser> users = new ArrayList<ParseUser>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        /*
        ParseUser.getCurrentUser().put("location", new ParseGeoPoint(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        ParseUser.getCurrentUser().saveInBackground();
        buildGoogleApiClient();
        */
        findButton = (Button) findViewById(R.id.findButton);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
                parseQuery.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> parseUsers, ParseException e) {
                        if(e == null){
                            for(ParseUser parseUser: parseUsers){
                                users.add(parseUser);
                            }
                        }else{
                            // if it doesn't work
                        }
                    }
                });

                ParseUser[] closest_users = findClosest(ParseUser.getCurrentUser(), users);
                */
                String[] closest_users = new String[3];
                closest_users[0] = "Tyler" + "\n" + "555-1234";
                closest_users[1] = "Yj" + "\n" + "555-6912";
                closest_users[2] = "Jim" + "\n" + "555-1233";

                display_demo(closest_users);
            }
        });

    }

    private void display(ParseUser[] users){
        TextView friend1 = (TextView)findViewById(R.id.friend1_textView);
        friend1.setText(users[0].getUsername()+ "/n" + users[0].get("phone"));
        TextView friend2 = (TextView)findViewById(R.id.friend2_textView);
        friend2.setText(users[1].getUsername()+ "/n" + users[1].get("phone"));
        TextView friend3 = (TextView)findViewById(R.id.friend3_textView);
        friend3.setText(users[2].getUsername()+ "/n" + users[2].get("phone"));
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public ParseUser[] findClosest(ParseUser user, ArrayList<ParseUser> query) {
        HashMap<Double, ParseUser> hash_map = new HashMap<Double, ParseUser>();
        ParseGeoPoint userloc = user.getParseGeoPoint("location");

        for (ParseUser object: query) {
            double new_distance = userloc.distanceInKilometersTo(object.getParseGeoPoint("location"));
            hash_map.put(new_distance, object);
        }


        ArrayList<Double> s = new ArrayList<Double>();
        for(double distance: hash_map.keySet()){
            s.add(distance);
        }
        Collections.sort(s);
        ParseUser[] closest = new ParseUser[3];
        for (int i = 0; i < 3; i++) {
            closest[i] = hash_map.get(s.get(i));
        }
        // returns array of 3 closest
        return closest;
    }

    public void display_demo(String[] users){
        TextView friend1 = (TextView)findViewById(R.id.friend1_textView);
        friend1.setText(users[0]);
        TextView friend2 = (TextView)findViewById(R.id.friend2_textView);
        friend2.setText(users[1]);
        TextView friend3 = (TextView)findViewById(R.id.friend3_textView);
        friend3.setText(users[2]);
    }

}
