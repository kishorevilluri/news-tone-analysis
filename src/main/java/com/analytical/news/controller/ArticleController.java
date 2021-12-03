package com.analytical.news.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.analytical.news.model.NewsApiResponse;
import com.analytical.news.properties.ConfigurationProperties;

@RestController
@RequestMapping("/articles")
public class ArticleController {
	
	@Autowired
	private ConfigurationProperties configProperties;
	
	@GetMapping(value="/topic")
	public ResponseEntity<NewsApiResponse> newsArticles(@RequestParam("topic") String topic) {
		RestTemplate restTemplate = new RestTemplate();
		String firstPageUrl = configProperties.getUrl()+"q={topic}&page=1";
		Map<String, String> params = new HashMap<>();
		params.put("topic", topic);
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-api-key", configProperties.getApiKey());
		HttpEntity<Void> entity = new HttpEntity<>(headers);
		ResponseEntity<NewsApiResponse> response = restTemplate.exchange(firstPageUrl, HttpMethod.GET, entity, NewsApiResponse.class,params);
		return response;
	}
}
