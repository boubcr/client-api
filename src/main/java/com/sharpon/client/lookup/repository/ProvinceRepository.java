package com.sharpon.client.lookup.repository;

import com.sharpon.client.lookup.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProvinceRepository extends JpaRepository<Province, Long>, JpaSpecificationExecutor<Province> {

}