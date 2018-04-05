package com.almundo.test.callcenter;

import com.almundo.test.callcenter.entities.Call;
import com.almundo.test.callcenter.services.DispatcherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EndPointTests {
    @MockBean
    private DispatcherService dispatcherService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_show_new_call_when_invoke_dispatch_end_point() throws Exception {
        Call call = new Call();
        call.setId(1L);

        when(dispatcherService.dispatchNewCall()).thenReturn(call);
        this.mockMvc.perform(get("/dispatch")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":" + call.getId())));
    }
}
