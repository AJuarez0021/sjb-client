package com.work.client.util;

import com.work.client.dto.MedicalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author linux
 */
@FeignClient(name = "medicalFeign", url = "https://jsonmock.hackerrank.com")
public interface MedicalFeign {

    @GetMapping("/api/medical_records")
    MedicalDTO callMedical(@RequestParam(name = "userId") Integer userId, @RequestParam(name = "page") Integer page);
}
