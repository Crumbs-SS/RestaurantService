package com.crumbs.fss.repository;

import com.crumbs.fss.entity.Category;
import com.crumbs.fss.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    @Query("select email from user_detail where email = ?1")
    String findUserByEmail(String ownerEmail);
}
