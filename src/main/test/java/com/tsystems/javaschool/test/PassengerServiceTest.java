package com.tsystems.javaschool.test;

import com.tsystems.javaschool.test.config.TestConfig;
import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.service.PassengerService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.LocalDate;
import java.util.Arrays;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class PassengerServiceTest {
    @Autowired
    private PassengerDao passengerDao;

    @Autowired
    private PassengerService passengerService;

    private static Passenger passengerAddedToDb;

    private static void setUpPassenger() {
        passengerAddedToDb = Passenger.builder()
                .id(1)
                .firstName("Ivan")
                .secondName("Ivanov")
                .birthDate(LocalDate.parse("2000-01-01"))
                .build();
    }

    @BeforeClass
    public static void setUp() {
        setUpPassenger();
    }

    @Test
    public void testAddPassenger() {
        PassengerDto passengerDto = PassengerDto.builder()
                .firstName("Ivan")
                .secondName("Ivanov")
                .birthDate(LocalDate.parse("2000-01-01"))
                .build();

        PassengerTrainDto passengerTrainDto = PassengerTrainDto.builder()
                .firstName("Ivan")
                .secondName("Ivanov")
                .birthDate("2000-01-01")
                .build();

        Passenger passenger = Passenger.builder()
                .firstName("Ivan")
                .secondName("Ivanov")
                .birthDate(LocalDate.parse("2000-01-01"))
                .build();

        Mockito.when(passengerDao.create(passenger)).thenReturn(passengerAddedToDb);
        Assert.assertNotNull(passengerService.addPassenger(passengerTrainDto));
    }

    @Test
    public void testGetPassenger() {
        PassengerTrainDto passengerTrainDto = PassengerTrainDto.builder()
                .firstName("Ivan")
                .secondName("Ivanov")
                .birthDate("2000-01-01")
                .build();

        Mockito.when(passengerDao.findByPersonalData(passengerTrainDto.getFirstName(), passengerTrainDto.getSecondName(),
                passengerAddedToDb.getBirthDate())).thenReturn(passengerAddedToDb);
        Assert.assertNotNull(passengerService.getPassenger(passengerTrainDto));
    }

    @Test
    public void testGetPassengerById() {
        Mockito.when(passengerDao.findOne(1)).thenReturn(passengerAddedToDb);
        Assert.assertNotNull(passengerService.getPassengerById(1));
    }

    @Test
    public void testGetPassengerListByTrainNumber() {
        Passenger passengerPetrov = Passenger.builder()
                .id(2)
                .firstName("Dmitrii")
                .secondName("Petrov")
                .birthDate(LocalDate.parse("1995-01-01"))
                .build();

        Passenger passengerIvanov = Passenger.builder()
                .id(1)
                .firstName("Ivan")
                .secondName("Ivanov")
                .birthDate(LocalDate.parse("2000-01-01"))
                .build();

        Mockito.when(passengerDao.findAllPassengersByTrainNumber(1)).thenReturn(Arrays.asList(passengerAddedToDb,
                passengerPetrov, passengerIvanov));
        Assert.assertThat(passengerService.getPassengerListByTrainNumber(1).size(), is(2));
    }
}
