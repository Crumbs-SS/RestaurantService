package com.crumbs.fss.repository;

import com.crumbs.fss.entity.Category;
import com.crumbs.fss.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
