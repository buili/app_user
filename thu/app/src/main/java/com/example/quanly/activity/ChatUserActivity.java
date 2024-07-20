package com.example.quanly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanly.R;
import com.example.quanly.adapter.ChatAdapter;
import com.example.quanly.adapter.SanPhamAdapter;
import com.example.quanly.model.ChatMessage;
import com.example.quanly.model.User;
import com.example.quanly.ultil.Utils;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ChatUserActivity extends AppCompatActivity {

    int iduser;
    String str_iduser;
    EditText edtiduser;
    RecyclerView recyclerView;
    ImageView imgSend;
    EditText edtMess;

    FirebaseFirestore db;
    ChatAdapter adapter;
    List<ChatMessage> list;
    int moi;

    // boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);
        intitView();
        initControl();
        listenMess();

    }

    private void insertUser() {

        Log.d("ChatUserActivity", "insertUser - str_iduser: " + str_iduser);
        int idusser_current = Utils.user_current.getId();
        String str_idusser_current = String.valueOf(idusser_current);


        HashMap<String, Object> user = new HashMap<>();
        user.put("id", Utils.user_current.getId());
        user.put("username", Utils.user_current.getUsername());
        db.collection(str_iduser)
                .document(String.valueOf(Utils.user_current.getId()))
                .set(user);

        HashMap<String, Object> user2 = new HashMap<>();
        user2.put("id", Integer.parseInt(str_iduser));
        User usernhan = new User(Integer.parseInt(str_iduser));
        user2.put("username", usernhan.getUsername());
        // user2.put("username", );
       // user2.put("username", Utils.user_current.getUsername());
        db.collection(str_idusser_current)
                .document(String.valueOf(str_iduser))
                .set(user2);
    }


    private void initControl() {
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (moi == 0) {
                    str_iduser = edtiduser.getText().toString().trim();
                    //flag = true;
                    Log.d("ChatUserActivity", "str_iduser: " + str_iduser);
                    if (!TextUtils.isEmpty(str_iduser)) {
                        edtiduser.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        listenMess();
                        //adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ChatUserActivity.this, "Vui lòng nhập ID người dùng", Toast.LENGTH_SHORT).show();
                        return; // Không tiếp tục nếu str_iduser rỗng
                    }

                }
                if (!TextUtils.isEmpty(str_iduser)) {
                    sendMessToFireBase();
                    insertUser();
                }

            }
        });
    }

    private void sendMessToFireBase() {
        String str_mess = edtMess.getText().toString().trim();
        if (TextUtils.isEmpty(str_mess)) {

        } else {
            HashMap<String, Object> message = new HashMap<>();
            message.put(Utils.SENDID, String.valueOf(Utils.user_current.getId()));
            message.put(Utils.RECEIVEDID, str_iduser);
            message.put(Utils.MESS, str_mess);
            message.put(Utils.DATETIME, new Date());

            db.collection(Utils.PATH_CHAT2)
                    .add(message);
            edtMess.setText("");

        }
    }

        private void listenMess(){
        db.collection(Utils.PATH_CHAT2)
                .whereEqualTo(Utils.SENDID, String.valueOf(Utils.user_current.getId()))
                .whereEqualTo(Utils.RECEIVEDID, str_iduser)
                .addSnapshotListener(eventListener);

        db.collection(Utils.PATH_CHAT2)
                .whereEqualTo(Utils.SENDID,  str_iduser)
                .whereEqualTo(Utils.RECEIVEDID, String.valueOf(Utils.user_current.getId()))
                .addSnapshotListener(eventListener);

    }
//    private void listenMess(String userId) {
//        db.collection(Utils.PATH_CHAT2)
//                .whereEqualTo(Utils.SENDID, String.valueOf(Utils.user_current.getId()))
//                .whereEqualTo(Utils.RECEIVEDID, userId)
//                .addSnapshotListener(eventListener);
//
//        db.collection(Utils.PATH_CHAT2)
//                .whereEqualTo(Utils.SENDID, userId)
//                .whereEqualTo(Utils.RECEIVEDID, String.valueOf(Utils.user_current.getId()))
//                .addSnapshotListener(eventListener);
//    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = list.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.sendid = documentChange.getDocument().getString(Utils.SENDID);
                    chatMessage.receivedid = documentChange.getDocument().getString(Utils.RECEIVEDID);
                    chatMessage.mess = documentChange.getDocument().getString(Utils.MESS);
                    chatMessage.dateObj = documentChange.getDocument().getDate(Utils.DATETIME);
                    chatMessage.datetime = format_date(documentChange.getDocument().getDate(Utils.DATETIME));

                    list.add(chatMessage);
                }
            }

            Collections.sort(list, (ob1, ob2) -> ob1.dateObj.compareTo(ob2.dateObj));
            if (count == 0) {
                adapter.notifyDataSetChanged();
            } else {
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(list.size() - 1);
            }
        }
    };

    private String format_date(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy- hh:mm a", Locale.getDefault()).format(date);
    }

    private void intitView() {
        list = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        edtiduser = findViewById(R.id.edtiduser2);
        recyclerView = findViewById(R.id.recyclerviewchatuser);
        imgSend = findViewById(R.id.img_senduser);
        edtMess = findViewById(R.id.edtinputtextuser);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new ChatAdapter(getApplicationContext(), list, String.valueOf(Utils.user_current.getId()));
        recyclerView.setAdapter(adapter);

//        iduser = getIntent().getIntExtra("id", 0);
//        str_iduser = String.valueOf(iduser);

        moi = getIntent().getIntExtra("moi", 1);
        if (moi == 0) {
            edtiduser.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
//            edtiduser.requestFocus();

        } else {
            iduser = getIntent().getIntExtra("id", 0);
            str_iduser = String.valueOf(iduser);
        }

        Log.d("ChatUserActivity", "intitView - str_iduser: " + str_iduser);
    }
}