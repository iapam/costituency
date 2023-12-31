package com.apam.constituencies.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterParliamentaryCandidateDto {
    private String candidate_name;
    private String candidate_image_url;
    private String region;
    private String candidate_party;
    private MultipartFile multipartFile;

    public RegisterParliamentaryCandidateDto(String candidate_name, String region, String candidate_party,String candidate_image_url) {
        this.candidate_name = candidate_name;
        this.region = region;
        this.candidate_party = candidate_party;
        this.candidate_image_url=candidate_image_url;
    }


}
