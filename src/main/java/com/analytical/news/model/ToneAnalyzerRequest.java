package com.analytical.news.model;

public class ToneAnalyzerRequest {
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "ToneAnalyserRequest [text=" + text + "]";
	}
}
