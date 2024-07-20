package com.work.client.service;

import com.work.client.dto.MedicalDTO;

/**
 *
 * @author linux
 */
public interface Client {

    MedicalDTO callRestTemplate(Integer userId, Integer page);
    
    MedicalDTO callFeign(Integer userId, Integer page);
}
