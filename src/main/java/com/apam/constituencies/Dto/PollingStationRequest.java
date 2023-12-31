package com.apam.constituencies.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PollingStationRequest {
    private String pollingstationid;
    private String pollingstationname;
    private String pollingstationtown;
    private String pollingAgentid;
}
