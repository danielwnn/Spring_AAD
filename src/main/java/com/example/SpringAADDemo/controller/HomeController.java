package com.example.SpringAADDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@Autowired
	OAuth2AuthorizedClientService oauthClientService;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/secure")
	public String secure(Model model) {
		// Get the current authenticated user's auth token 
		OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		
		// Get user details (user name, claims, etc.) from the auth token
		DefaultOidcUser userDetails = (DefaultOidcUser) authToken.getPrincipal();
		
		// Exchange the auth token for an access token that can be used to access Microsoft Graph APIs
		OAuth2AuthorizedClient client = oauthClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
		String accessToken = client.getAccessToken().getTokenValue();

		model.addAttribute("userName", userDetails.getGivenName());
		model.addAttribute("idToken", userDetails.getIdToken().getTokenValue());
		model.addAttribute("accessToken", accessToken);
		model.addAttribute("claims", userDetails.getAttributes());
		
		return "secure";
	}

}
