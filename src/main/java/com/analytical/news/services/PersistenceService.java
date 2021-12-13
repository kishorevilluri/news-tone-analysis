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

import com.analytical.news.entities.ArticleEntity;
import com.analytical.news.entities.ArticleToneEntity;
import com.analytical.news.entities.AuthorEntity;
import com.analytical.news.entities.SentenceToneEntity;
import com.analytical.news.entities.SourceEntity;
import com.analytical.news.entities.ToneEntity;
import com.analytical.news.model.NewsApiResponse;
import com.analytical.news.model.SentenceTone;
import com.analytical.news.model.Tone;
import com.analytical.news.model.ToneAnalyzerResponse;
import com.analytical.news.repository.ArticleRepository;
import com.analytical.news.repository.ArticleToneRepository;
import com.analytical.news.repository.AuthorRepository;
import com.analytical.news.repository.SentenceToneRepository;
import com.analytical.news.repository.SourceRepository;
import com.analytical.news.repository.ToneRepository;

@Service
public class PersistenceService {

	Logger logger = LoggerFactory.getLogger(PersistenceService.class);

	@Autowired
	ArticleRepository articleRespository;

	@Autowired
	SourceRepository sourceRepository;

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	ToneRepository toneRepository;

	@Autowired
	ArticleToneRepository articleToneRepository;

	@Autowired
	SentenceToneRepository sentenceToneRepository;

	public List<ArticleEntity> saveArticles(String topic, ResponseEntity<NewsApiResponse> response) {
		List<ArticleEntity> articleList = response.getBody().getArticles();
		List<ArticleEntity> articleListToSave = new ArrayList<>();
		for (ArticleEntity article : articleList) {
			AuthorEntity author = new AuthorEntity();
			if (null != article.getAuthor()) {
				String[] name = article.getAuthor().split(" ");
				if (name.length > 1) {
					author.setFirst_name(name[0]);
					author.setLast_name(name[name.length - 1]);
				} else {
					author.setFirst_name(name[0]);
					author.setLast_name("N/A");
				}
			} else {
				author.setLast_name("Anonymous");
				author.setFirst_name("Anonymous");
			}
			Example<AuthorEntity> authorExample = Example.of(author);
			Optional<AuthorEntity> dbAuthor = authorRepository.findOne(authorExample);
			if (dbAuthor.isEmpty()) {
				authorRepository.save(author);
				article.setNewsAuthor(author);
			} else {
				article.setNewsAuthor(dbAuthor.get());
			}
			Example<SourceEntity> sourceExample = Example.of(article.getSource());
			Optional<SourceEntity> dbSource = sourceRepository.findOne(sourceExample);
			if (dbSource.isEmpty()) {
				sourceRepository.save(article.getSource());
			} else if (dbSource.isPresent()) {
				article.setSource(dbSource.get());
			}
			article.setDate_published(OffsetDateTime.parse(article.getPublishedAt()));
			article.setUser_search_param(topic);

			getArticleContentFromWebPage(article);

			ArticleEntity dbArticle = articleRespository.findByUrl(article.getUrl());
			if (null == dbArticle) {
				articleListToSave.add(article);
			}
		}
		if (articleListToSave.size() > 0) {
			articleRespository.saveAll(articleListToSave);
		}
		return articleList;
	}

	public void saveTones(ResponseEntity<ToneAnalyzerResponse> response, ArticleEntity article) {
		ToneAnalyzerResponse res = response.getBody();
		ToneEntity toneEntity = new ToneEntity();

		ArticleEntity dbArticle = articleRespository.findByUrl(article.getUrl());
		List<ArticleToneEntity> art = articleToneRepository.findByArticleId(dbArticle.getId());
		if (null == art || art.isEmpty()) {
			for (Tone tone : res.getDocument_tone().getTones()) {
				ArticleToneEntity articleToneEntity = new ArticleToneEntity();
				articleToneEntity.setArticle(dbArticle);
				articleToneEntity.setScore(tone.getScore());
				toneEntity.setCategory(tone.getTone_name().toLowerCase());
				Example<ToneEntity> toneExample = Example.of(toneEntity);
				Optional<ToneEntity> dbTone = toneRepository.findOne(toneExample);
				articleToneEntity.setTone(dbTone.get());
				articleToneRepository.save(articleToneEntity);
			}
		}

		List<SentenceToneEntity> sent = sentenceToneRepository.findByArticleId(dbArticle.getId());
		if (null == sent || sent.isEmpty()) {
			for (SentenceTone sentenceTone : res.getSentences_tone()) {
				for (Tone tone : sentenceTone.getTones()) {
					SentenceToneEntity sentenceToneEntity = new SentenceToneEntity();
					sentenceToneEntity.setArticle(dbArticle);
					sentenceToneEntity.setScore(tone.getScore());
					toneEntity.setCategory(tone.getTone_name().toLowerCase());
					Example<ToneEntity> toneExample = Example.of(toneEntity);
					Optional<ToneEntity> dbTone = toneRepository.findOne(toneExample);
					sentenceToneEntity.setTone(dbTone.get());
					sentenceToneEntity.setSentence(sentenceTone.getText());
					sentenceToneRepository.save(sentenceToneEntity);
				}
			}
		}
	}

	private void getArticleContentFromWebPage(ArticleEntity article) {
		Connection webConnection;
		webConnection = Jsoup.connect(article.getUrl());
		try {
			Document doc = webConnection.newRequest().get();
			Element body = doc.body();
			List<Element> listElements = body.getElementsByTag("p");
			StringBuilder articlePara = new StringBuilder();
			for (Element ele : listElements) {
				articlePara.append(ele.text());
				articlePara.append("\n");
			}
			article.setContent(articlePara.toString());
		} catch (HttpStatusException http) {
			logger.error("Http status error occured on search param %s with URI %s", article.getUser_search_param(),
					article.getUrl());
		} catch (SocketTimeoutException soc) {
			logger.error("Socket Timeout error status occured on search param %s with URI %s",
					article.getUser_search_param(), article.getUrl());
		} catch (Exception e) {
			logger.error("Error occured on search param %s with URI %s", article.getUser_search_param(),
					article.getUrl());
		}
	}
}
