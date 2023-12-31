package com.apam.constituencies.Controllers;

import com.apam.constituencies.Dto.*;
import com.apam.constituencies.Model.Director;
import com.apam.constituencies.Services.DirectorService;
import com.apam.constituencies.Services.RegistrationService;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class DirectorController {
    private final RegistrationService registrationService;
    private final DirectorService directorService;
    @PostMapping("/register")
    public String RegisterDirector(@RequestBody RegistrationRequest registrationRequest){
      return registrationService.register(registrationRequest);
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Director director= (Director) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ReturnDetails> detailsList=directorService.getDetails(director);
        Integer total_ndc_votes=directorService.totalndcvotes(detailsList);
        Integer total_npp_votes=directorService.totalNppvotes(detailsList);
        Integer total_voting_location=directorService.totalVotingLocation(director);
        Integer reported_voting_location=directorService.Reported_Voting_location(director);
        Integer pending_location=directorService.Pending_Location(director);
        Integer total_votes_cast=directorService.total_votes_cast(director);
        ValidandPercentage total_valid_votes_and_percentages=directorService.total_valid_votes_and_percentages(director);
        Integer total_rejected_votes=directorService.rejected_votes(director);
        model.addAttribute("total_ndc_votes",total_ndc_votes);
        model.addAttribute("total_npp_votes",total_npp_votes);
        model.addAttribute("total_valid_votes_and_percentages",total_valid_votes_and_percentages);
        model.addAttribute("total_voting_location",total_voting_location);
        model.addAttribute("reported_location",reported_voting_location);
        model.addAttribute("pending_location",pending_location);
        model.addAttribute("total_votes_cast",total_votes_cast);
        model.addAttribute("total_rejected_votes",total_rejected_votes);
        model.addAttribute("detailsList",detailsList);
        model.addAttribute("constituency_name",director.getConstituency_name()+" PROVISIONAL RESULTS PORTAL");
        System.out.println(total_valid_votes_and_percentages);
        return "constituency_dashboard";
    }
    @PostMapping("edit_votes/{id}")
    public String Edit_Votes(@RequestBody EditVotes editVotes,@PathVariable String id){
        directorService.editVotes(editVotes,id,"record");
        return "Edited";
    }
    @PostMapping("/registerparlia")
    public String registerPaliamentaryCanditates(@RequestParam MultipartFile candidate_image,
                                                 @RequestParam String candidate_name,
                                                 @RequestParam String canditate_party,
                                                 @RequestParam String region
    ) throws IOException {
        Director director= (Director) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    directorService.registerPaliamentaryCandidate(new RegisterParliamentaryCandidateDto(candidate_name,
            region,canditate_party,candidate_image.getOriginalFilename()),director,candidate_image);
        return "hello";
    }


}
