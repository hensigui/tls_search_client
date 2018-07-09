package com.tolovesoul.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tolovesoul.service.SearchService;

@RestController
public class SearchController {
	@Autowired
	private SearchService searchService;
	@GetMapping("/test")
	public String testSearch() {
		return searchService.search("fate", "0");
	}
}
