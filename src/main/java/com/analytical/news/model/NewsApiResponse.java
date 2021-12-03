package com.analytical.news.model;

import java.util.List;

import com.analytical.news.entities.Article;

public class NewsApiResponse {
	private String status;
	private Double totalResults;
	private List<Article> articles;
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
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
	
}
