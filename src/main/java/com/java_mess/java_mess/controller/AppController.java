package com.java_mess.java_mess.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java_mess.java_mess.dto.app.InspectAppResponse;
import com.java_mess.java_mess.model.App;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AppController extends BaseController {

	@GetMapping("/inspect")
	public ResponseEntity<InspectAppResponse> inspectApp(HttpServletRequest request) {
		App app = this.getAuthenticatedApp();
		log.info("class = {}, app = {}", app.getClass(), app);
		return ResponseEntity.ok(InspectAppResponse.builder().app(app).build());
	}
}
