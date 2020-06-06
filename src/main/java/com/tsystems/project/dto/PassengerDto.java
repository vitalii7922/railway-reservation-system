package com.tsystems.project.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * author Vitalii Nefedov
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto implements Serializable {

    private long id;

    private String firstName;

    private String secondName;

    private LocalDate birthDate;
}


