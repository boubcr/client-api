package com.sharpon.client.lookup.repository;

import com.sharpon.client.lookup.entity.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NationalityRepository extends JpaRepository<Nationality, Long>, JpaSpecificationExecutor<Nationality> {

}