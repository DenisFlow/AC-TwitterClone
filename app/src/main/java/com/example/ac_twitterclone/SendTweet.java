package com.example.ac_twitterclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendTweet extends AppCompatActivity implements View.OnClickListener{
    private Button buttonSendTweet, buttonViewOthers;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> arrayUsers, arrayMainUsers;
    private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        buttonSendTweet = findViewById(R.id.buttonSendTweet);
        buttonViewOthers = findViewById(R.id.buttonViewOthers);

        buttonSendTweet.setOnClickListener(this);
        buttonViewOthers.setOnClickListener(this);

        arrayUsers = new ArrayList();
        arrayMainUsers = new ArrayList();

        listView = findViewById(R.id.ListViewTweets);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.two_line_list_item, arrayUsers);





    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.buttonSendTweet:
                EditText editTextTweet = findViewById(R.id.editTextTweet);
                ParseObject parseObject = new ParseObject("MyTweet");
                parseObject.put("tweet", editTextTweet.getText().toString());
                parseObject.put("user", ParseUser.getCurrentUser().getUsername());
                final ProgressDialog progressDialog = new ProgressDialog(SendTweet.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(SendTweet.this, "Done!!!", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        } else {
                            FancyToast.makeText(SendTweet.this, "ERROR", Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                        progressDialog.dismiss();
                    }
                });

                break;
            case R.id.buttonViewOthers:
//                SimpleAdapter sa;

                ParseQuery<ParseUser> parseQuery = new ParseUser().getQuery();

                parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

                parseQuery.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> users, ParseException e) {
                        if (e == null){
                            if (users.size() > 0){

                                for (ParseUser user : users){
                                    arrayUsers.add(user.getUsername());
                                }

//                                listView.setAdapter(arrayAdapter);
                                for (String twitterUser : arrayUsers) {
                                    if (ParseUser.getCurrentUser().getList("fanOf") != null && ParseUser.getCurrentUser().getList("fanOf").contains(twitterUser)) {

                                        ParseQuery<ParseObject> parseQueryMyTweet = ParseQuery.getQuery("MyTweet");

                                        parseQueryMyTweet.whereEqualTo("user", twitterUser);


                                        parseQueryMyTweet.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objects, ParseException e) {
                                                HashMap<String,String> item;
                                                for (ParseObject tweet: objects) {
                                                    item = new HashMap<String,String>();
                                                    item.put( "line1", tweet.get("user").toString());
                                                    item.put( "line2", tweet.get("tweet").toString());
                                                    list.add( item );
                                                }

                                            }
                                        });



                                    }

                                }
                                if (list.size() > 0) {
                                    SimpleAdapter sa = new SimpleAdapter(SendTweet.this, list,
                                            R.layout.twolines,
                                            new String[]{"line1", "line2"},
                                            new int[]{R.id.line_a, R.id.line_b});
                                    ((ListView) findViewById(R.id.list)).setAdapter(sa);
                                }


                            }
                        }
                    }
                });


                break;
        }
    }
}

