package com.finologee.apifinologeebank.core.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finologee.apifinologeebank.core.service.IbanCheckerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping(path = "${base_url}/swift", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class IbanCheckerController {

    /*    private final IbanCheckerService ibanCheckerService;

    *//*    @GetMapping(path = "/get_iban_info/{iban}", produces = {"application/json"})
        public ResponseEntity<?> getIbanInfo(@PathVariable(name = "iban") String iban) {
            Optional<Object> obj = this.ibanCheckerService.getIbanInfo(iban);
            if (obj.isPresent()) {
                if (obj.get() instanceof Iban) {
                    return ResponseEntity.ok((Iban) obj.get());
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.empty());
        }*//*

        @GetMapping(path = "/iban_checker/{iban}", produces = {"application/json"})
        public ResponseEntity<?> getIbanChecker(@PathVariable(name = "iban") String iban) throws IOException {
            URL url = new URL("https://api.api-ninjas.com/v1/iban?iban=DE16200700000532013000");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseStream);
            System.out.println(root.path("fact").asText());
            return null;
        }

        @GetMapping(path = "/iban_ch/{iban}", produces = {"application/json"})
        public ResponseEntity<?> getIbanCheckerZ(@PathVariable(name = "iban") String iban) throws IOException {
            // Define the URL



            String apiUrl = "https://api.api-ninjas.com/v1/iban?iban=DE16200700000532013000";

            // Create an HTTP client
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                // Create an HTTP GET request
                HttpGet httpGet = new HttpGet(apiUrl);
                // Set accept header
                httpGet.setHeader("accept", "application/json");
                httpGet.addHeader("X-Api-Key", "VpFOphlO6FEESVhfJoCG2A==R80yqAFetb2Hyz0c");

                // Execute the request and obtain the response
                try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                    // Read the response content
                    try (InputStream responseStream = response.getEntity().getContent()) {
                        // Parse JSON response
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode root = mapper.readTree(responseStream);

                        // Print the desired JSON field
                        System.out.println(root.path("fact").asText());
                        return root;
                    }
                }
            }
            return null;
        }*/
}
