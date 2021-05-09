package com.crumbs.fss.repository;

import com.crumbs.fss.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
