package com.work.client.service;

import com.work.client.dto.MedicalDTO;
import com.work.client.util.MedicalFeign;
import com.work.client.util.MedicalRestTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author linux
 */
@Service
@Slf4j
public class ClientImpl implements Client {

    private final MedicalRestTemplate medicalRestTemplate;
    private final MedicalFeign medicalFeign;

    public ClientImpl(MedicalRestTemplate medicalRestTemplate, MedicalFeign medicalFeign) {
        this.medicalRestTemplate = medicalRestTemplate;
        this.medicalFeign = medicalFeign;
    }

    @CircuitBreaker(name = "medicalRestTemplate", fallbackMethod = "fallbackRestTemplate")
    @Override
    public MedicalDTO callRestTemplate(Integer userId, Integer page) {
        return this.medicalRestTemplate.callMedical(userId, page);
    }

    public MedicalDTO fallbackRestTemplate(Throwable t) {
        log.error("Error: ", t);
        MedicalDTO error = new MedicalDTO();
        error.setData(new ArrayList<>());
        error.setPage(0);
        error.setPerPage(0);
        error.setTotal(0);
        error.setTotalPages(0);
        return error;
    }

    @CircuitBreaker(name = "medicalFeign", fallbackMethod = "fallbackFeign")
    @Override
    public MedicalDTO callFeign(Integer userId, Integer page) {
        return this.medicalFeign.callMedical(userId, page);
    }

    public MedicalDTO fallbackFeign(Throwable t) {
        log.error("Error: ", t);
        MedicalDTO error = new MedicalDTO();
        error.setData(new ArrayList<>());
        error.setPage(0);
        error.setPerPage(0);
        error.setTotal(0);
        error.setTotalPages(0);
        return error;
    }

}
