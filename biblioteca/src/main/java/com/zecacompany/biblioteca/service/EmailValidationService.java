package com.zecacompany.biblioteca.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.annotation.JsonProperty;

@Service
public class EmailValidationService {

    @Value("${invertexto.api.token}")
    private String apiToken;
    private final RestTemplate restTemplate;
    private static final String EMAIL_VALIDATION_URL = "https://api.invertexto.com/v1/email-validator/";

    public EmailValidationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String obterUrlRequisicao(String apiUrl, String apiToken, String email) {
        return apiUrl + email + "?" + "token=" + apiToken;
    }

    public boolean validarEmail(String email) {
        String url = obterUrlRequisicao(EMAIL_VALIDATION_URL, apiToken, email);
        EmailValidationResponse response = restTemplate.getForObject(url, EmailValidationResponse.class);
        return response != null && response.isValidFormat() && response.isValidMx();
    }


    public static class EmailValidationResponse {
        @JsonProperty("email")
        private String email;

        @JsonProperty("valid_format")
        private boolean validFormat;

        @JsonProperty("valid_mx")
        private boolean validMx;

        @JsonProperty("disposable")
        private boolean disposable;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isValidFormat() {
            return validFormat;
        }

        public void setValidFormat(boolean validFormat) {
            this.validFormat = validFormat;
        }

        public boolean isValidMx() {
            return validMx;
        }

        public void setValidMx(boolean validMx) {
            this.validMx = validMx;
        }

        public boolean isDisposable() {
            return disposable;
        }

        public void setDisposable(boolean disposable) {
            this.disposable = disposable;
        }
    }

}

