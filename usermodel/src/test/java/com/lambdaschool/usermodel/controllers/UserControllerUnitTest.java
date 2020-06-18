package com.lambdaschool.usermodel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerUnitTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @Before
    public void setUp() throws Exception
    {
        userList = new ArrayList<>();

        Role r1 = new Role("admin");
        r1.setRoleid(1);
        Role r2 = new Role("user");
        r2.setRoleid(2);
        Role r3 = new Role("data");
        r3.setRoleid(3);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(),
            r1));
        admins.add(new UserRoles(new User(),
            r2));
        admins.add(new UserRoles(new User(),
            r3));

        User u1 = new User("test admin",
            "password",
            "admin@lambdaschool.local",
            admins);
        u1.setUserid(11);
        u1.getUseremails()
            .add(new Useremail(u1,
                "admin@email.local"));
        u1.getUseremails().get(0).setUseremailid(20);
        u1.getUseremails()
            .add(new Useremail(u1,
                "admin@mymail.local"));
        u1.getUseremails().get(1).setUseremailid(21);

        userList.add(u1);

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(),
            r3));
        datas.add(new UserRoles(new User(),
            r2));
        User u2 = new User("test cinnamon",
            "1234567",
            "cinnamon@lambdaschool.local",
            datas);
        u2.setUserid(12);
        u2.getUseremails()
            .add(new Useremail(u2,
                "cinnamon@mymail.local"));
        u2.getUseremails().get(0).setUseremailid(30);
        u2.getUseremails()
            .add(new Useremail(u2,
                "hops@mymail.local"));
        u2.getUseremails().get(1).setUseremailid(31);
        u2.getUseremails()
            .add(new Useremail(u2,
                "bunny@email.local"));
        u2.getUseremails().get(2).setUseremailid(32);

        userList.add(u2);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(),
            r2));
        User u3 = new User("test barnbarn",
            "ILuvM4th!",
            "barnbarn@lambdaschool.local",
            users);
        u3.setUserid(13);
        u3.getUseremails()
            .add(new Useremail(u3,
                "barnbarn@email.local"));
        u3.getUseremails().get(0).setUseremailid(40);

        userList.add(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
            r2));
        User u4 = new User("test puttat",
            "password",
            "puttat@school.lambda",
            users);
        u4.setUserid(14);

        userList.add(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
            r2));
        User u5 = new User("test misskitty",
            "password",
            "misskitty@school.lambda",
            users);
        u5.setUserid(15);

        userList.add(u5);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllUsers() throws Exception
    {
        String apiUrl = "/users/users";
        Mockito.when(userService.findAll()).thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn(); // this can throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);
        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getUserById()
    {
    }

    @Test
    public void getUserByName()
    {
    }

    @Test
    public void getUserLikeName()
    {
    }

    @Test
    public void addNewUser()
    {
    }

    @Test
    public void updateFullUser()
    {
    }

    @Test
    public void updateUser()
    {
    }

    @Test
    public void deleteUserById() throws Exception
    {
        String apiUrl = "/users/user/{userid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "12")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}