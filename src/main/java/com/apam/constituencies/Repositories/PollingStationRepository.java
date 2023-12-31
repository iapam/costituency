package com.apam.constituencies.Repositories;

import com.apam.constituencies.Model.Director;
import com.apam.constituencies.Model.pollingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PollingStationRepository extends JpaRepository<pollingStation,String> {
    pollingStation findByPollingAgentid(String pollingid);
    List<pollingStation>findAllByDirectorAndPresidentialstatus(Director director, String presidentialstatus);

    List<pollingStation> findAllByDirector(Director director);
    @Modifying
    @Transactional
    @Query("update pollingStation set ndcpresidentialvotes=:votes where pollingAgentid=:pollingstationid and presidentialstatus=:presidentialstatus")
    public void updateNdcVotes(String pollingstationid, Integer votes,String presidentialstatus);

    @Modifying
    @Transactional
    @Query("update pollingStation set npppresidentialvotes=:votes where pollingAgentid=:pollingstationid and presidentialstatus=:presidentialstatus")
    public void updatenppVotes(String pollingstationid, Integer votes,String presidentialstatus);
    @Modifying
    @Transactional
    @Query("update pollingStation set presidentialrejectedvotes=:presidentialrejectedvotes where pollingAgentid=:pollingstationid and presidentialstatus=:presidentialstatus")
    public void updaterejectedvotes(String pollingstationid, Integer presidentialrejectedvotes,String presidentialstatus);

    @Modifying
    @Transactional
    @Query("update pollingStation set presidentialtotalvotescast=:presidentialtotalvotescast where pollingAgentid=:pollingstationid and presidentialstatus=:presidentialstatus")
    public void updatetotalvotescast(String pollingstationid, Integer presidentialtotalvotescast,String presidentialstatus);

    @Modifying
    @Transactional
    @Query("update pollingStation set presidentialvalidvotes=:presidentialvalidvotes where pollingAgentid=:pollingstationid and presidentialstatus=:presidentialstatus")
    public void updatevalidvotes(String pollingstationid, Integer presidentialvalidvotes,String presidentialstatus);
    @Transactional
    @Modifying
    @Query("update pollingStation set ndcpresidentialvotes=:ndcpresidentialvotes,cpppresidentialvotes=:cpppresidentialvotes," +
            "ndppresidentialvotes=:ndppresidentialvotes,ppppresidentialvotes=:ppppresidentialvotes," +
            "pncpresidentialvotes=:pncpresidentialvotes,inppresidentialvotes=:inppresidentialvotes," +
            "presidentialstatus=:record,npppresidentialvotes=:npppresidentialvotes,presidentialrejectedvotes=:presidentialrejectedvotes," +
            "presidentialtotalvotescast=:presidentialtotalvotescast,presidentialvalidvotes=:presidentialvalidvotes" +
            " where pollingAgentid=:pollingNumber and director=:director and presidentialstatus=:pending")
    int updateVotes(String pollingNumber, Director director, Integer ndcpresidentialvotes,String pending,String record, Integer npppresidentialvotes,
                     Integer presidentialrejectedvotes, Integer presidentialtotalvotescast,
                     Integer presidentialvalidvotes, Integer cpppresidentialvotes,
                     Integer ndppresidentialvotes, Integer ppppresidentialvotes,
                     Integer pncpresidentialvotes, Integer inppresidentialvotes
    );

    @Transactional
    @Modifying
    @Query("update pollingStation set ndcparliamentarylvotes=:ndcparliamentarylvotes," +
            "cppparliamentaryvotes=:cppparliamentaryvotes,ndpparliamentaryvotes=:ndpparliamentaryvotes," +
            "pppparliamentaryvotes=:pppparliamentaryvotes,parliamentarystatus=:parliamentarystatus," +
            "nppparliamentaryvotes=:nppparliamentaryvotes,parliamentaryrejectedvotes=:parliamentaryrejectedvotes," +
            "parliamentarytotalvotescast=:parliamentarytotalvotescast,parliamentaryvalidvotes=:parliamentaryvalidvotes," +
            "inpparliamentaryvotes=:inpparliamentaryvotes,pncparliamentaryvotes=:pncparliamentaryvotes " +
            "where pollingAgentid=:pollingNumber and director=:director and parliamentarystatus=:pending")
    int updateParliamentaryvotes(String pollingNumber, Director director,
                                 Integer ndcparliamentarylvotes,Integer cppparliamentaryvotes,
                                 Integer ndpparliamentaryvotes,Integer pppparliamentaryvotes,String parliamentarystatus,
                                 Integer nppparliamentaryvotes,Integer parliamentaryrejectedvotes,Integer parliamentarytotalvotescast,
                                 Integer parliamentaryvalidvotes,String pending,Integer inpparliamentaryvotes,Integer pncparliamentaryvotes);
}
