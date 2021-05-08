package com.prabhat.springit.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String body;

    //Link
    @ManyToOne
    private Link link;

}
