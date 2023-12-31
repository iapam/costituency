package com.apam.constituencies.Dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class PollingAgentDto {
    private final String pollingNumber;
    private final String email;
    private final String name;
    private final String number;
    private final String password;
}
