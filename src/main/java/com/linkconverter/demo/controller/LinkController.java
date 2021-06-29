package com.linkconverter.demo.controller;

import com.linkconverter.demo.model.DeepLinkRequest;
import com.linkconverter.demo.model.WebUrlRequest;
import com.linkconverter.demo.service.LinkService;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api")
@RequiredArgsConstructor
public class LinkController {

	private final LinkService linkService;

	@GetMapping("/deeplink")
	public ResponseEntity<String> convertToDeepLink(@Valid @RequestBody DeepLinkRequest deepLinkRequest) {
		String deepLink = linkService.convertToDeepLink(deepLinkRequest.getWebUrl());
		return new ResponseEntity<>(deepLink, HttpStatus.OK);
	}

	@GetMapping("/weburl")
	public ResponseEntity<String> convertToWebUrl(@Valid @RequestBody WebUrlRequest webUrlRequest) {
		String deepLink = linkService.convertToWebUrl(webUrlRequest.getDeepLink());
		return new ResponseEntity<>(deepLink, HttpStatus.OK);
	}

}
