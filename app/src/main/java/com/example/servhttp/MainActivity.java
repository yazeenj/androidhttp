package com.example.servhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_get,btn_post,btn_put,btn_delete;
    private TextView txt_result;
    private ListView list_users;
    private ImageView img_avatar;
    private ArrayAdapter adp;

    private ArrayList<userModal> users_data;

    private final static String SERVER_URL = "https://reqres.in/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_get = findViewById(R.id.btn_get);
        btn_post = findViewById(R.id.btn_post);
        btn_put = findViewById(R.id.btn_put);
        btn_delete = findViewById(R.id.btn_delete);
        txt_result = findViewById(R.id.txt_result);
        list_users= findViewById(R.id.list_users);
        img_avatar = findViewById(R.id.avatar_view);
        users_data = new ArrayList();

        updateViews();

        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
        btn_put.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        
        list_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, "Email: " + users_data.get(position).getAvatar(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void updateViews() {
        adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,users_data);
        list_users.setAdapter(adp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_get:

                JsonObjectRequest myGetReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL + "users?page=2", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //txt_result.setText(response.toString());
                        try {
                            JSONArray dataObject = response.getJSONArray("data");
                            for(int i=0; i < dataObject.length();i++){
                                JSONObject userObject = dataObject.getJSONObject(i);

                                userModal tempUser = new userModal(userObject.getInt("id"),
                                        userObject.getString("email"),
                                        userObject.getString("first_name"),
                                        userObject.getString("last_name"),
                                        userObject.getString("avatar"));


                                users_data.add(tempUser);

                                Picasso.get().load(userObject.getString("avatar")).into(img_avatar);
                                updateViews();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

                VolleyNetwork.getInstance(this.getApplicationContext()).addToRequestQueue(myGetReq);

                break;
            case R.id.btn_post:
                Toast.makeText(this, "POST", Toast.LENGTH_SHORT).show();

                JSONObject postData = new JSONObject();

                try {
                    postData.put("name","Yazeen");
                    postData.put("course", "EC");
                    postData.put("id","123");

                    JsonObjectRequest myPostReq = new JsonObjectRequest(Request.Method.POST, SERVER_URL + "users", postData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            txt_result.setText(response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                    VolleyNetwork.getInstance(this.getApplicationContext()).addToRequestQueue(myPostReq);

                } catch (JSONException e){
                    e.printStackTrace();
                }

                break;
            case R.id.btn_put:
                Toast.makeText(this, "PUT", Toast.LENGTH_SHORT).show();

                JSONObject putData = new JSONObject();

                try {
                    putData.put("name", "johan");
                    putData.put("course", "Android");

                    JsonObjectRequest myPutReq = new JsonObjectRequest(Request.Method.PUT, SERVER_URL + "users/2", putData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            txt_result.setText(response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                    VolleyNetwork.getInstance(this.getApplicationContext()).addToRequestQueue(myPutReq);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                break;
            case R.id.btn_delete:
                Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();

                JsonObjectRequest myDeleteReq = new JsonObjectRequest(Request.Method.DELETE, SERVER_URL + "users/2", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        txt_result.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txt_result.setText(error.toString());
                        error.printStackTrace();
                    }
                });

                VolleyNetwork.getInstance(this.getApplicationContext()).addToRequestQueue(myDeleteReq);
                break;
        }
    }
}
