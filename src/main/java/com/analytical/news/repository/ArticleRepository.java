package com.analytical.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.analytical.news.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	
}
