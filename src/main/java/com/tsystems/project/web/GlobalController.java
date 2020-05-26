package com.tsystems.project.web;

import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.dto.TrainDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


/**
 * author Vitalii Nefedov
 */
@ControllerAdvice
public final class GlobalController {
    /**
     * create objects TrainDto, PassengerTrainDto to initialize them from UI
     *
     * @param model model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("trainDto", new TrainDto());
        model.addAttribute("passengerTrainDto", new PassengerTrainDto());
    }
}
