package com.app_wallet.virtual_wallet.entity;

import com.app_wallet.virtual_wallet.utils.LinkedList;

public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private SystemPoints systemPoints;

    LinkedList<Account> accounts = new LinkedList<>();
    LinkedList<Notification> notifications = new LinkedList<>();

    public User() {
    }

    public User(long id, String name, String email, String password, SystemPoints systemPoints) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.systemPoints = systemPoints;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SystemPoints getSystemPoints() {
        return systemPoints;
    }

    public void setSystemPoints(SystemPoints systemPoints) {
        this.systemPoints = systemPoints;
    }

    public LinkedList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(LinkedList<Account> accounts) {
        this.accounts = accounts;
    }

    public LinkedList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(LinkedList<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }
}
