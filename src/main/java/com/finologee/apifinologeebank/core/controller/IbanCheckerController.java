package com.finologee.apifinologeebank.core.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${base_url}/swift", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class IbanCheckerController {

}
