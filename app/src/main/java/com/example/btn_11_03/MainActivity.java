package com.example.btn_11_03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btn_11_03.model.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Danh sách gốc (lưu toàn bộ dữ liệu)
    public static List<Room> roomList = new ArrayList<>();
    // Danh sách phụ dùng để hiển thị lên RecyclerView
    private List<Room> filteredList = new ArrayList<>();
    private RoomAdapter adapter;

    private Spinner spinnerStatus;
    private EditText edtMinPrice, edtMaxPrice;
    private Button btnFilter, btnAddRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        RecyclerView recyclerView = findViewById(R.id.recyclerViewRooms);
        btnAddRoom = findViewById(R.id.btnAddRoom);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        edtMinPrice = findViewById(R.id.edtMinPrice);
        edtMaxPrice = findViewById(R.id.edtMaxPrice);
        btnFilter = findViewById(R.id.btnFilter);

        // Cài đặt dữ liệu cho Spinner chọn trạng thái
        String[] statusOptions = {"Tất cả", "Còn trống", "Đã thuê"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusOptions);
        spinnerStatus.setAdapter(spinnerAdapter);

        // Khởi tạo Adapter với danh sách PHỤ (filteredList)
        adapter = new RoomAdapter(this, filteredList, new RoomAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                // Phải tìm đúng index trong danh sách gốc để sửa
                Room selectedRoom = filteredList.get(position);
                int originalIndex = roomList.indexOf(selectedRoom);

                Intent intent = new Intent(MainActivity.this, AddEditRoomActivity.class);
                intent.putExtra("position", originalIndex);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                // Xóa ở cả 2 danh sách
                Room selectedRoom = filteredList.get(position);
                roomList.remove(selectedRoom);
                filteredList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Nút thêm phòng
        btnAddRoom.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditRoomActivity.class);
            startActivity(intent);
        });

        // Xử lý sự kiện nút Lọc
        btnFilter.setOnClickListener(v -> filterData());
    }

    private void filterData() {
        filteredList.clear();

        // Lấy điều kiện lọc
        int selectedStatusPosition = spinnerStatus.getSelectedItemPosition(); // 0: Tất cả, 1: Trống, 2: Đã thuê
        String minPriceStr = edtMinPrice.getText().toString().trim();
        String maxPriceStr = edtMaxPrice.getText().toString().trim();

        double minPrice = minPriceStr.isEmpty() ? 0 : Double.parseDouble(minPriceStr);
        double maxPrice = maxPriceStr.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceStr);

        if (minPrice > maxPrice) {
            Toast.makeText(this, "Giá trị 'Từ' không được lớn hơn 'Đến'!", Toast.LENGTH_SHORT).show();
            // Nạp lại toàn bộ nếu lỗi logic
            filteredList.addAll(roomList);
            adapter.notifyDataSetChanged();
            return;
        }

        // Duyệt danh sách gốc để lọc
        for (Room room : roomList) {
            boolean matchesStatus = true;
            if (selectedStatusPosition == 1 && room.isRented()) {
                matchesStatus = false; // Chọn Trống nhưng phòng Đã thuê
            } else if (selectedStatusPosition == 2 && !room.isRented()) {
                matchesStatus = false; // Chọn Đã thuê nhưng phòng Trống
            }

            boolean matchesPrice = (room.getPrice() >= minPrice && room.getPrice() <= maxPrice);

            // Nếu thỏa mãn cả 2 điều kiện thì thêm vào danh sách hiển thị
            if (matchesStatus && matchesPrice) {
                filteredList.add(room);
            }
        }

        // Thông báo cho adapter vẽ lại danh sách
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại danh sách hiển thị mỗi khi quay lại từ màn hình Thêm/Sửa
        filterData();
    }
}