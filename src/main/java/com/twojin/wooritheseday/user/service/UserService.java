package com.twojin.wooritheseday.user.service;


import com.twojin.wooritheseday.user.entity.UserDTO;

public interface UserService  {


    UserDTO login(UserDTO user);

    UserDTO saveUser(UserDTO user);

    UserDTO findMyAccount(UserDTO user);

    UserDTO loadMyAccountByUserId(String userId);

    UserDTO selectUserByUserId(String userId);

    boolean agreeUserInfo(UserDTO userDTO);

    UserDTO updateUserImgUrl(String imgUrl, String userId);
}
