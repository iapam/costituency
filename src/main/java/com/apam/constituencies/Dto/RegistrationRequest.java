package com.apam.constituencies.Dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RegistrationRequest {
    private String constituencyid;
    private String directorid;
    private final String name;
    private final String email;
    private final String number;
    private final String password;
    private String constituency_name;
    private String region;
}
