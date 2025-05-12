package com.app_wallet.virtual_wallet.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_entity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "system_points_id")
    private SystemPointsEntity systemPoints;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountEntity> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationEntity> notifications = new ArrayList<>();

    public UserEntity() {}

    public UserEntity(Long id, String name, String email, String password, SystemPointsEntity systemPoints) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.systemPoints = systemPoints;
    }

    public long getId() {
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

    public List<AccountEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountEntity> accounts) {
        this.accounts = accounts;
    }

    public List<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(NotificationEntity notification) {
        notifications.add(notification);
        notification.setUser(this);
    }

    public void addAccount(AccountEntity account) {
        accounts.add(account);
        account.setUser(this);
    }
}
