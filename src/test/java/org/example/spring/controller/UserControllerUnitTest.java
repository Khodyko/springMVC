package org.example.spring.controller;

import junit.framework.TestCase;
import org.example.spring.facade.FacadeImpl;
import org.example.spring.model.Entity.UserEntity;
import org.example.spring.model.User;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
@WebAppConfiguration
public class UserControllerUnitTest extends TestCase {

    private MockMvc mockMvc;
    @Autowired
    private FacadeImpl facade;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getUserById() throws Exception {
        UserEntity userEntity = new UserEntity(0, "sergei", "sergei@mail.ru");
        when(facade.getUserById(0)).thenReturn(userEntity);
        mockMvc.perform(get("/users").param("userId", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(model().attribute("user", Matchers.equalTo(userEntity)));
    }

    @Test
    public void getUserByEmail() throws Exception {
        UserEntity userEntity = new UserEntity(0, "sergei", "sergei@mail.ru");
        when(facade.getUserByEmail("sergei@mail.ru")).thenReturn(userEntity);
        mockMvc.perform(get("/users").param("email", "sergei@mail.ru"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(model().attribute("user", Matchers.equalTo(userEntity)));
    }

    @Test
    public void getUsersByName() throws Exception {
        List<User> userList = new ArrayList<>();
        UserEntity userEntity = new UserEntity(0, "sergei", "sergei@mail.ru");
        userList.add(userEntity);
        when(facade.getUsersByName("sergei", 100, 0)).thenReturn(userList);
        mockMvc.perform(get("/users").param("name", "sergei")
                .param("page-size", "100")
                .param("page-num", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(model().attribute("users", Matchers.equalTo(userList)));
    }

    @Test
    public void createUser() throws Exception {
        UserEntity userEntity = new UserEntity( "sergei", "sergei@mail.ru");
        UserEntity userEntityFromMock = new UserEntity(0, "sergei", "sergei@mail.ru");
        when(facade.createUser(userEntity)).thenReturn(userEntityFromMock);
        mockMvc.perform(post("/users").param("name", "sergei")
                .param("email", "sergei@mail.ru"))
                .andExpect(status().isCreated())
                .andExpect(view().name("user"))
                .andExpect(model().attribute("user", Matchers.equalTo(userEntityFromMock)));
    }

    @Test
    public void updateUser() throws Exception {
        UserEntity userEntity = new UserEntity(0, "sergei", "sergei@mail.ru");
        UserEntity userEntityFromMock = new UserEntity(0, "sergei", "sergei@mail.ru");
        when(facade.updateUser(userEntity)).thenReturn(userEntityFromMock);
        mockMvc.perform(put("/users").param("eventId", "0")
                .param("name", "sergei")
                .param("email", "sergei@mail.ru"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(model().attribute("user", Matchers.equalTo(userEntityFromMock)));
    }

    @Test
    public void deleteUser() throws Exception {
        when(facade.deleteUser(0)).thenReturn(true);
        mockMvc.perform(delete("/users").param("userId", "0"))
                .andExpect(status().isNoContent())
                .andExpect(view().name("user"));
    }
}