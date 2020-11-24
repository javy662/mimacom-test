package com.mimacom.testproject.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimacom.testproject.domain.entity.Task;
import com.mimacom.testproject.exception.TaskNotFoundException;
import com.mimacom.testproject.service.TaskService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerMockMvcTest {
	
	private static final ObjectMapper om = new ObjectMapper();
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;
	
    
    @Test
    public void getAllTaskMvc() throws Exception {
    	when(taskService.getAllTask()).thenReturn(generateListTask());
    	 mockMvc.perform(get("/api/mimacom/task"))
         /*.andDo(print())*/
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(1)));

    	 verify(taskService, times(1)).getAllTask();
    }
    
    @Test
    public void getTaskMvc() throws Exception {
    	when(taskService.getTaskById(1l)).thenReturn(generateTask());
    	 mockMvc.perform(get("/api/mimacom/task/1"))
         /*.andDo(print())*/
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk());

    	 verify(taskService, times(1)).getTaskById(1l);
    }
    
    @Test
    public void getTaskNotFoundMvc() throws Exception {
    	when(taskService.getTaskById(1l)).thenThrow(new TaskNotFoundException(1l));
    	 mockMvc.perform(get("/api/mimacom/task/1"))
         /*.andDo(print())*/
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isNotFound());
    }
    
    @Test
    public void delete_task_OK() throws Exception {

        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/mimacom/task/1"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    public void deleteTaskNotFound() throws Exception {

        doThrow(new TaskNotFoundException(1l)).when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/mimacom/task/1"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    public void saveTaskOK() throws Exception {

        
        when(taskService.createTask(any(Task.class))).thenReturn(generateTask());

        mockMvc.perform(post("/api/mimacom/task/")
                .content(om.writeValueAsString(generateTask()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isCreated());

        verify(taskService, times(1)).createTask(any(Task.class));

    }

    @Test
    public void updateTaskOK() throws Exception {

        
        when(taskService.updateTask(any(Task.class))).thenReturn(generateTask());

        mockMvc.perform(put("/api/mimacom/task")
                .content(om.writeValueAsString(generateTask()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isOk());

        verify(taskService, times(1)).updateTask(any(Task.class));

    }
    
    @Test
    public void updateFinishTaskOK() throws Exception {

        Task task = generateTask();
        task.setFinish(Boolean.TRUE);
        when(taskService.updateState(anyLong())).thenReturn(task);

        mockMvc.perform(put("/api/mimacom/task/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isOk());

        verify(taskService, times(1)).updateState(anyLong());

    }
    
	private List<Task> generateListTask() {
		ArrayList<Task> allTask = new ArrayList<Task>();
		allTask.add(generateTask());
		return allTask;
	}

	private Task generateTask() {
		return new Task(1L, "title", "description", Boolean.FALSE);
	}

}
