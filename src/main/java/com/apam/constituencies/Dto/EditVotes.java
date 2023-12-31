package com.apam.constituencies.Dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EditVotes {
    private Integer ndcvotes;
    private Integer nppvotes;
    private Integer rejectedvotes;
    private Integer totalvotescast;
    private Integer validvotes;
}
