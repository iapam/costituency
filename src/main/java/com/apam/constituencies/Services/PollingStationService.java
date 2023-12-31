package com.apam.constituencies.Services;

import com.apam.constituencies.Dto.PollingStationRequest;
import com.apam.constituencies.Dto.PollingStationdto;
import com.apam.constituencies.Model.Director;
import com.apam.constituencies.Model.PollingAgent;
import com.apam.constituencies.Model.pollingStation;
import com.apam.constituencies.Repositories.DirectorRepository;
import com.apam.constituencies.Repositories.PollingAgentRepository;
import com.apam.constituencies.Repositories.PollingStationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PollingStationService {
    private final PollingAgentService pollingAgentService;
    private final DirectorRepository directorRepository;
    private final PollingStationRepository pollingStationRepository;
    private final PollingAgentRepository agentRepository;
    public String registerPollingStation(PollingStationRequest pollingStationRequest,Director director) throws MessagingException, UnsupportedEncodingException {
         String status="pending";
        pollingAgentService.registerpollingstation(new pollingStation(pollingStationRequest.getPollingstationid()
        ,pollingStationRequest.getPollingstationname()
        ,pollingStationRequest.getPollingstationtown()
        ,pollingStationRequest.getPollingAgentid(),status,status,director),director);
     return "agent successfully registered";
    }
    public String saveVotes(PollingStationdto pollingStationdto) {

        Director director = directorRepository.findByConstituencyid(pollingStationdto.getConstituencyid());
        System.out.println("The director "+director.getDirectorid());
        pollingStationRepository.updateVotes(pollingStationdto.getPollingNumber(), director,
                pollingStationdto.getNdcpresidentialvotes(), "pending",
                "record",pollingStationdto.getNpppresidentialvotes(),pollingStationdto.getPresidentialrejectedvotes(),
                pollingStationdto.getPresidentialtotalvotescast(),pollingStationdto.getPresidentialvalidvotes(),
                pollingStationdto.getCpppresidentialvotes(),pollingStationdto.getNdppresidentialvotes(),
                pollingStationdto.getPpppresidentialvotes(),pollingStationdto.getPncpresidentialvotes(),
                pollingStationdto.getInppresidentialvotes()
        );
        return "parliamentary_page";

    }
    public String saveParliamentary(PollingStationdto pollingStationdto){
        Director agent= (Director) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PollingAgent pollingAgent=agentRepository.findByPollingNumber(agent.getDirectorid());
        System.out.println("the number "+pollingAgent.getDirector().getConstituencyid());
        Director director = agentRepository.findDirector(pollingAgent.getPollingNumber());
        System.out.println("The director "+director.getEmail());
        pollingStationRepository.updateParliamentaryvotes(pollingAgent.getPollingNumber(),director,
                pollingStationdto.getNdcparliamentarylvotes(),pollingStationdto.getCppparliamentaryvotes(),
                pollingStationdto.getNdpparliamentaryvotes(),pollingStationdto.getPppparliamentaryvotes(),
                "record",pollingStationdto.getNppparliamentaryvotes(),
                pollingStationdto.getParliamentaryrejectedvotes(),pollingStationdto.getParliamentarytotalvotescast(),
                pollingStationdto.getParliamentaryvalidvotes(),"pending",pollingStationdto.getInpparliamentaryvotes(),
                pollingStationdto.getPncparliamentaryvotes());
        return "homepage";
    }
}
