package com.apam.constituencies.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ParliamentaryCandidates {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String candidate_name;
    private String candidate_image_url;
    private String region;
    private String candidate_party;
    @ManyToOne
    @JoinColumn
    private Director director;

    public ParliamentaryCandidates(String candidate_name, String candidate_image_url, String region, String candidate_party, Director director) {
        this.candidate_name = candidate_name;
        this.candidate_image_url = candidate_image_url;
        this.region = region;
        this.candidate_party = candidate_party;
        this.director = director;
    }
}
