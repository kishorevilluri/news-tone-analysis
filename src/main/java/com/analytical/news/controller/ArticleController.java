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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.analytical.news.entities.ArticleEntity;
import com.analytical.news.model.NewsApiResponse;
import com.analytical.news.model.ToneAnalyzerRequest;
import com.analytical.news.model.ToneAnalyzerResponse;
import com.analytical.news.services.PersistenceService;


@RestController
@RequestMapping("/articles")
public class ArticleController {
	Logger logger = LoggerFactory.getLogger(ArticleController.class);
	@Autowired
	PersistenceService persistence;
	
	//@Autowired
	//private ConfigurationProperties configProperties;
	
	@GetMapping("/news")
	public ResponseEntity<String> newsArticles(@RequestParam(name="topic") String topic) throws MalformedURLException, IOException {
		ResponseEntity<NewsApiResponse> response = callNewsApi(topic);
		
		//Persist data		
		List<ArticleEntity> articles = persistence.saveArticles(topic, response);
		
		//Tone Analyzer
		callToneAnalyzer(articles);
		
		return response.ok("Excellet!");
	}

	private void callToneAnalyzer(List<ArticleEntity> articles) {
		ToneAnalyzerRequest requestObject = new ToneAnalyzerRequest();
		String url = "https://api.eu-gb.tone-analyzer.watson.cloud.ibm.com/instances/14b7ce24-0585-4b09-9e02-825fca0395dc/v3/tone?version=2017-09-21";
		RestTemplate ibmRestTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("apiKey", "-XxmGkjdiDXaatyNJfUhzzkY7hAybNZgQLqbxAyVVtG5");
		
		for(ArticleEntity article : articles) {
			requestObject.setText(article.getContent());
			HttpEntity<ToneAnalyzerRequest> reqEntity = new HttpEntity<>(requestObject,headers);
			ResponseEntity<ToneAnalyzerResponse> response= ibmRestTemplate.postForEntity(url, reqEntity, ToneAnalyzerResponse.class);
			persistence.saveTones(response, article);
			break;
		}
	}

	private ResponseEntity<NewsApiResponse> callNewsApi(String topic) {
		RestTemplate restTemplate = new RestTemplate();
		String firstPageUrl = "http://newsapi.org/v2/everything?q={topic}&page=1";
		Map<String, String> params = new HashMap<>();
		params.put("topic", topic);
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-api-key", "e88b84b3405949d58d626e3d091404ca");
		HttpEntity<Void> entity = new HttpEntity<>(headers);
		
		//Call NewsAPI
		ResponseEntity<NewsApiResponse> response = restTemplate.exchange(firstPageUrl, HttpMethod.GET, entity, NewsApiResponse.class,params);
		return response;
	}
}
