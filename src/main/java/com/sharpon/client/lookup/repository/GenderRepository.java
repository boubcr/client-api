package com.sharpon.client.lookup.repository;

import com.sharpon.client.lookup.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenderRepository extends JpaRepository<Gender, Long>, JpaSpecificationExecutor<Gender> {

}