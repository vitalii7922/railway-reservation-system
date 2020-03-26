package com.tsystems.project.web;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServlet;
import java.time.LocalDateTime;
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

    @ResponseBody
    @GetMapping(value = "/addTrain")
    public ModelAndView addTrain(@RequestParam("train_number") String trainNumber, ModelAndView model) {
        int number = Integer.parseInt(trainNumber);
        Train train = trainService.getTrainByNumber(number);
        model.addObject("train", number);
        if (train == null) {
            model.setViewName("train.jsp");
        } else {
            List<TrainDto> trains = trainService.getAllTrainsByNumbers(number);
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
                                 @RequestParam("number_of_seats") String numberOfSeats,
                                 @RequestParam("departure_time") String departureTime,
                                 @RequestParam("arrival_time") String arrivalTime, ModelAndView model) {

        model.addObject("train", number);
        Station from = stationService.getStationByName(originStation);
        Station to = stationService.getStationByName(destinationStation);
        LocalDateTime timeDeparture = LocalDateTime.parse(departureTime);
        LocalDateTime timeArrival = LocalDateTime.parse(arrivalTime);

        model.setViewName("train.jsp");

        if (originStation == null || destinationStation == null) {
            return model;
        }

        if (from.getId() == to.getId() || timeDeparture.isAfter(timeArrival)) {
            return model;
        }

        Train train = trainService.addTrain(number, from, to, numberOfSeats);
        TrainDto trainDto;

        if (train != null) {
            scheduleService.addSchedule(train, LocalDateTime.parse(departureTime), LocalDateTime.parse(arrivalTime));
            trainDto = modelMapper.map(train, TrainDto.class);
            model.addObject("listOfStations", trainDto);
        }

        model.setViewName("trains.jsp");
        return model;
    }



    @ResponseBody
    @GetMapping(value = "/getTrains")
    public ModelAndView getTrain(ModelAndView model/*, HttpServletRequest request, HttpServletResponse response*/)  {
        List<TrainDto> trains = trainService.getAllTrains();

        model.setViewName("trainsList.jsp");
        model.addObject("trains", trains);
        return model;
    }
}
