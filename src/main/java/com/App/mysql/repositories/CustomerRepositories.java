package com.App.mysql.repositories;

import com.App.mysql.entities.CustomerMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepositories extends JpaRepository<CustomerMain,Long> {
}
