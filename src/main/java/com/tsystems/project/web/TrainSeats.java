package com.tsystems.project.web;

import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Map;

@Controller
public class TrainSeats {

    @Autowired
    SeatsService seatsService;

    @ResponseBody
    @GetMapping(value = "/getSeats")
    public ModelAndView getSeats(@RequestParam("train_number") int trainNumber, ModelAndView model) {
            List<TrainDto> trains = seatsService.getTrainByNumber(trainNumber);
            model.setViewName("seats.jsp");
            model.addAllObjects(Map.of("trains", trains, "train", trainNumber));
            return model;
    }
}
