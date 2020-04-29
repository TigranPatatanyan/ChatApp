package com.tigran.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ContactsListAdapter adapter;
    FirebaseUser myUser;
    User toUser;
    List<User> users;
    RecyclerView recyclerView;
    ImageView myImage;
    ImageView logOut;
    TextView name;
    DatabaseReference myRef;
    DatabaseReference refUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    private void init() {
        myImage = findViewById(R.id.iv_menu_avatar);
        name = findViewById(R.id.tv_username);
        logOut = findViewById(R.id.btn_logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialog
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ChatActivity.this, LoginActivity.class));
                finish();
            }
        });
        myUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("users").child(myUser.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                name.setText(user.getName());
                if (user.getImageURL().equals("default")) {

                } else {
                    Glide.with(ChatActivity.this).load(user.getImageURL()).apply(RequestOptions.circleCropTransform()).into(myImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView = findViewById(R.id.rv_contacts);
        users = new ArrayList<>();
        adapter = new ContactsListAdapter(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUser = (User) view.getTag();
                tuneToDialog(toUser.getUid());
            }
        }, users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        refUsers = FirebaseDatabase.getInstance().getReference("users");
        refUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User newUser = dataSnapshot.getValue(User.class);
                users.add(newUser);
                for (User i : users) {
                    if (i.getUid().equals(myUser.getUid())) {
                        users.remove(i);
                        break;
                    }
                }
                adapter.update(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void tuneToDialog(String uid) {
        Intent intent = new Intent(ChatActivity.this, DialogActivity.class).putExtra("toUser", uid);
        startActivity(intent);
        finish();
    }
}
