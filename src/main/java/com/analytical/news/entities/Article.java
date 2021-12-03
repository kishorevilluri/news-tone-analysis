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
	private String image_url;
	
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date date_published;

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
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public java.util.Date getDate_published() {
		return date_published;
	}

	public void setDate_published(java.util.Date date_published) {
		this.date_published = date_published;
	}
	
}
