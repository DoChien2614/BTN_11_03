package com.example.btn_11_03;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btn_11_03.model.Room;

public class AddEditRoomActivity extends AppCompatActivity {

    private EditText edtRoomId, edtRoomName, edtPrice, edtTenantName, edtPhone;
    private CheckBox cbIsRented;
    private Button btnSave;
    private int position = -1; // -1 nghĩa là thêm mới, >= 0 là sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_room);

        // Ánh xạ View
        edtRoomId = findViewById(R.id.edtRoomId);
        edtRoomName = findViewById(R.id.edtRoomName);
        edtPrice = findViewById(R.id.edtPrice);
        cbIsRented = findViewById(R.id.cbIsRented);
        edtTenantName = findViewById(R.id.edtTenantName);
        edtPhone = findViewById(R.id.edtPhone);
        btnSave = findViewById(R.id.btnSave);

        // Lấy position từ Intent nếu có (trường hợp click vào item để sửa)
        if (getIntent().hasExtra("position")) {
            position = getIntent().getIntExtra("position", -1);
            if (position != -1) {
                // Load dữ liệu cũ lên form
                Room room = MainActivity.roomList.get(position);
                edtRoomId.setText(room.getId());
                edtRoomName.setText(room.getName());
                edtPrice.setText(String.valueOf(room.getPrice()));
                cbIsRented.setChecked(room.isRented());
                edtTenantName.setText(room.getTenantName());
                edtPhone.setText(room.getPhone());

                setTitle("Sửa thông tin phòng");
            }
        } else {
            setTitle("Thêm phòng mới");
        }

        btnSave.setOnClickListener(v -> saveRoom());
    }

    private void saveRoom() {
        String id = edtRoomId.getText().toString().trim();
        String name = edtRoomName.getText().toString().trim();
        String priceStr = edtPrice.getText().toString().trim();
        boolean isRented = cbIsRented.isChecked();
        String tenantName = edtTenantName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        // Validate dữ liệu cơ bản
        if (id.isEmpty() || name.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ Mã, Tên và Giá phòng!", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá thuê phải là số hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng Room mới
        Room room = new Room(id, name, price, isRented, tenantName, phone);

        if (position == -1) {
            // Thêm vào List [cite: 24]
            MainActivity.roomList.add(room);
            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        } else {
            // Cập nhật lại List [cite: 37]
            MainActivity.roomList.set(position, room);
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }

        // Đóng Activity và quay lại MainActivity
        finish();
    }
}