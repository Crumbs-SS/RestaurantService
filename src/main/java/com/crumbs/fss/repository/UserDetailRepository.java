package com.crumbs.fss.repository;

import com.crumbs.fss.entity.Category;
import com.crumbs.fss.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}
