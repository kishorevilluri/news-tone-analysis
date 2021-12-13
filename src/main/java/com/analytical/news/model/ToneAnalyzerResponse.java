package com.analytical.news.model;

import java.util.List;

public class ToneAnalyzerResponse {
	
	private ArticleTone document_tone;
	private List<SentenceTone> sentences_tone;
	
	public ArticleTone getDocument_tone() {
		return document_tone;
	}
	public void setDocument_tone(ArticleTone document_tone) {
		this.document_tone = document_tone;
	}
	public List<SentenceTone> getSentences_tone() {
		return sentences_tone;
	}
	public void setSentences_tone(List<SentenceTone> sentences_tone) {
		this.sentences_tone = sentences_tone;
	}
	@Override
	public String toString() {
		return "ToneAnalyzerResponse [document_tone=" + document_tone + ", sentences_tone=" + sentences_tone + "]";
	}
}
