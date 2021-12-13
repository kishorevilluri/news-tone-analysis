package com.analytical.news.model;

public class Tone {
	private Double score;
	private String tone_name;
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getTone_name() {
		return tone_name;
	}
	public void setTone_name(String tone_name) {
		this.tone_name = tone_name;
	}
	@Override
	public String toString() {
		return "Tone [score=" + score + ", tone_name=" + tone_name + "]";
	}
}
