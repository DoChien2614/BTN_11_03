package com.example.btn_11_03;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btn_11_03.model.Room;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Room> roomList = new ArrayList<>(); // Dữ liệu chỉ lưu tạm thời bằng List
    private RoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRooms);
        Button btnAddRoom = findViewById(R.id.btnAddRoom);

        adapter = new RoomAdapter(this, roomList, new RoomAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                // Mở màn hình sửa thông tin phòng [cite: 35, 36]
                Intent intent = new Intent(MainActivity.this, AddEditRoomActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                // Xóa khỏi List và Cập nhật lại RecyclerView [cite: 41, 42]
                roomList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Nút thêm phòng [cite: 21]
        btnAddRoom.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditRoomActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged(); // Cập nhật RecyclerView mỗi khi quay lại màn hình [cite: 25, 37]
    }
}