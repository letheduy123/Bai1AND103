package fpoly.duyltph38444.bai1crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button btnInsert,btnUpdate,btnDelete,btnShow;
    TextView tvHienThi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=FirebaseFirestore.getInstance();//khoi tao database
        btnInsert =findViewById(R.id.btnInsert);
        btnUpdate =findViewById(R.id.btnUpdate);
        btnDelete =findViewById(R.id.btnDelete);
        btnShow =findViewById(R.id.btnShow);
        tvHienThi =findViewById(R.id.tvHienThi);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertFirebase(tvHienThi);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFirebase(tvHienThi);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              deleteFirebase(tvHienThi);
            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hien thi du lieu ne
                SelectData(tvHienThi);
            }
        });



    }
    String  id="";
    ToDo toDo =null;
    FirebaseFirestore database;
    public void  insertFirebase(TextView tvResult){
        id= UUID.randomUUID().toString(); //lay id bat ki
        toDo=new ToDo(id,"title 3","content3");
        //chuyen doi sang doi tuong co the thao tac voi firebase
        HashMap<String,Object> mapTodo=toDo.convertHashMap();
        //insert vao database
        database.collection("TODO")
                .document(id)
                .set(mapTodo)//doi tuong can insert
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
tvResult.setText("Them thanh cong");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tvResult.setText(e.getMessage());
                    }
                });



    }
public void updateFirebase(TextView tvResul){
        id="21d3dd7e-a7cb-4a6b-83bf-6e513e39dba6";
        toDo=new ToDo(id,"sua title 3","sua conten3");
        database.collection("TODO").document(toDo.getId())
                .update(toDo.convertHashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
tvResul.setText("Update thanhcong ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tvResul.setText(e.getMessage());
                    }
                });

}
public  void deleteFirebase(TextView tvResul){
        id="21d3dd7e-a7cb-4a6b-83bf-6e513e39dba6";
        database.collection("TODO").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
tvResul.setText("Delete thanh cong");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
tvResul.setText(e.getMessage());
                    }
                });

}
String strResul="";
    public ArrayList<ToDo>SelectData(TextView tvResul){
        ArrayList<ToDo>list=new ArrayList<>();
        database.collection("TODO")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){ //lay du lieu thanh cong

                            strResul="";
                            //doc theo tung dong
                            for (QueryDocumentSnapshot docoment: task.getResult()){
                                //chuyen dong doc duoc theo doi tuong
                                ToDo toDo1 =docoment.toObject(ToDo.class);

                                //chuyen doi tuong thanh chuoi
                                strResul +="Id: "+toDo1.getId()+"\n";
                                list.add(toDo1);  //them vao list
 //hien thi ket qua
                                tvResul.setText(strResul);

                            }
                        }else {
                            tvResul.setText("Doc du lieu that bai");
                        }


                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
tvResul.setText(e.getMessage());
                    }
                });
        return list;
    }
}