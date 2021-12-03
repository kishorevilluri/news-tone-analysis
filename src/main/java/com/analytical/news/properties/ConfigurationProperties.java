package com.analytical.news.properties;

import org.springframework.context.annotation.Configuration;

@Configuration
@org.springframework.boot.context.properties.ConfigurationProperties(prefix="newsapi")
public class ConfigurationProperties {
	private String url;
	private String apiKey;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	
}
