package com.app_wallet.virtual_wallet.dto;

import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;
import java.util.List;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email,String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;

    }

    public UserDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserDTO(Long userId, String s, String s1) {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }


}