package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void afindUserById()
    {
        assertEquals("test admin", userService.findUserById(4).getUsername());
    }

    @Test(expected = EntityNotFoundException.class)
    public void bfindUserByIdFail()
    {
        assertEquals("test eagle cafe", userService.findUserById(99999).getUsername());
    }

    @Test
    public void cfindByNameContaining()
    {
    }

    @Test
    public void dfindAll()
    {
        assertEquals(30, userService.findAll().size());
    }

    @Test
    public void edelete()
    {
        userService.delete(7);
        assertEquals(29, userService.findAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void eadeletenotfound()
    {
        userService.delete(99999);
        assertEquals(29, userService.findAll().size());
    }

    @Test
    public void ffindByName()
    {
    }

    @Test
    public void gsave()
    {
        List<UserRoles> thisRole = new ArrayList<>();
        User newUser = new User("Number 1 Test User", "pass", "test@lambdaschool.local", thisRole);
        newUser.getUseremails().add(new Useremail(newUser, "newTest@lambdaschool.local"));
        User addUser = userService.save(newUser);

        assertNotNull(addUser);
        User foundUser = userService.findUserById(addUser.getUserid());
        assertEquals(addUser.getUsername(), foundUser.getUsername());
    }

    @Test
    public void hupdate()
    {
        List<UserRoles> thisRole = new ArrayList<>();
        User newUser = new User("Number 9999 Test User", "pass", "test@lambdaschool.local", thisRole);
        newUser.getUseremails().add(new Useremail(newUser, "newTest99@lambdaschool.local"));

        User updateUser = userService.update(newUser, 11);
        assertEquals("number 9999 test user", updateUser.getUsername());
    }

    @Test
    public void igetCountUserEmails()
    {
    }

    @Test
    public void jdeleteUserRole()
    {
    }

    @Test
    public void kaddUserRole()
    {
    }
}