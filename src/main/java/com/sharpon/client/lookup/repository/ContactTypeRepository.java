package com.sharpon.client.lookup.repository;

import com.sharpon.client.lookup.entity.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactTypeRepository extends JpaRepository<ContactType, Long>, JpaSpecificationExecutor<ContactType> {

}