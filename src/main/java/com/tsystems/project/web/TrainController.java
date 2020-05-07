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

/**
 * author Vitalii Nefedov
 */

@Controller
public class TrainController {
    private final TrainService trainService;

    private final TrainValidator trainValidator;

    @Autowired
    public TrainController(TrainService trainService, TrainValidator trainValidator) {
        this.trainService = trainService;
        this.trainValidator = trainValidator;
    }

    /**
     * @param trainNumber  train number
     * @param modelAndView model and view
     * @return model(train rout) and view(trains.jsp)
     */
    @ResponseBody
    @PostMapping(value = "/employee/train")
    public ModelAndView addTrain(@RequestParam("train_number") int trainNumber, ModelAndView modelAndView) {
        TrainDto train = trainService.getTrainByNumber(trainNumber);
        modelAndView.addObject("train", trainNumber);
        if (train == null) {
            modelAndView.setViewName("train.jsp");
        } else {
            List<TrainStationDto> trains = trainService.getTrainRoutByTrainNumber(trainNumber);
            modelAndView.setViewName("trains.jsp");
            modelAndView.addObject("trainList", trains);
        }
        return modelAndView;
    }

    /**
     * @param trainDto      train data
     * @param bindingResult result of validation
     * @param model         model
     * @return model and view
     */
    @ResponseBody
    @PostMapping(value = "/employee/trip")
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

    /**
     * @param modelAndView model and view
     * @return model and view
     */
    @ResponseBody
    @GetMapping(value = "/employee/trains")
    public ModelAndView getTrainList(ModelAndView modelAndView) {
        List<TrainDto> trains = trainService.getTrainList();
        modelAndView.setViewName("menu.jsp");
        if (!CollectionUtils.isEmpty(trains)) {
            modelAndView.setViewName("trainsList.jsp");
            modelAndView.addObject("listOfTrains", trains);
        } else {
            modelAndView.addObject("messageTrain", "no trains");
        }
        return modelAndView;
    }
}
