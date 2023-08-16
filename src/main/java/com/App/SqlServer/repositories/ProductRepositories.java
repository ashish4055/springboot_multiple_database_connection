package com.App.SqlServer.repositories;

import com.App.SqlServer.entities.ProductMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositories extends JpaRepository<ProductMain,Long> {
}
