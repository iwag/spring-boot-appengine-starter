package io.github.iwag.springstarter.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.iwag.springstarter.models.TaskEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TestTaskController {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createAndLoginShouldSuccess() throws Exception {

        {
            TaskEntity ue = new TaskEntity("0", "iw", 0, "2017/08/31");
            String js = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ue);
            this.mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON).content(js))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().string(containsString("2017")));
        }
    }

    @Test
    public void createShouldFail() throws Exception {
        this.mockMvc.perform(put("/task").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andDo(print()).andExpect(status().isBadRequest());
    }

}