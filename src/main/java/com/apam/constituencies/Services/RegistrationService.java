package com.apam.constituencies.Services;

import com.apam.constituencies.Dto.RegistrationRequest;
import com.apam.constituencies.Model.Director;
import com.apam.constituencies.Model.adminRole;
import com.apam.constituencies.Repositories.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
private final DirectorService directorService;
private final DirectorRepository directorRepository;
private final BCryptPasswordEncoder passwordEncode;
    public String register(RegistrationRequest registrationRequest) {
       Director director=directorRepository.findByDirectorid(registrationRequest.getDirectorid());
       if(director!=null){
        return "User with this email "+registrationRequest.getEmail()+" already exist";
       }
       String encodepass=passwordEncode.encode(registrationRequest.getPassword());
       return directorService.signup(new Director(registrationRequest.getConstituencyid(),
               registrationRequest.getDirectorid(),
               registrationRequest.getConstituency_name(),
               registrationRequest.getRegion(),
               registrationRequest.getEmail(),
               registrationRequest.getName(),
               registrationRequest.getNumber(),encodepass, adminRole.ADMIN));
    }

}
