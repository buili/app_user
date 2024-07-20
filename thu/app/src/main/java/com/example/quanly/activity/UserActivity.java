package com.example.quanly.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.quanly.R;
import com.example.quanly.adapter.ChatUserAdapter;
import com.example.quanly.adapter.UserAdapter;
import com.example.quanly.model.User;
import com.example.quanly.ultil.Chung;
import com.example.quanly.ultil.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ChatUserAdapter userAdapter;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        intitView();
        actionToolBar();
        getUserFromFire();
        intitControl();
    }

    private void intitControl() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatUserActivity.class);
                intent.putExtra("moi", 0);
                startActivity(intent);
            }
        });
    }

    private void getUserFromFire() {
        int idusser_current = Utils.user_current.getId();
        String str_idusser_current = String.valueOf(idusser_current);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(str_idusser_current)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<User> userList = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                User user = new User();
                                //user.setId(Integer.parseInt(documentSnapshot.getString("id")));
                                user.setId(documentSnapshot.getLong("id").intValue());
                                user.setUsername(documentSnapshot.getString("username"));
                                userList.add(user);
                            }
                            if(userList.size() > 0){
                                userAdapter = new ChatUserAdapter(getApplicationContext(), userList);
                                recyclerView.setAdapter(userAdapter);
                            }
                        }
                    }
                });
    }

    private void actionToolBar() {
        Chung.ActionToolBar(this, toolbar);
    }

    private void intitView() {
        toolbar = findViewById(R.id.toolbaruser);
        recyclerView = findViewById(R.id.recyclerview_user);
        imageView = findViewById(R.id.img_user);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
}