package com.example.ahmetanilgur.swe599_25112018_v1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mUserRecyclerView;
    private UserAdapter mUserAdapter;
    private ArrayList<SingleItemUser> mSingleItemUser;
    private RequestQueue mUserRequestQueue;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserRecyclerView = findViewById(R.id.rv_users);
        mUserRecyclerView.setHasFixedSize(true);

        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSingleItemUser = new ArrayList<>();
        mUserRequestQueue = Volley.newRequestQueue(this);
        mUserAdapter = new UserAdapter(MainActivity
                .this, mSingleItemUser);
        mUserRecyclerView.setAdapter(mUserAdapter);
        parseJSON();

    }

    private void parseJSON() {
        String userUrl = "https://599api-rnxlyxdydc.now.sh/user";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, userUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray =
                                            response
                                                    .getJSONArray("result");
                                    for (int i = 0; i <
                                            jsonArray.length();
                                         i++) {
                                        JSONObject hit =
                                                jsonArray
                                                        .getJSONObject(i);
                                        String userName = hit
                                                .getString
                                                        ("name");
                                        String userJob = hit
                                                .getString("job");
                                        int userPrice = hit
                                                .getInt("price");
                                        mSingleItemUser.add(new
                                                SingleItemUser
                                                (userName,
                                                        userJob, userPrice));
                                    }
                                    mUserAdapter = new
                                            UserAdapter
                                            (MainActivity.this
                                                    , mSingleItemUser);
                                    mUserRecyclerView
                                            .setAdapter(mUserAdapter);
                                    mUserAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        mUserRequestQueue.add(request);
    }
}

