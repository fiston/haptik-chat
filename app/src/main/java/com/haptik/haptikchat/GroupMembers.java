package com.haptik.haptikchat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.haptik.haptikchat.adapters.ChatAdapter;
import com.haptik.haptikchat.adapters.MembersAdapter;
import com.haptik.haptikchat.models.Member;

import java.util.ArrayList;

public class GroupMembers extends AppCompatActivity {

    private final String TAG = "GroupMembers";

    private ImageView back;
    private FloatingActionButton addMember;
    private RecyclerView membersRecyclerView;
    private ArrayList<Member> members;
    private MembersAdapter membersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_members);

        initialise();
        initialiseListeners();

        // Received the data from the previous activity
        members = getIntent().getParcelableArrayListExtra("members");
        setupRecyclerView();
    }

    private void initialise() {
        back = (ImageView) findViewById(R.id.back);
        addMember = (FloatingActionButton) findViewById(R.id.add_member);
        membersRecyclerView = (RecyclerView) findViewById(R.id.group_members_rv);
    }

    private void initialiseListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add new member!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupRecyclerView() {
        membersAdapter = new MembersAdapter(members, this);
        membersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        membersRecyclerView.setAdapter(membersAdapter);
    }
}