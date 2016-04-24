package com.haptik.haptikchat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.haptik.haptikchat.adapters.ChatAdapter;
import com.haptik.haptikchat.models.ChatMessage;
import com.haptik.haptikchat.models.Member;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class GroupChat extends AppCompatActivity {

    private final String TAG = "MainActivity";
    int[] colors;
    private RecyclerView chatRecyclerView;
    private ProgressBar progressBar;
    private EditText inputMessage;
    private ImageView sendMessage;
    private LinearLayout groupName;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatMessage> messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_chat);

        initialise();
        initialiseListeners();
        getChatData();
    }

    private void initialise() {
        chatRecyclerView = (RecyclerView) findViewById(R.id.chat_rv);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        groupName = (LinearLayout) findViewById(R.id.group_name);
        inputMessage = (EditText) findViewById(R.id.input_message);
        sendMessage = (ImageView) findViewById(R.id.message_send);

        colors = getResources().getIntArray(R.array.UserNameColors);
    }

    private void initialiseListeners() {
        groupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GroupChat.this, GroupMembers.class);
                i.putParcelableArrayListExtra("members", getMemberList());
                startActivity(i);
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageBody = inputMessage.getText().toString();
                if (!TextUtils.isEmpty(messageBody)) {
                    Member member = new Member("jyotman", "Jyotman", "http://jyotman94.pythonanywhere.com/static/profile.gif");
                    ChatMessage chatMessage = new ChatMessage(messageBody, new Date().getTime(), member, R.color.colorAccent);
                    messagesList.add(chatMessage);
                    chatAdapter.notifyItemInserted(messagesList.size() - 1);
                    chatRecyclerView.scrollToPosition(messagesList.size() - 1);
                    inputMessage.setText("");
                } else {
                    Log.e(TAG, "Empty Message");
                }
            }
        });
    }

    /**
     * Volley GET request to get the json data
     * Data is parsed and sent to recyclerview
     */
    private void getChatData() {
        String url = "http://haptik.co/android/test_data/";
        JsonObjectRequest chatRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Chat data received");
                        parseChatData(response);
                        setupRecyclerView();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error getting chat data");
                        progressBar.setVisibility(View.GONE);
                        error.printStackTrace();
                        Toast.makeText(GroupChat.this, "Error loading data", Toast.LENGTH_LONG).show();
                    }
                });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(chatRequest);
    }

    /**
     * Parses the json to form objects
     * Assigns different color to each user
     * @param response received from Volley request
     */
    private void parseChatData(JSONObject response) {
        messagesList = new ArrayList<>();
        HashMap<String, Integer> colorMapping = new HashMap<>();

        try {
            JSONArray messages = response.getJSONArray("messages");
            for (int i = 0; i < messages.length(); i++) {
                JSONObject message = messages.getJSONObject(i);
                Member member = new Member(message.getString("username"), message.getString("Name"),
                        message.getString("image-url"));
                int color;
                if (colorMapping.containsKey(member.getUsername())) {
                    color = colorMapping.get(member.getUsername());
                } else {
                    color = colorMapping.size() % colors.length;
                    colorMapping.put(member.getUsername(), color);
                }
                ChatMessage chatMessage = new ChatMessage(message.getString("body"),
                        getEpochFromTZ(message.getString("message-time")), member, color);
                messagesList.add(chatMessage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupRecyclerView() {
        progressBar.setVisibility(View.GONE);
        chatAdapter = new ChatAdapter(messagesList, this);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private long getEpochFromTZ(String time) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Count the messages of each user and form a list
     * @return the Members list to be used by GroupMember activity
     */
    private ArrayList<Member> getMemberList() {
        ArrayList<Member> members = new ArrayList<>();
        HashMap<String, Member> members2 = new HashMap<>();
        for (ChatMessage chatMessage : messagesList) {
            String username = chatMessage.getMember().getUsername();
            if (members2.containsKey(username)) {
                int existingCount = members2.get(username).getMessageCount();
                members2.get(username).setMessageCount(existingCount + 1);
            } else {
                chatMessage.getMember().setMessageCount(1);
                members2.put(username, chatMessage.getMember());
            }
        }

        Set memberUserNames = members2.keySet();
        Iterator i = memberUserNames.iterator();
        while (i.hasNext()) {
            members.add(members2.get(i.next()));
        }

        return members;
    }
}