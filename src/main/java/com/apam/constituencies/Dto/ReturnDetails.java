package com.apam.constituencies.Dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReturnDetails {
    private String pollingNumber;
    private String Pollingstationname;
    private String pollingagentname;
    private String pollingagentnumber;
    private Integer ndcpresidentialvotes;
    private Integer npppresidentialvotes;
    private Integer cpppresidentialvotes;
    private Integer ndppresidentialvotes;
    private Integer ppppresidentialvotes;
    private Integer pncpresidentialvotes;
    private Integer inppresidentialvotes;
    private Integer presidentiarejectedvotes;
    private Integer presidentiatotalvotescast;
    private Integer presidentiavalidvotes;
    private double ndcpresidentialpercentage;
    private double npppresidentialpercentage;
    private String pollingstationtown;
}
