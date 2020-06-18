package com.lambdaschool.usermodel.controllers;

import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * The entry point for clients to access user data
 */
@RestController
@RequestMapping("/users")
public class UserController
{
    /**
     * Using the User service to process user data
     */
    @Autowired
    private UserService userService;

    /**
     * Returns a list of all users
     * <br>Example: <a href="http://localhost:2019/users/users">http://localhost:2019/users/users</a>
     *
     * @return JSON list of all users with a status of OK
     * @see UserService#findAll() UserService.findAll()
     */
    @GetMapping(value = "/users",
        produces = {"application/json"})
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers,
            HttpStatus.OK);
    }

    /**
     * Returns a single user based off a user id number
     * <br>Example: <a href="http://localhost:2019/users/user/7">http://localhost:2019/users/user/7</a>
     *
     * @param userId The primary key of the user you seek
     * @return JSON object of the user you seek
     * @see UserService#findUserById(long) UserService.findUserById(long)
     */
    @GetMapping(value = "/user/{userId}",
        produces = {"application/json"})
    public ResponseEntity<?> getUserById(
        @PathVariable
            Long userId)
    {
        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u,
            HttpStatus.OK);
    }

    /**
     * Return a user object based on a given username
     * <br>Example: <a href="http://localhost:2019/users/user/name/cinnamon">http://localhost:2019/users/user/name/cinnamon</a>
     *
     * @param userName the name of user (String) you seek
     * @return JSON object of the user you seek
     * @see UserService#findByName(String) UserService.findByName(String)
     */
    @GetMapping(value = "/user/name/{userName}",
        produces = {"application/json"})
    public ResponseEntity<?> getUserByName(
        @PathVariable
            String userName)
    {
        User u = userService.findByName(userName);
        return new ResponseEntity<>(u,
            HttpStatus.OK);
    }

    /**
     * Returns a list of users whose username contains the given substring
     * <br>Example: <a href="http://localhost:2019/users/user/name/like/da">http://localhost:2019/users/user/name/like/da</a>
     *
     * @param userName Substring of the username for which you seek
     * @return A JSON list of users you seek
     * @see UserService#findByNameContaining(String, Pageable) UserService.findByNameContaining(String)
     */
    @GetMapping(value = "/user/name/like/{userName}",
        produces = {"application/json"})
    public ResponseEntity<?> getUserLikeName(
        @PathVariable
            String userName, Pageable pageable)
    {
        List<User> u = userService.findByNameContaining(userName, pageable);
        return new ResponseEntity<>(u,
            HttpStatus.OK);
    }

    /**
     * Given a complete User Object, create a new User record and accompanying useremail records
     * and user role records.
     * <br> Example: <a href="http://localhost:2019/users/user">http://localhost:2019/users/user</a>
     *
     * @param newuser A complete new user to add including emails and roles.
     *                roles must already exist.
     * @return A location header with the URI to the newly created user and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the location header
     * @see UserService#save(User) UserService.save(User)
     */
    @PostMapping(value = "/user",
        consumes = {"application/json"})
    public ResponseEntity<?> addNewUser(
        @Valid
        @RequestBody
            User newuser) throws URISyntaxException
    {
        newuser.setUserid(0);
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{userid}")
            .buildAndExpand(newuser.getUserid())
            .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null,
            responseHeaders,
            HttpStatus.CREATED);
    }

    /**
     * Given a complete User Object
     * Given the user id, primary key, is in the User table,
     * replace the User record , user role combinations and Useremail records.
     * <br> Example: <a href="http://localhost:2019/users/user/15">http://localhost:2019/users/user/15</a>
     *
     * @param updateUser A complete User including all emails and roles to be used to
     *                   replace the User. Roles must already exist.
     * @param userid     The primary key of the user you wish to replace.
     * @return status of OK
     * @see UserService#save(User) UserService.save(User)
     */
    @PutMapping(value = "/user/{userid}",
        consumes = {"application/json"})
    public ResponseEntity<?> updateFullUser(
        @Valid
        @RequestBody
            User updateUser,
        @PathVariable
            long userid)
    {
        updateUser.setUserid(userid);
        userService.save(updateUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Updates the user record associated with the given id with the provided data. Only the provided fields are affected.
     * If an email list or user role combination list is given, it replaces the list.
     * <br> Example: <a href="http://localhost:2019/users/user/7">http://localhost:2019/users/user/7</a>
     *
     * @param updateUser An object containing values for just the fields that are being updated. All other fields are left NULL.
     * @param id         The primary key of the user you wish to update.
     * @return A status of OK
     * @see UserService#update(User, long) UserService.update(User, long)
     */
    @PatchMapping(value = "/user/{id}",
        consumes = {"application/json"})
    public ResponseEntity<?> updateUser(
        @RequestBody
            User updateUser,
        @PathVariable
            long id)
    {
        userService.update(updateUser,
            id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a given user along with associated emails and roles
     * <br>Example: <a href="http://localhost:2019/users/user/14">http://localhost:2019/users/user/14</a>
     *
     * @param id the primary key of the user you wish to delete
     * @return Status of OK
     */
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUserById(
        @PathVariable
            long id)
    {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     *
     * The following routes are new from initial
     *
     */

    /**
     * Find all users and the number of emails associated with them. Does not include primary email in the report
     * <br>Example: <a href="http://localhost:2019/users/user/email/count">http://localhost:2019/users/user/email/count</a>
     *
     * @return JSON list of all users with the number of emails associated with them.
     */
    @GetMapping(value = "/user/email/count",
        produces = {"application/json"})
    public ResponseEntity<?> getNumUserEmails()
    {
        return new ResponseEntity<>(userService.getCountUserEmails(),
            HttpStatus.OK);
    }

    /**
     * Deletes the given user, user role combination
     * <br>Example: <a href="http://localhost:2019/users/user/7/role/2">http://localhost:2019/users/user/7/role/2</a>
     *
     * @param userid the user id of the user of the user role combination
     * @param roleid the role id of the user of the user user role combination
     * @return Status OK
     */
    @DeleteMapping(value = "/user/{userid}/role/{roleid}")
    public ResponseEntity<?> deleteUserRoleByIds(
        @PathVariable
            long userid,
        @PathVariable
            long roleid)
    {
        userService.deleteUserRole(userid,
            roleid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Adds the given user, user role combination
     * <br>Example: <a href="http://localhost:2019/users/user/7/role/2">http://localhost:2019/users/user/7/role/2</a>
     *
     * @param userid the user id of the user user role combination
     * @param roleid the role id of the user user role combination
     * @return Status OK
     */
    @PostMapping(value = "/user/{userid}/role/{roleid}")
    public ResponseEntity<?> postUserRoleByIds(
        @PathVariable
            long userid,
        @PathVariable
            long roleid)
    {
        userService.addUserRole(userid,
            roleid);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}