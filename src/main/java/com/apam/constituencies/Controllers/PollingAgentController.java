package com.apam.constituencies.Controllers;

import com.apam.constituencies.Dto.PollingAgentDto;
import com.apam.constituencies.Dto.PollingStationRequest;
import com.apam.constituencies.Model.Director;
import com.apam.constituencies.Model.PollingAgent;
import com.apam.constituencies.Services.PollingAgentService;
import com.apam.constituencies.Services.PollingStationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class PollingAgentController {
    private final PollingAgentService pollingAgentservice;
    private final PollingStationService pollingStationService;

    @PostMapping("/registerPollingAgent")
    public String RegisterPollingAgent(@ModelAttribute PollingAgentDto pollingAgentDto, Model model){
        Director director= (Director) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String pollingAgent=pollingAgentservice.register(pollingAgentDto,director);
        System.out.println("returing computation "+pollingAgent);
        model.addAttribute("pollingAgent",pollingAgentDto);
            return pollingAgent;

    }
    @GetMapping("/registerPage")
    public String RegisterPage(){

        return "register";
    }
    @GetMapping("/registerpolllingPage")
    public String RegisterpollingstationPage(){

        return "registerPollingstationpage";
    }
    @PostMapping("/registerpollingstation")
    public String RegisterPollingStation(@ModelAttribute PollingStationRequest pollingStationRequest) throws MessagingException, UnsupportedEncodingException {
        Director director= (Director) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pollingStationService.registerPollingStation(pollingStationRequest,director);

      return "";
    }

}
