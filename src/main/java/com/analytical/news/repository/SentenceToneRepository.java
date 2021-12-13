package com.analytical.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.analytical.news.entities.SentenceToneEntity;

public interface SentenceToneRepository extends JpaRepository<SentenceToneEntity, Long>{
	public List<SentenceToneEntity> findByArticleId(Long article_id);
}
