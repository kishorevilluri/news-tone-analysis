package com.analytical.news.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="article", schema="news")
public class ArticleEntity implements Serializable {
	
	private static final long serialVersionUID = -1798070786993154676L;
	
	@Id
	@Column(name = "article_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="source_id")
	private SourceEntity source;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="author_id")
	private AuthorEntity newsAuthor;
	
	@Transient
	private String author;
	
	private String title;
	private String description;
	private String url;
	private String user_search_param;
	
	@Column(name="content",
			columnDefinition = "CLOB NOT NULL")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String content;
	
	@Column(name="image_url")
	private String urlToImage;
	
	@Column(name="date_published", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime date_published;
	
	private String publishedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SourceEntity getSource() {
		return source;
	}

	public void setSource(SourceEntity source) {
		this.source = source;
	}

	public AuthorEntity getNewsAuthor() {
		return newsAuthor;
	}

	public void setNewsAuthor(AuthorEntity newsAuthor) {
		this.newsAuthor = newsAuthor;
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

	public OffsetDateTime getDate_published() {
		return date_published;
	}

	public void setDate_published(OffsetDateTime publishedAt) {
		this.date_published = publishedAt;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	
	
	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}
	
	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}
	
	public String getUser_search_param() {
		return user_search_param;
	}

	public void setUser_search_param(String user_search_param) {
		this.user_search_param = user_search_param;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", source=" + source + ", newsAuthor=" + newsAuthor + ", author=" + author
				+ ", title=" + title + ", description=" + description + ", url=" + url + ", urlToImage=" + urlToImage
				+ ", publishedAt=" + date_published + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((newsAuthor == null) ? 0 : newsAuthor.hashCode());
		result = prime * result + ((date_published == null) ? 0 : date_published.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((urlToImage == null) ? 0 : urlToImage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleEntity other = (ArticleEntity) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (newsAuthor == null) {
			if (other.newsAuthor != null)
				return false;
		} else if (!newsAuthor.equals(other.newsAuthor))
			return false;
		if (date_published == null) {
			if (other.date_published != null)
				return false;
		} else if (!date_published.equals(other.date_published))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (urlToImage == null) {
			if (other.urlToImage != null)
				return false;
		} else if (!urlToImage.equals(other.urlToImage))
			return false;
		return true;
	}
	
}
