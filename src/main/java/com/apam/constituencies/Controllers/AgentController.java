package com.apam.constituencies.Controllers;


import com.apam.constituencies.Dto.OTPDto;
import com.apam.constituencies.Dto.PollingStationdto;
import com.apam.constituencies.Model.Director;
import com.apam.constituencies.Model.PollingAgent;
import com.apam.constituencies.Repositories.DirectorRepository;
import com.apam.constituencies.Repositories.PollingAgentRepository;
import com.apam.constituencies.Services.OTPGenerator;
import com.apam.constituencies.Services.PollingAgentService;
import com.apam.constituencies.Services.PollingStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AgentController {
    private final PollingAgentRepository agentRepository;
    private final PollingStationService pollingStationService;
   private final OTPGenerator otpGenerator;
    private final PollingAgentService agentService;
    private final DirectorRepository directorRepository;
    @GetMapping("/votespage")
    public String votespage(Model model,@ModelAttribute OTPDto otpDto) throws IOException {
       Director pollingAgent= (Director) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       PollingAgent mainagent=agentRepository.findByPollingNumber(pollingAgent.getDirectorid());
//       String verifyotp=otpGenerator.Verify_otp(otpDto.getOtpCode(),mainagent.getNumber() );
//        System.out.println("The message "+verifyotp);
//       if(!verifyotp.equals("Successful")) {
//           return "redirect:/sendotp";
//       }
        Director constituency = directorRepository.findByDirectorid(mainagent.getDirector().getDirectorid());
        model.addAttribute("pollingAgent", mainagent);
        model.addAttribute("contituency", constituency);
        return "VotesPage";

    }
    @PostMapping("record_votes")
    public String RecordVotes(@ModelAttribute PollingStationdto pollingStationdto){
        System.out.println("The votes cast "+pollingStationdto.getPresidentialtotalvotescast());
     pollingStationService.saveVotes(pollingStationdto);
     return "thanks";
    }
    @PostMapping("/recordparliamentary")
    public String Record_Parliamentary(@ModelAttribute PollingStationdto pollingStationdto){
        System.out.println("The vote is cpp "+pollingStationdto.getNdcparliamentarylvotes());
        pollingStationService.saveParliamentary(pollingStationdto);
        return "";
    }

    @GetMapping("/ibm/{new_password}/{old_password}")
    public String Change_Password(@PathVariable String new_password,@PathVariable String old_password){
        PollingAgent pollingAgent= (PollingAgent) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     String message= agentService.changePassord(pollingAgent,new_password,old_password);
     return "";
    }
    @GetMapping ("/sendotp")
    public String verify(@ModelAttribute OTPDto otpDto) throws IOException {
        Director director= (Director) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PollingAgent pollingagent=agentRepository.findByPollingNumber(director.getDirectorid());
        String otpexist=otpGenerator.SendOtp(pollingagent.getNumber());
        return "verifyotpPage";
    }
}
