package com.example.ac_twitterclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
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
    private ArrayList<String> arrayUsers;
    private ArrayList<HashMap<String,String>> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        buttonSendTweet = findViewById(R.id.buttonSendTweet);
        buttonViewOthers = findViewById(R.id.buttonViewOthers);

        buttonSendTweet.setOnClickListener(this);
        buttonViewOthers.setOnClickListener(this);

        arrayUsers = new ArrayList();

        listView = findViewById(R.id.ListViewTweets);





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
                try {
                    list = new ArrayList<>();
                    final SimpleAdapter sa = new SimpleAdapter(SendTweet.this, list,
                            R.layout.twolines,
                            new String[]{"line1", "line2"},
                            new int[]{R.id.line_a, R.id.line_b});

                    ParseQuery<ParseObject> parseQueryMyTweet = ParseQuery.getQuery("MyTweet");
                    parseQueryMyTweet.whereContainedIn("user", ParseUser.getCurrentUser().getList("fanOf"));
                    parseQueryMyTweet.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (objects.size() > 0 && e == null) {

                                for (ParseObject tweet : objects) {
                                    HashMap<String, String> item = new HashMap<>();
                                    item.put("line1", tweet.getString("user"));
                                    item.put("line2", tweet.getString("tweet"));
                                    list.add(item);
                                }

                                listView.setAdapter(sa);
                            }

                        }
                    });

                }catch (Exception e){
                   e.printStackTrace();
                }
//                SimpleAdapter sa;
                break;
        }
    }
}

