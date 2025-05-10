package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
}
