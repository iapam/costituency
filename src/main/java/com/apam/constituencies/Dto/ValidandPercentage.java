package com.apam.constituencies.Dto;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Component
public class ValidandPercentage {
    private Integer total_valid_votes;
    private double ndc_total_percentage;
    private double npp_total_percentage;
}
