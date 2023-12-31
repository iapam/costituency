package com.apam.constituencies.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class pollingStation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pollingAgentid;
    private String pollingstationid;
    private String pollingstationname;
    private String pollingstationtown;
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
    private String presidentialstatus;
    private String parliamentarystatus;
    @ManyToOne
    @JoinColumn
    private Director director;

    public pollingStation(String pollingstationid,
                          String pollingstationname,
                          String pollingstationtown,
                          String PollingAgentid,
                          String presidentialstatus,
                          String parliamentarystatus,
                          Director director){
        this.pollingstationid = pollingstationid;
        this.pollingstationname = pollingstationname;
        this.pollingstationtown = pollingstationtown;
        this.pollingAgentid=PollingAgentid;
        this.presidentialstatus=presidentialstatus;
        this.director=director;
        this.parliamentarystatus=parliamentarystatus;
    }
}
