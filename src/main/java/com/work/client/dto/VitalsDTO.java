package com.work.client.dto;

import lombok.Data;

/**
 *
 * @author linux
 */
@Data
public class VitalsDTO {

    private Integer bloodPressureDiastole;
    private Integer bloodPressureSystole;
    private Integer pulse;
    private Integer breathingRate;
    private Double bodyTemperature;

}
