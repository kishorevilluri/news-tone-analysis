package com.analytical.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.analytical.news.entities.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	public String findByUrl(String url);
}
