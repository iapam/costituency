package com.apam.constituencies.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PollingStationdto {
    private final String pollingNumber;
    private final String constituencyid;
    private Integer ndcpresidentialvotes;
    private Integer cpppresidentialvotes;
    private Integer ndppresidentialvotes;
    private Integer ppppresidentialvotes;
    private Integer pncpresidentialvotes;
    private Integer inppresidentialvotes;
    private Integer npppresidentialvotes;
    private Integer ndcparliamentarylvotes;
    private Integer nppparliamentaryvotes;
    private Integer inpparliamentaryvotes;
    private Integer pncparliamentaryvotes;
    private Integer pppparliamentaryvotes;
    private Integer ndpparliamentaryvotes;
    private Integer cppparliamentaryvotes;
    private Integer presidentialrejectedvotes;
    private Integer presidentialtotalvotescast;
    private Integer presidentialvalidvotes;
    private Integer parliamentaryrejectedvotes;
    private Integer parliamentarytotalvotescast;
    private Integer parliamentaryvalidvotes;
}
