package com.tsystems.project.web;
import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.domain.Station;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServlet;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
public class TrainController extends HttpServlet {

    @Autowired
    StationService stationService;

    @Autowired
    TrainService trainService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TimeConverter timeConverter;

    private int trainNumber;

    private static final String MESSAGE = "message";

    private static final Log log = LogFactory.getLog(TrainController.class);

    @ResponseBody
    @GetMapping(value = "/addTrain")
    public ModelAndView addTrain(@RequestParam("train_number") int trainNumber, ModelAndView model) {
        TrainDto train = trainService.getTrainByNumber(trainNumber);
        model.addObject("train", trainNumber);
        if (train == null) {
            model.setViewName("train.jsp");
        } else {
            List<TrainStationDto> trains = trainService.getAllTrainsByNumbers(trainNumber);
            model.setViewName("trains.jsp");
            model.addObject("listOfStations", trains);
        }
        return model;
    }

    @ResponseBody
    @GetMapping(value = "/addTrip")
    public ModelAndView addTrain(@RequestParam("train_number") int number,
                                 @RequestParam("origin_station") String originStation,
                                 @RequestParam("destination_station") String destinationStation,
                                 @RequestParam("number_of_seats") int numberOfSeats,
                                 @RequestParam("departure_time") String departureTime,
                                 @RequestParam("arrival_time") String arrivalTime, ModelAndView modelAndView) {

        modelAndView.addObject("train", number);
        trainNumber = number;
        modelAndView.setViewName("train.jsp");

        LocalDateTime timeDeparture = LocalDateTime.parse(departureTime);
        LocalDateTime timeArrival = LocalDateTime.parse(arrivalTime);

        Station from = stationService.getStationByName(originStation);
        Station to = stationService.getStationByName(destinationStation);

        if (from == null || to == null) {
            modelAndView.addObject(MESSAGE, "you haven't added station or this station doesn't exist in DB");
            return modelAndView;
        }

        if (from.getId() == to.getId()) {
            modelAndView.addObject(MESSAGE, "origin and destination stations are the same");
            return modelAndView;
        }

        if (timeDeparture.isAfter(timeArrival)) {
            modelAndView.addObject(MESSAGE, "time departure is after time arrival");
            return modelAndView;
        }

        TrainDto train = trainService.addTrain(number, from, to, numberOfSeats);

        if (train != null) {
            scheduleService.addSchedule(train, timeDeparture, timeArrival);
            List<TrainStationDto> trains = trainService.getAllTrainsByNumbers(train.getNumber());
            modelAndView.addObject("listOfStations", trains);
            modelAndView.setViewName("trains.jsp");
        }
        return modelAndView;
    }

    @ResponseBody
    @GetMapping(value = "/getTrains")
    public ModelAndView getTrain(ModelAndView model) {
        List<TrainDto> trains = trainService.getAllTrains();
        model.addObject("messageList", "no trains");
        model.setViewName("menu.jsp");
        if (trains != null) {
            model.setViewName("trainsList.jsp");
            model.addObject("listOfTrains", trains);
        }
        return model;
    }


    @ExceptionHandler(DateTimeParseException.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        log.error(e.getCause());
        modelAndView.addObject(MESSAGE, "incorrect date format");
        modelAndView.addObject("train", trainNumber);
        modelAndView.setViewName("train.jsp");
        return modelAndView;
    }
}
