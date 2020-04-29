package com.tigran.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends AppCompatActivity {

    String currentUid;
    String toUid;
    EditText message;
    DialogMessagesAdapter adapter;
    List<Message> messageList;
    DatabaseReference refMessages;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        init();
        displayAllMessages();
    }

    private void init() {
        refMessages = FirebaseDatabase.getInstance().getReference("messages");
        messageList = new ArrayList<>();
        currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        toUid = getIntent().getExtras().getString("toUser");
        recyclerView = findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        message = findViewById(R.id.et_message);
        adapter = new DialogMessagesAdapter(this, currentUid, messageList);
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refMessages.setValue(new Message(currentUid, toUid, message.getText().toString()));
                message.setText("");
            }
        });

        refMessages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                if ((message.getFrom() == currentUid && message.getTo() == toUid) || (message.getFrom() == toUid && message.getTo() == currentUid)) {
                    messageList.add(message);
                    adapter.update(messageList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayAllMessages() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
