package com.example.btn_11_03;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btn_11_03.model.Room;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int position); // Click vào item -> mở màn hình sửa [cite: 36]
        void onDeleteClick(int position); // Xóa phòng
    }

    public RoomAdapter(Context context, List<Room> roomList, OnItemClickListener listener) {
        this.context = context;
        this.roomList = roomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvRoomName.setText("Phòng: " + room.getName());
        holder.tvPrice.setText("Giá: " + room.getPrice());

        // Có thể tô màu: Xanh -> Còn trống, Đỏ -> Đã thuê [cite: 32, 33, 34]
        if (room.isRented()) {
            holder.tvStatus.setText("Tình trạng: Đã thuê");
            holder.tvStatus.setTextColor(Color.RED);
        } else {
            holder.tvStatus.setText("Tình trạng: Còn trống");
            holder.tvStatus.setTextColor(Color.GREEN);
        }

        // Sự kiện click để sửa [cite: 36]
        holder.itemView.setOnClickListener(v -> listener.onEditClick(position));

        // Nhấn giữ để hiển thị AlertDialog xác nhận xóa [cite: 39, 40]
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa phòng này?")
                    .setPositiveButton("Xóa", (dialog, which) -> listener.onDeleteClick(position))
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvPrice, tvStatus;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}