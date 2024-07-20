package com.work.client.util;

import com.work.client.dto.MedicalDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author linux
 */
@Component
public class MedicalRestTemplateImpl implements MedicalRestTemplate {

    private final RestTemplate restTemplate;

    public MedicalRestTemplateImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
           
    @Override
    public MedicalDTO callMedical(Integer userId, Integer page) {
        String url = String.format("https://jsonmock.hackerrank.com/api/medical_records?userId=%d&page=%d", userId, page);
        return restTemplate.getForObject(url, MedicalDTO.class);
    }

    
}
