package com.analytical.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.analytical.news.entities.SourceEntity;

@Repository
public interface SourceRepository extends JpaRepository<SourceEntity, Long> {

}
