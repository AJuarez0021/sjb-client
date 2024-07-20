package com.work.client.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author linux
 */
@Setter
@Getter
public class DataDTO {

    private Integer id;

    private Long timestamp;

    private Integer userId;

    private String userName;
    
    private String userDob;
    
    private DiagnosisDTO diagnosis;
    
    private VitalsDTO vitals;
    
    private DoctorDTO doctor;
    
    private MetaDTO meta;
}
