package com.tsystems.project.web;

import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.dto.TrainDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public final class GlobalController {
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("trainDto", new TrainDto());
        model.addAttribute("passengerTrainDto", new PassengerTrainDto());
    }
}
