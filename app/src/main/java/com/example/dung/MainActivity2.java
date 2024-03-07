package com.example.dung;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    TextView tvKQ;
    FirebaseFirestore database;
    Context context;
    String strQK = "";
    ToDo toDo = null;
    @SuppressWarnings("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        database = FirebaseFirestore.getInstance();
        tvKQ = findViewById(R.id.tvKQ);
        context = this;
//        insert();
//        update();
//        delete();
        select();
    }
    void insert(){
        String id = UUID.randomUUID().toString();//lấy chuỗi ngẫu nhiên
        toDo = new ToDo(id, "title 11", "content 11");//tạo đối tượng mới để insert
        database.collection("todo")
                .document(id).set(toDo.convertToHashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Insert thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(context, "Insert thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    void update(){
        String id = "6967f2ba-f6d5-41b3-b66d-ee86f835d4b7";//copy id vào đây
        toDo = new ToDo(id, "title 20 update", "content 20 update");//nội dung cần update
        database.collection("todo")
                .document(id)
                .update(toDo.convertToHashMap())//thực hiện update
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void delete(){
        String id = "6967f2ba-f6d5-41b3-b66d-ee86f835d4b7";//copy id vào đây
        database.collection("todo")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Delete này", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Delete thất bị", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    ArrayList<ToDo> select(){
        ArrayList<ToDo> list = new ArrayList<>();
        database.collection("todo")
                .get()//lấy về dữ liệu
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                           strQK = "";
                           for(QueryDocumentSnapshot doc : task.getResult()){
                               ToDo toDo = new ToDo(doc.getId(), doc.getString("title"), doc.getString("content"));//chuyển dữ liệu đọc được sang ToDo
                               list.add(toDo);
                               strQK += "id: " + toDo.getId() + "\n";
                               strQK += "title: " + toDo.getTitle() + "\n";
                               strQK += "content: " + toDo.getContent() + "\n";
                           }
                           tvKQ.setText(strQK);
                        }else {
                            Toast.makeText(context, "select thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return list;
    }
}