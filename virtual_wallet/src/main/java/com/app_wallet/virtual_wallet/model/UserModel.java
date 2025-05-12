package com.app_wallet.virtual_wallet.model;

import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.entity.NotificationEntity;
import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;
import com.app_wallet.virtual_wallet.utils.LinkedList;

public class UserModel {

    private long id;
    private String name;
    private String email;
    private String password;
    private SystemPointsEntity systemPoints;

    private LinkedList<AccountEntity> accounts = new LinkedList<>();
    private LinkedList<NotificationEntity> notifications = new LinkedList<>();

    public UserModel() {
    }

    public UserModel(long id, String name, String email, String password, SystemPointsEntity systemPoints, LinkedList<AccountEntity> accounts, LinkedList<NotificationEntity> notifications) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.systemPoints = systemPoints;
        this.accounts = accounts;
        this.notifications = notifications;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SystemPointsEntity getSystemPoints() {
        return systemPoints;
    }

    public void setSystemPoints(SystemPointsEntity systemPoints) {
        this.systemPoints = systemPoints;
    }

    public LinkedList<AccountEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(LinkedList<AccountEntity> accounts) {
        this.accounts = accounts;
    }

    public LinkedList<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(LinkedList<NotificationEntity> notifications) {
        this.notifications = notifications;
    }
}

