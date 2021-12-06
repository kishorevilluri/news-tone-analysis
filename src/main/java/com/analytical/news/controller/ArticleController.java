package com.analytical.news.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.analytical.news.entities.Article;
import com.analytical.news.model.NewsApiResponse;
import com.analytical.news.services.PersistenceService;


@RestController
@RequestMapping("/articles")
public class ArticleController {
	Logger logger = LoggerFactory.getLogger(ArticleController.class);
	@Autowired
	PersistenceService persistence;
	
	//@Autowired
	//private ConfigurationProperties configProperties;
	
	@GetMapping("/{topic}")
	public ResponseEntity<String> newsArticles(@PathVariable(value = "topic") String topic) throws MalformedURLException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		String firstPageUrl = "http://newsapi.org/v2/everything?q={topic}&page=1";
		Map<String, String> params = new HashMap<>();
		params.put("topic", topic);
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-api-key", "e88b84b3405949d58d626e3d091404ca");
		HttpEntity<Void> entity = new HttpEntity<>(headers);
		
		//Call NewsAPI
		ResponseEntity<NewsApiResponse> response = restTemplate.exchange(firstPageUrl, HttpMethod.GET, entity, NewsApiResponse.class,params);
		
		//Persist data		
		List<Article> articles = persistence.saveArticles(topic, response);
		
		return response.ok("Excellet!");
	}
}
