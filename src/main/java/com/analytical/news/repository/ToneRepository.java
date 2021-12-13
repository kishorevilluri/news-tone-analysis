package com.analytical.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.analytical.news.entities.ToneEntity;

@Repository
public interface ToneRepository extends JpaRepository<ToneEntity, Long>{
	public String findByCategory(String category);
}