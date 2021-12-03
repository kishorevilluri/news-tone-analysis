package com.analytical.news.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {
	
	@GetMapping(value="/{topic}")
	public ResponseEntity<String> newsArticles(@PathVariable("topic") String topic) {
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
}
