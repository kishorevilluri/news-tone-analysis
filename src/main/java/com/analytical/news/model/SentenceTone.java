package com.analytical.news.model;

import java.util.List;

public class SentenceTone {
	private String sentence_id;
	private String text;
	private List<Tone> tones;
	public String getSentence_id() {
		return sentence_id;
	}
	public void setSentence_id(String sentence_id) {
		this.sentence_id = sentence_id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Tone> getTones() {
		return tones;
	}
	public void setTones(List<Tone> tones) {
		this.tones = tones;
	}
	@Override
	public String toString() {
		return "SentenceTone [sentence_id=" + sentence_id + ", text=" + text + ", tones=" + tones + "]";
	}
}
