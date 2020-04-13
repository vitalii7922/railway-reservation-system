import com.tsystems.project.config.TestContext;
import com.tsystems.project.config.WebAppContext;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.TrainService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration

public class TrainControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TrainService trainServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addTrain() throws Exception {
        TrainDto train = trainServiceMock.getTrainByNumber(1);
        when(trainServiceMock.getTrainByNumber(1)).thenReturn(train);
        mockMvc.perform(get("/addTrain?train_number=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("train.jsp"))
                .andExpect(forwardedUrl("train.jsp"))
                .andExpect(model().attribute("train", 1));
    }
}
