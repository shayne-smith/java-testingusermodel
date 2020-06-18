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
import org.springframework.data.domain.Pageable;
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

    private Pageable pageable;

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
        assertEquals(1, userService.findByNameContaining("cinn", pageable).size());
    }

    @Test
    public void dfindAll()
    {
        assertEquals(30, userService.findAll().size());
    }

    @Test
    public void edelete()
    {
        userService.delete(11);
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
        assertEquals("test cinnamon", userService.findByName("test cinnamon").getUsername());
    }

    @Test(expected = EntityNotFoundException.class)
    public void fafindByNameNotFound()
    {
        assertEquals("cinnamon", userService.findByName("cinnamon").getUsername());
    }

    @Test
    public void gasave()
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
    public void gsaveasput()
    {
        List<UserRoles> thisRole = new ArrayList<>();
        thisRole.add(new UserRoles());
        thisRole.get(0).setRole(new Role());
        thisRole.get(0).getRole().setRoleid(2);
        thisRole.get(0).setUser(new User());
        User newUser = new User("Number 12 Test User", "pass", "test2@lambdaschool.local", thisRole);

        newUser.setUserid(4);
        newUser.getUseremails()
            .add(new Useremail(newUser, "newTest@lambdaschool.local"));
        User addUser = userService.save(newUser);

        assertNotNull(addUser);
        User foundUser = userService.findUserById(addUser.getUserid());
        assertEquals(addUser.getUsername(), foundUser.getUsername());
    }

    @Test
    public void hupdate()
    {

//        newUser.getUseremails().add(new Useremail(newUser, "newTest99@lambdaschool.local"));
//
//        User updateUser = userService.update(newUser, 11);
//        assertEquals("number 9999 test user", updateUser.getUsername());

        List<UserRoles> thisRole = new ArrayList<>();
        thisRole.add(new UserRoles());
        thisRole.get(0).setRole(new Role());
        thisRole.get(0).getRole().setRoleid(2);
        thisRole.get(0).setUser(new User());
        User newUser = new User("Number 9999 Test User", "pass", "test3@lambdaschool.local", thisRole);

        newUser.getUseremails().add(new Useremail(newUser, "newTest99@lambdaschool.local"));

        User updateUser = userService.update(newUser, 13);
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