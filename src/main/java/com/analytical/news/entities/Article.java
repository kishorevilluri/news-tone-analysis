package com.analytical.news.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="article", schema="news")
public class Article {
	@Id
	@Column(name = "article_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="source_id")
	private Source source;
	
	@ManyToOne
	@JoinColumn(name="author_id")
	private Author author;
	
	private String title;
	private String description;
	private String url;
	
	@Column(name="image_url")
	private String urlToImage;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_published")
	private java.util.Date publishedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage_url() {
		return urlToImage;
	}

	public void setImage_url(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public java.util.Date getDate_published() {
		return publishedAt;
	}

	public void setDate_published(java.util.Date publishedAt) {
		this.publishedAt = publishedAt;
	}
	
}
