package com.driving.controller;

import com.driving.mapper.AccountMapper;
import com.driving.mapper.TokenMapper;
import com.driving.mapper.UserMapper;
import com.driving.model.Account;
import com.driving.model.Token;
import com.driving.model.User;
import com.driving.status.ListObject;
import com.driving.status.StatusHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * created by wk on 2017-11-23
 * account的Controller
 */
@RestController
public class AccountController {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TokenMapper tokenMapper;

    @Autowired
    private UserMapper userMapper;

    //@Transactional（去掉注释可以实现事务管理）
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ListObject register(String phone,String password,String username){
        ListObject list = new ListObject();
        Account account = new Account();
        User user = new User();
        Token token = new Token();
        account.setPhone(phone);
        account.setLoginPassword(password);
        accountMapper.register(account);
        //同时向user表和token表插入account_id
        /*System.out.println(account);
        user.setAccountId(account.getId());
        user.setName(username);
        token.setAccountId(account.getId());
        userMapper.insertUser(user);
        */
        String uuid = UUID.randomUUID().toString();
        String accessToken = uuid.replace("-", "");
        token.setAccessToken(accessToken);
        tokenMapper.insertToken(token);
        list.setData(account);
        list.setStatusObject(StatusHouse.COMMON_STATUS_OK);
        list.setMessage("提交成功");
        return list;
    }

    /**
     * 登录
     * @param phone
     * @param password
     * @return
     */
    @Transactional
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ListObject login( String phone, String password) {
        ListObject list = new ListObject();
        Account account = accountMapper.login(phone, password);
        Token token = new Token();
        token.setAccountId(account.getId());
        String uuid = UUID.randomUUID().toString();
        String accessToken = uuid.replace("-", "");
        token.setAccessToken(accessToken);
        tokenMapper.insertToken(token);
        list.setData(account);
        if (account != null){
            list.setStatusObject(StatusHouse.COMMON_STATUS_OK);
            list.setMessage("提交成功");
        }else{
            list.setStatusObject(StatusHouse.COMMON_STATUS_ERROR);
            list.setMessage("提交失败");
        }
        return list;
    }

    /**
     *  获取当前用户信息
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public ListObject getUser() {
        ListObject list = new ListObject();
        //获取当前用户的token（待写入）
        User user = userMapper.getUser("57840f8b-d29b-11e7-b576-525400933215");
        if(user != null){
            list.setData(user);
            list.setStatusObject(StatusHouse.COMMON_STATUS_OK);
            list.setMessage("获取成功！");
        }else{
            list.setStatusObject(StatusHouse.COMMON_STATUS_ERROR);
            list.setMessage("获取失败！");
        }
        return list;
    }

    /**
     *  更新当前用户信息
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.PUT)
    public ListObject updateUser(String username,String image,String district) {
        ListObject list = new ListObject();
        //获取当前用户的token（待写入）
        User user = new User();
        userMapper.updateUser("57840f8b-d29b-11e7-b576-525400933215",username,image,district);
        return list;
    }

}
