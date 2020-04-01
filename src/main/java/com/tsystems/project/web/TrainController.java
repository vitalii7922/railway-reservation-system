package com.tsystems.project.web;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.PassengerService;
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
import javax.servlet.http.HttpServletRequest;
import java.time.DateTimeException;
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



    private static final Log log = LogFactory.getLog(PassengerService.class);

    @ResponseBody
    @GetMapping(value = "/addTrain")
    public ModelAndView addTrain(@RequestParam("train_number") int trainNumber, ModelAndView model) {
//        int number = Integer.parseInt(trainNumber);
        TrainDto train = trainService.getTrainByNumber(trainNumber);
        model.addObject("train", trainNumber);
        if (train == null) {
//            model.addObject("train", trainNumber);
            model.setViewName("train.jsp");
        } else {
            List<TrainDto> trains = trainService.getAllTrainsByNumbers(trainNumber);
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
                                 @RequestParam("arrival_time") String arrivalTime, ModelAndView model) {

        model.addObject("train", number);
        LocalDateTime timeDeparture = LocalDateTime.parse(departureTime);
        LocalDateTime timeArrival = LocalDateTime.parse(arrivalTime);

        Station from = stationService.getStationByName(originStation);
        Station to = stationService.getStationByName(destinationStation);


        model.setViewName("incorrect_date_format.jsp");

        if (from == null || to == null) {
            model.addObject("message", "you haven't added station or this station doesn't exist in DB");
            return model;
        }

        if (from.getId() == to.getId()) {
            model.addObject("message", "origin and destination stations are the same");
            return model;
        }

        if (timeDeparture.isAfter(timeArrival)) {
            model.addObject("message", "time departure is after time arrival");
            return model;
        }

        TrainDto train = trainService.addTrain(number, from, to, numberOfSeats);

        if (train != null) {
            scheduleService.addSchedule(train, timeDeparture, timeArrival);
            List<TrainDto> trains = trainService.getAllTrainsByNumbers(train.getNumber());
            model.addObject("listOfStations", trains);
        }
        model.setViewName("trains.jsp");
        return model;
    }

    @ResponseBody
    @GetMapping(value = "/getTrains")
    public ModelAndView getTrain(ModelAndView model) {
        List<TrainDto> trains = trainService.getAllTrains();
        model.addObject("messageList", "list is empty");
        model.setViewName("menu.jsp");
        if (trains != null) {
            model.setViewName("trainsList.jsp");
            model.addObject("listOfTrains", trains);
        }
        return model;
    }


    @ExceptionHandler(DateTimeParseException.class)
    public ModelAndView handleException(Exception e) {
        log.error(e.getCause());
        return new ModelAndView("incorrect_date_format.jsp").addObject("message", "incorrect date");
    }
}
