package com.web.ecomm.app.service;

import com.web.ecomm.app.dto.AuthenticationResponse;
import com.web.ecomm.app.dto.SignInRequest;
import com.web.ecomm.app.pojo.Credentials;
import com.web.ecomm.app.pojo.User;

import java.util.List;

public interface IUserService {

    User userSignup(User u);

    AuthenticationResponse userSignIn(SignInRequest user);

    User getProfile(int id);

    User userUpdate(int id, User u);

    List<User> getUsersListAll();

    Credentials addNewAuth(Credentials credentials);

    String changeUserActiveStatus(int user_id, int status);

    Integer getAllUserCount();
}
