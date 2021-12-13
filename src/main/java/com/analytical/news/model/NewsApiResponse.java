package com.analytical.news.model;

import java.util.List;

import com.analytical.news.entities.ArticleEntity;

public class NewsApiResponse {
	private String status;
	private Double totalResults;
	private List<ArticleEntity> articles;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(Double totalResults) {
		this.totalResults = totalResults;
	}
	public List<ArticleEntity> getArticles() {
		return articles;
	}
	public void setArticles(List<ArticleEntity> articles) {
		this.articles = articles;
	}
	
	
}
