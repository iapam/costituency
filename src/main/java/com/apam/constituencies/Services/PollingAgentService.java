package com.apam.constituencies.Services;

import com.apam.constituencies.Dto.PollingAgentDto;
import com.apam.constituencies.Model.Director;
import com.apam.constituencies.Model.PollingAgent;
import com.apam.constituencies.Model.adminRole;
import com.apam.constituencies.Model.pollingStation;
import com.apam.constituencies.Repositories.DirectorRepository;
import com.apam.constituencies.Repositories.PollingAgentRepository;
import com.apam.constituencies.Repositories.PollingStationRepository;
import com.apam.constituencies.Security.PasswordEncode;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PollingAgentService {
    private final PasswordEncode encoder;
    private final DirectorService directorService;
    private final PollingStationRepository pollingStationRepository;
    private final PollingAgentRepository pollingAgentRepository;
    private final DirectorRepository directorRepository;

    public  String register(PollingAgentDto pollingAgentDto, Director director) {

        return directorService.registerPollingAgent(new PollingAgent(pollingAgentDto.getPollingNumber(),
                pollingAgentDto.getEmail(), pollingAgentDto.getName(), pollingAgentDto.getNumber(), pollingAgentDto.getPassword(), director, adminRole.USER));
    }

    public void registerpollingstation(pollingStation pollingstation,Director director) throws MessagingException, UnsupportedEncodingException {
        PollingAgent agent=pollingAgentRepository.findByPollingNumber(pollingstation.getPollingAgentid());
        pollingStation station=pollingStationRepository.findByPollingAgentid(pollingstation.getPollingAgentid());
        directorService.EmailSender(agent.getEmail(), pollingstation.getPollingAgentid(), "zin",director);
        System.out.println(agent.getEmail());

        if(agent!=null && station==null){
            pollingStationRepository.save(pollingstation);

        }else{
            throw new IllegalStateException("Registered agent with this id before giving polling station");
        }

    }
    public String changePassord(PollingAgent pollingAgent,String new_password,String old_password){
        if(encoder.PasswordEncoder().matches(old_password,pollingAgent.getPassword())){
            String encodepassword=encoder.PasswordEncoder().encode(new_password);
            directorRepository.updatePollingAgent(pollingAgent.getPollingNumber(),encodepassword);
            return "";
        }
        return "Incorrect password";

    }

}
