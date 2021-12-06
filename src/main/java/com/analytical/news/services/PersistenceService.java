package com.analytical.news.services;

import java.net.SocketTimeoutException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.analytical.news.entities.Article;
import com.analytical.news.entities.Author;
import com.analytical.news.entities.Source;
import com.analytical.news.model.NewsApiResponse;
import com.analytical.news.repository.ArticleRepository;
import com.analytical.news.repository.AuthorRepository;
import com.analytical.news.repository.SourceRepository;

@Service
public class PersistenceService {
	
	Logger logger = LoggerFactory.getLogger(PersistenceService.class);
	
	@Autowired
	ArticleRepository articleRespository;
	
	@Autowired
	SourceRepository sourceRepository;
	
	@Autowired
	AuthorRepository authorRepository;
	
	public List<Article> saveArticles(String topic, ResponseEntity<NewsApiResponse> response) {
		List<Article> articleList = response.getBody().getArticles();
		Connection webConnection = null;
		List<Article> articleListToSave = new ArrayList<>();
		for(Article article : articleList) {
			Author author = new Author();
			if(null != article.getAuthor()){
				String[] name = article.getAuthor().split(" ");
				if(name.length > 1) {
					author.setFirst_name(name[0]);
					author.setLast_name(name[name.length-1]);
				}else {
					author.setFirst_name(name[0]);
					author.setLast_name("N/A");
				}
			}else {
				author.setLast_name("Anonymous");
				author.setFirst_name("Anonymous");
			}
			Example<Author> authorExample = Example.of(author); 
			Optional<Author> dbAuthor = authorRepository.findOne(authorExample);
			if(dbAuthor.isEmpty()) {
				authorRepository.save(author);
				article.setNewsAuthor(author);
			}else {
				article.setNewsAuthor(dbAuthor.get());
			}
			Example<Source> sourceExample = Example.of(article.getSource());
			Optional<Source> dbSource = sourceRepository.findOne(sourceExample);
			if(dbSource.isEmpty()) {
				sourceRepository.save(article.getSource());
			}else if(dbSource.isPresent()){
				article.setSource(dbSource.get());
			}
			article.setDate_published(OffsetDateTime.parse(article.getPublishedAt()));
			article.setUser_search_param(topic);
			
			getArticleContentFromWebPage(articleListToSave, article);
		}
		if(articleListToSave.size() > 0) {
			articleRespository.saveAll(articleListToSave);
		}
		return articleList;
	}

	private void getArticleContentFromWebPage(List<Article> articleListToSave, Article article) {
		Connection webConnection;
		webConnection = Jsoup.connect(article.getUrl());
		try {
			Document doc = webConnection.newRequest().get();
			Element body = doc.body();
			List<Element> listElements = body.getElementsByTag("p");
			StringBuilder articlePara = new StringBuilder();
			for(Element ele : listElements) {
				articlePara.append(ele.text());
				articlePara.append("\n");
			}
			article.setContent(articlePara.toString());
		}catch(HttpStatusException http) {
			logger.error("Http status error occured on search param %s with URI %s",article.getUser_search_param(),article.getUrl());
		}catch(SocketTimeoutException soc) {
			logger.error("Socket Timeout error status occured on search param %s with URI %s",article.getUser_search_param(),article.getUrl());
		}catch (Exception e) {
			logger.error("Error occured on search param %s with URI %s",article.getUser_search_param(),article.getUrl());
		}
		String url = articleRespository.findByUrl(article.getUrl());
		if(null == url) {
			articleListToSave.add(article);
		}
	}
}
