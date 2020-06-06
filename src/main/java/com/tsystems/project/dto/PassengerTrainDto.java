package com.tsystems.project.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * author Vitalii Nefedov
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassengerTrainDto implements Serializable {

    private int trainNumber;

    private int seats;

    private String originStation;

    private String destinationStation;

    private String departureTime;

    @NotEmpty(message = "Incorrect first name")
    private String firstName;

    @NotEmpty(message = "Incorrect second name")
    private String secondName;

    private String birthDate;
}
