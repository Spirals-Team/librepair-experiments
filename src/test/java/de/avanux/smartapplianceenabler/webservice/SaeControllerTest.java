package de.avanux.smartapplianceenabler.webservice;

import de.avanux.smartapplianceenabler.Application;
import de.avanux.smartapplianceenabler.appliance.*;
import de.avanux.smartapplianceenabler.schedule.*;
import de.avanux.smartapplianceenabler.util.FileHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SaeControllerTest {

    private final static String SCHEDULE_RUNTIME_REQUEST_DAY_TIMEFRAME =
            "<Schedules>\n" +
            "  <Schedule>\n" +
            "    <RuntimeRequest min=\"7200\" max=\"10800\" />\n" +
            "    <DayTimeframe>\n" +
            "      <Start hour=\"10\" minute=\"0\" second=\"0\"/>\n" +
            "      <End hour=\"14\" minute=\"0\" second=\"0\"/>\n" +
            "      <DayOfWeek>6</DayOfWeek>\n" +
            "      <DayOfWeek>7</DayOfWeek>\n" +
            "    </DayTimeframe>\n" +
            "  </Schedule>\n" +
            "</Schedules>\n"
            ;

    private  final static String SCHEDULE_CONSECUTIVE_DAYS_TIMEFRAME =
            "<Schedules>\n" +
            "  <Schedule>\n" +
            "    <RuntimeRequest min=\"36000\" max=\"43200\" />\n" +
            "    <ConsecutiveDaysTimeframe>\n" +
            "      <Start dayOfWeek=\"5\" hour=\"16\" minute=\"0\" second=\"0\" />\n" +
            "      <End dayOfWeek=\"7\" hour=\"20\" minute=\"0\" second=\"0\" />\n" +
            "    </ConsecutiveDaysTimeframe>\n" +
            "  </Schedule>\n" +
            "</Schedules>\n"
            ;

    private final static String SCHEDULE_ENERGY_REQUEST_DAY_TIMEFRAME =
            "<Schedules>\n" +
                    "  <Schedule>\n" +
                    "    <EnergyRequest min=\"1380\" max=\"7360\" />\n" +
                    "    <DayTimeframe>\n" +
                    "      <Start hour=\"10\" minute=\"0\" second=\"0\"/>\n" +
                    "      <End hour=\"14\" minute=\"0\" second=\"0\"/>\n" +
                    "      <DayOfWeek>6</DayOfWeek>\n" +
                    "      <DayOfWeek>7</DayOfWeek>\n" +
                    "    </DayTimeframe>\n" +
                    "  </Schedule>\n" +
                    "</Schedules>\n"
            ;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_XML.getType(),
            MediaType.APPLICATION_XML.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        System.setProperty(FileHandler.SAE_HOME, System.getProperty("java.io.tmpdir"));
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void setSchedules_RuntimeRequest_DayTimeframe() throws Exception {
        RunningTimeMonitor runningTimeMonitor = runTest(SaeController.SCHEDULES_URL, SCHEDULE_RUNTIME_REQUEST_DAY_TIMEFRAME);

        List<Schedule> schedules = runningTimeMonitor.getSchedules();
        Assert.assertEquals(1, schedules.size());
        Schedule schedule = schedules.get(0);

        Assert.assertEquals(7200, schedule.getRequest().getMin().intValue());
        Assert.assertEquals(10800, schedule.getRequest().getMax().intValue());

        Timeframe timeframe = schedule.getTimeframe();
        Assert.assertTrue(timeframe instanceof DayTimeframe);
        DayTimeframe dayTimeframe = (DayTimeframe) timeframe;

        Assert.assertTrue(schedule.getRequest() instanceof RuntimeRequest);
        Assert.assertEquals(new TimeOfDay(10, 0 ,0), dayTimeframe.getStart());
        Assert.assertEquals(new TimeOfDay(14, 0 ,0), dayTimeframe.getEnd());

        List<Integer> daysOfWeekValues = dayTimeframe.getDaysOfWeekValues();
        Assert.assertEquals(2, daysOfWeekValues.size());
        Assert.assertEquals(6, daysOfWeekValues.get(0).intValue());
        Assert.assertEquals(7, daysOfWeekValues.get(1).intValue());
    }

    @Test
    public void setSchedules_RuntimeRequest_ConsecutiveDaysTimeframe() throws Exception {
        RunningTimeMonitor runningTimeMonitor = runTest(SaeController.SCHEDULES_URL, SCHEDULE_CONSECUTIVE_DAYS_TIMEFRAME);

        List<Schedule> schedules = runningTimeMonitor.getSchedules();
        Assert.assertEquals(1, schedules.size());
        Schedule schedule = schedules.get(0);

        Assert.assertTrue(schedule.getRequest() instanceof RuntimeRequest);
        Assert.assertEquals(36000, schedule.getRequest().getMin().intValue());
        Assert.assertEquals(43200, schedule.getRequest().getMax().intValue());

        Timeframe timeframe = schedule.getTimeframe();
        Assert.assertTrue(timeframe instanceof ConsecutiveDaysTimeframe);

        ConsecutiveDaysTimeframe consecutiveDaysTimeframe = (ConsecutiveDaysTimeframe) timeframe;
        Assert.assertEquals(new TimeOfDayOfWeek(5, 16, 0, 0), consecutiveDaysTimeframe.getStart());
        Assert.assertEquals(new TimeOfDayOfWeek(7, 20, 0, 0), consecutiveDaysTimeframe.getEnd());
    }

    @Test
    public void setSchedules_EnergyRequest_DayTimeframe() throws Exception {
        RunningTimeMonitor runningTimeMonitor = runTest(SaeController.SCHEDULES_URL, SCHEDULE_ENERGY_REQUEST_DAY_TIMEFRAME);

        List<Schedule> schedules = runningTimeMonitor.getSchedules();
        Assert.assertEquals(1, schedules.size());
        Schedule schedule = schedules.get(0);

        Assert.assertTrue(schedule.getRequest() instanceof EnergyRequest);
        Assert.assertEquals(1380, schedule.getRequest().getMin().intValue());
        Assert.assertEquals(7360, schedule.getRequest().getMax().intValue());

        Timeframe timeframe = schedule.getTimeframe();
        Assert.assertTrue(timeframe instanceof DayTimeframe);
        DayTimeframe dayTimeframe = (DayTimeframe) timeframe;

        Assert.assertEquals(new TimeOfDay(10, 0 ,0), dayTimeframe.getStart());
        Assert.assertEquals(new TimeOfDay(14, 0 ,0), dayTimeframe.getEnd());

        List<Integer> daysOfWeekValues = dayTimeframe.getDaysOfWeekValues();
        Assert.assertEquals(2, daysOfWeekValues.size());
        Assert.assertEquals(6, daysOfWeekValues.get(0).intValue());
        Assert.assertEquals(7, daysOfWeekValues.get(1).intValue());
    }

    private RunningTimeMonitor runTest(String url, String content) throws Exception {
        RunningTimeMonitor runningTimeMonitor = new RunningTimeMonitor();

        String applianceId = "F-00000001-000000000001-00";
        Appliance appliance = new Appliance();
        appliance.setId(applianceId);
        appliance.setRunningTimeMonitor(runningTimeMonitor);

        Appliances appliances = new Appliances();
        appliances.setAppliances(Collections.singletonList(appliance));

        ApplianceManager.getInstanceWithoutTimer().setAppliances(appliances);

        this.mockMvc.perform(post(url)
                .param("id", applianceId)
                .contentType(contentType)
                .content(content))
                .andExpect(status().isOk());

        return runningTimeMonitor;
    }
}
