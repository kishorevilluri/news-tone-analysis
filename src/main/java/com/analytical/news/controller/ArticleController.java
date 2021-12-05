package com.analytical.news.controller;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.format.datetime.DateFormatter;
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
import com.analytical.news.entities.Author;
import com.analytical.news.entities.Source;
import com.analytical.news.model.NewsApiResponse;
import com.analytical.news.repository.ArticleRepository;
import com.analytical.news.repository.AuthorRepository;
import com.analytical.news.repository.SourceRepository;

@RestController
@RequestMapping("/articles")
public class ArticleController {
	
	@Autowired
	ArticleRepository articleRespository;
	
	@Autowired
	SourceRepository sourceRepository;
	
	@Autowired
	AuthorRepository authorRepository;
	
	//@Autowired
	//private ConfigurationProperties configProperties;
	
	@GetMapping("/{topic}")
	public ResponseEntity<String> newsArticles(@PathVariable(value = "topic") String topic) {
		RestTemplate restTemplate = new RestTemplate();
		String firstPageUrl = "http://newsapi.org/v2/everything?q={topic}&page=1";
		Map<String, String> params = new HashMap<>();
		params.put("topic", topic);
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-api-key", "e88b84b3405949d58d626e3d091404ca");
		HttpEntity<Void> entity = new HttpEntity<>(headers);
		ResponseEntity<NewsApiResponse> response = restTemplate.exchange(firstPageUrl, HttpMethod.GET, entity, NewsApiResponse.class,params);
		List<Author> authorList = new ArrayList<>();
		List<Source> sourceList = new ArrayList<>();
		List<Article> articleList = response.getBody().getArticles();
		for(Article article : articleList) {
			Author author = new Author();
			if(null != article.getAuthor()){
				String[] name = article.getAuthor().split(" ");
				if(name.length > 1) {
					author.setFirst_name(name[name.length-1]);
					author.setLast_name(name[0]);
				}else {
					author.setFirst_name(name[0]);
					author.setLast_name("N/A");
				}
			}else {
				author.setLast_name("Anonymous");
				author.setFirst_name("Anonymous");
			}
			Example<Author> authorExample = Example.of(author); 
			if(!authorList.contains(author) && !authorRepository.exists(authorExample)) {
				authorList.add(author);
			}else {
				article.setNewsAuthor(authorRepository.findOne(authorExample).get());
			}
			Example<Source> sourceExample = Example.of(article.getSource());
			if(!sourceList.contains(article.getSource()) && !sourceRepository.exists(sourceExample)) {
				sourceList.add(article.getSource());
			}else {
				article.setSource(sourceRepository.findOne(sourceExample).get());
			}
			article.setDate_published(OffsetDateTime.parse(article.getPublishedAt()));
			article.setUser_search_param(topic);
		}
		if(authorList.size() != 0) {
			authorRepository.saveAll(authorList);
		}
		if(sourceList.size() != 0) {
			sourceRepository.saveAll(sourceList);
		}
		articleRespository.saveAll(articleList);
		return response.ok("Excellet!");
	}
}
