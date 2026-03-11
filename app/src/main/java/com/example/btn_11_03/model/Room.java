package com.example.btn_11_03.model;

import java.io.Serializable;

public class Room implements Serializable {
    private String id; // Mã phòng [cite: 13]
    private String name; // Tên phòng [cite: 14]
    private double price; // Giá thuê [cite: 15]
    private boolean isRented; // Tình trạng: true = Đã thuê, false = Còn trống [cite: 16]
    private String tenantName; // Tên người thuê [cite: 17]
    private String phone; // Số điện thoại [cite: 18]

    public Room(String id, String name, double price, boolean isRented, String tenantName, String phone) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isRented = isRented;
        this.tenantName = tenantName;
        this.phone = phone;
    }

    // Generate các Getter và Setter cho các thuộc tính trên
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public boolean isRented() { return isRented; }
    public void setRented(boolean rented) { isRented = rented; }
    public String getTenantName() { return tenantName; }
    public void setTenantName(String tenantName) { this.tenantName = tenantName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}