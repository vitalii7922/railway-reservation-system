import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.TrainService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class TrainServiceTest {

    @Autowired
    TrainService trainService;

    @Test
    public void testGetTrain() {
        Assert.assertEquals(trainService.getTrainByNumber(1).getNumber(), 1);
    }

    @Test
    public void testGetAllTrains() {
        Assert.assertEquals(trainService.getAllTrains().size(), 2);
    }
}
