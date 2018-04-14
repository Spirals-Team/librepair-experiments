package com.prussia.play.spring.web.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/optional")
public class SampleController {

	@RequestMapping(value = "dopt", produces = "text/plain")
	public @ResponseBody String requestParaAsOptional(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "id") Optional<LocalDate> localDate) {
		final StringBuilder result = new StringBuilder("id:");
		localDate.ifPresent(value -> result.append(value.toString()));
		return result.toString();
	}

}
