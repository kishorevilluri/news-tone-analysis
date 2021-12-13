package com.analytical.news.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sentence_tone", schema="news")
public class SentenceToneEntity {
	
	@Id
	@Column(name="sentence_tone_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="article_id")
	private ArticleEntity article;
	
	@ManyToOne
	@JoinColumn(name="tone_id")
	private ToneEntity tone;
	
	private Double score;
	private String sentence;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ArticleEntity getArticle() {
		return article;
	}
	public void setArticle(ArticleEntity article) {
		this.article = article;
	}
	public ToneEntity getTone() {
		return tone;
	}
	public void setTone(ToneEntity tone) {
		this.tone = tone;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
}
