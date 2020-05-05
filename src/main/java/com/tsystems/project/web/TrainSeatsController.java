package com.tsystems.project.web;

import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.SeatsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Map;

/**
 * author Vitalii Nefedov
 */

@Controller
public class TrainSeatsController {

    private final SeatsService seatsService;

    public TrainSeatsController(SeatsService seatsService) {
        this.seatsService = seatsService;
    }

    /**
     * @param trainNumber  train number
     * @param modelAndView model and view
     * @return model(number of seats from point A to point B) and view
     */
    @ResponseBody
    @GetMapping(value = "/admin/seats")
    public ModelAndView getSeats(@RequestParam("train_number") int trainNumber, ModelAndView modelAndView) {
        List<TrainDto> trains = seatsService.getTrainByNumber(trainNumber);
        modelAndView.setViewName("seats.jsp");
        modelAndView.addAllObjects(Map.of("trains", trains, "train", trainNumber));
        return modelAndView;
    }
}
