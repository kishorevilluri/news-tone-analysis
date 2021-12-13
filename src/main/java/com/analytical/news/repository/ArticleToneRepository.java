package com.analytical.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.analytical.news.entities.ArticleToneEntity;

public interface ArticleToneRepository extends JpaRepository<ArticleToneEntity, Long>{
	public List<ArticleToneEntity> findByArticleId(Long article_id);
}
