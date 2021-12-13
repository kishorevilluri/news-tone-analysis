package com.analytical.news.model;

import java.util.List;

public class ArticleTone {
	private List<Tone> tones;

	public List<Tone> getTones() {
		return tones;
	}

	public void setTones(List<Tone> tones) {
		this.tones = tones;
	}
	@Override
	public String toString() {
		return "ArticleTone [tones=" + tones + "]";
	}
}
