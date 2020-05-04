package com.tsystems.project.web;

import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.service.TrainService;
import com.tsystems.project.validator.TrainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import javax.validation.Valid;

@Controller
public class TrainController {
    @Autowired
    TrainService trainService;

    @Autowired
    TrainValidator trainValidator;

    @ResponseBody
    @PostMapping(value = "/admin/train")
    public ModelAndView addTrain(@RequestParam("train_number") int trainNumber, ModelAndView model) {
        TrainDto train = trainService.getTrainByNumber(trainNumber);
        model.addObject("train", trainNumber);
        if (train == null) {
            model.setViewName("train.jsp");
        } else {
            List<TrainStationDto> trains = trainService.getTrainRoutByTrainNumber(trainNumber);
            model.setViewName("trains.jsp");
            model.addObject("trainList", trains);
        }
        return model;
    }

    @ResponseBody
    @PostMapping(value = "/admin/trip")
    public ModelAndView addTrip(@Valid @ModelAttribute("trainDto") TrainDto trainDto,
                                 BindingResult bindingResult, Model model) {
        model.addAttribute("train", trainDto.getNumber());
        trainValidator.validate(trainDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("train.jsp", bindingResult.getModel());
        }
        trainService.addTrain(trainDto);
        List<TrainStationDto> trains = trainService.getTrainRoutByTrainNumber(trainDto.getNumber());
        model.addAttribute("trainList", trains);
        return new ModelAndView("trains.jsp");
    }

    @ResponseBody
    @GetMapping(value = "/admin/trains")
    public ModelAndView getTrainList(ModelAndView model) {
        List<TrainDto> trains = trainService.getTrainList();
        model.setViewName("menu.jsp");
        if (!CollectionUtils.isEmpty(trains)) {
            model.setViewName("trainsList.jsp");
            model.addObject("listOfTrains", trains);
        } else {
            model.addObject("messageTrain", "no trains");
        }
        return model;
    }
}
