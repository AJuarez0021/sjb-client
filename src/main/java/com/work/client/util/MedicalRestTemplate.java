package com.work.client.util;

import com.work.client.dto.MedicalDTO;

/**
 *
 * @author linux
 */
public interface MedicalRestTemplate {

    MedicalDTO callMedical(Integer userId, Integer page);
}
