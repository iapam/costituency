package com.apam.constituencies.Services;

import com.apam.constituencies.Dto.EditVotes;
import com.apam.constituencies.Dto.RegisterParliamentaryCandidateDto;
import com.apam.constituencies.Dto.ReturnDetails;
import com.apam.constituencies.Dto.ValidandPercentage;
import com.apam.constituencies.Model.*;
import com.apam.constituencies.Repositories.DirectorRepository;
import com.apam.constituencies.Repositories.PaliamentaryCandidateRepository;
import com.apam.constituencies.Repositories.PollingAgentRepository;
import com.apam.constituencies.Repositories.PollingStationRepository;
import com.apam.constituencies.Security.PasswordEncode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService implements UserDetailsService {
    private final DirectorRepository directorRepository;
    private final PollingAgentRepository pollingAgentRepository;
    private final PasswordEncode passwordEncode;
    private final PollingStationRepository pollingStationRepository;
    private final ValidandPercentage validandPercentage;
    private final JavaMailSender javaMailSender;
    private final PaliamentaryCandidateRepository paliamentaryCandidateRepository;
    @Value("${spring.mail.username}")
    private String from;
    public String signup(Director director) {
         directorRepository.save(director);
    return "registered";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return directorRepository.findDirectorByDirectorid(username)
                .orElseThrow(()->new IllegalStateException("We have not register you"));
    }

    public String registerPollingAgent(PollingAgent pollingAgent){
       PollingAgent pollingAgent1=pollingAgentRepository.findByPollingNumber(pollingAgent.getPollingNumber());
       pollingStation Pollingstation=pollingStationRepository.findByPollingAgentid(pollingAgent.getPollingNumber());
       if(pollingAgent1!=null && Pollingstation!=null){
           return "errorPage ";
       }else if (pollingAgent1!=null && Pollingstation==null){
           return "registerPollingstationpage";
       }else {
           String encodepassword=passwordEncode.PasswordEncoder().encode(pollingAgent.getPassword());
           pollingAgent.setPassword(encodepassword);
           directorRepository.save(new Director("user",pollingAgent.getPollingNumber(),pollingAgent.getPassword(), adminRole.USER));
           pollingAgentRepository.save(pollingAgent);

           return "registerPollingstationpage";
       }

    }

    public List<ReturnDetails> getDetails(Director director) {
   List<ReturnDetails> details=new ArrayList<>();

    List<pollingStation>recordagents=pollingStationRepository.findAllByDirectorAndPresidentialstatus(director,"record");

     for (pollingStation station: recordagents){
         double ndcvotes=station.getNdcpresidentialvotes();
         double totalvalidvotes=station.getPresidentialvalidvotes();
         double ndc_presidential_percentage=ndcvotes/totalvalidvotes*100;
         double nppvotes=station.getNpppresidentialvotes();
         double npp_presidential_percentage=nppvotes/totalvalidvotes*100;
         ReturnDetails individualdetails=new ReturnDetails();
         PollingAgent agent=pollingAgentRepository.findByPollingNumber(station.getPollingAgentid());
       individualdetails.setPollingstationname(station.getPollingstationname());
       individualdetails.setNdcpresidentialvotes(station.getNdcpresidentialvotes());
       individualdetails.setPollingNumber(station.getPollingAgentid());
       individualdetails.setPresidentiarejectedvotes(station.getPresidentialrejectedvotes());
       individualdetails.setPresidentiatotalvotescast(station.getPresidentialtotalvotescast());
       individualdetails.setPresidentiavalidvotes(station.getPresidentialvalidvotes());
       individualdetails.setPollingagentname(agent.getName());
       individualdetails.setPollingagentnumber(agent.getNumber());
       individualdetails.setPollingstationtown(station.getPollingstationtown());
       individualdetails.setNpppresidentialvotes(station.getNpppresidentialvotes());
       individualdetails.setNdcpresidentialpercentage(ndc_presidential_percentage);
       individualdetails.setNpppresidentialpercentage(npp_presidential_percentage);
       details.add(individualdetails);
     }
        System.out.println(details);
        return details;
    }

    public Integer totalndcvotes(List<ReturnDetails> detailsList) {
        Integer ndcsum=0;
      for(ReturnDetails details:detailsList){
          ndcsum=ndcsum+details.getNdcpresidentialvotes();
      }
      return ndcsum;
    }
    public Integer totalNppvotes(List<ReturnDetails> detailsList) {
        Integer totalnppvotes=0;
        for(ReturnDetails details:detailsList){
            totalnppvotes=totalnppvotes+details.getNpppresidentialvotes();
        }
        return totalnppvotes;
    }
    public Integer totalVotingLocation(Director director){
        List<pollingStation> pollingStations=pollingStationRepository.findAllByDirector(director);
        return pollingStations.size();
    }
    public Integer Reported_Voting_location(Director director){
        List<pollingStation>recordagents=pollingStationRepository.findAllByDirectorAndPresidentialstatus(director,"record");
        return recordagents.size();
    }
    public Integer Pending_Location(Director director){
        List<pollingStation>recordagents=pollingStationRepository.findAllByDirectorAndPresidentialstatus(director,"pending");
        return recordagents.size();
    }
    public Integer total_votes_cast(Director director){
        Integer total_votes_cast=0;
        List<pollingStation>recordagents=pollingStationRepository.findAllByDirectorAndPresidentialstatus(director,"record");
        for(pollingStation station:recordagents){
          total_votes_cast=total_votes_cast+station.getPresidentialtotalvotescast();
        }
        return total_votes_cast;
    }
    public Integer rejected_votes(Director director){
        Integer total_rejected_votes=0;
        List<pollingStation>recordagents=pollingStationRepository.findAllByDirectorAndPresidentialstatus(director,"record");
        for(pollingStation station:recordagents){
            total_rejected_votes=total_rejected_votes+station.getPresidentialrejectedvotes();
        }
        return total_rejected_votes;
    }
    public ValidandPercentage total_valid_votes_and_percentages(Director director){
        Integer total_valid_votes=0;
        double total_ndc=0;
        double total_npp=0;
        List<pollingStation>recordagents=pollingStationRepository.findAllByDirectorAndPresidentialstatus (director,"record");
        for(pollingStation station:recordagents){
            total_valid_votes=total_valid_votes+station.getPresidentialvalidvotes();
            total_ndc=total_ndc+station.getNdcpresidentialvotes();
            total_npp=total_npp+station.getNpppresidentialvotes();
        }
        validandPercentage.setNdc_total_percentage(total_ndc/total_valid_votes*100);
        validandPercentage.setNpp_total_percentage(total_npp/total_valid_votes*100);
        validandPercentage.setTotal_valid_votes(total_valid_votes);
        return validandPercentage;
    }
    public void EmailSender(String to,String polling_station_id,String password,Director director) throws MessagingException, UnsupportedEncodingException {
           String body="This is your polling station ID and password"+"\n";
           body+="Login to the app and change the password to your own password"+"\n";
           body+="Polling station ID: "+polling_station_id+"\n";
           body+="Password: "+password;
           MimeMessage mimeMessage=javaMailSender.createMimeMessage();
           MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
           mimeMessageHelper.setFrom(from,director.getName());
           mimeMessageHelper.setTo(to);
           mimeMessageHelper.setText(body);
           mimeMessageHelper.setSubject("Welcome");
           mimeMessageHelper.setReplyTo("no-reply@gmail.com");
           javaMailSender.send(mimeMessage);

    }
    public String editVotes(EditVotes editVotes,String id,String status){
        if(editVotes.getNdcvotes()!=null){
            pollingStationRepository.updateNdcVotes(id,editVotes.getNdcvotes(),status);
        }
        if(editVotes.getNppvotes()!=null){
            pollingStationRepository.updatenppVotes(id,editVotes.getNppvotes(),status);
        }
        if(editVotes.getTotalvotescast()!=null){
            pollingStationRepository.updatetotalvotescast(id,editVotes.getTotalvotescast(),status);
        }
        if(editVotes.getValidvotes()!=null){
            pollingStationRepository.updatevalidvotes(id,editVotes.getValidvotes(),status);
        }
        if(editVotes.getRejectedvotes()!=null){
            pollingStationRepository.updaterejectedvotes(id,editVotes.getRejectedvotes(),status);
        }
        return "all users page";
    }
    public void registerPaliamentaryCandidate(RegisterParliamentaryCandidateDto registerParliamentaryCandidateDto,
                                              Director director,MultipartFile image) throws IOException {
        String image_url=image.getOriginalFilename();
        String upload_image="src/main/resources/static/images/"+image_url;
        File image_file=new File(upload_image);
        FileOutputStream fileOutputStream=new FileOutputStream(image_file);
        BufferedOutputStream outputStream=new BufferedOutputStream(fileOutputStream);
        outputStream.write(image.getBytes());
        paliamentaryCandidateRepository.save(new ParliamentaryCandidates(registerParliamentaryCandidateDto.getCandidate_name(),image_url,
                registerParliamentaryCandidateDto.getRegion(),registerParliamentaryCandidateDto.getCandidate_party(),director));


    }

}
