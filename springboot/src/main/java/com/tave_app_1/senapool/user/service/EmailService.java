package com.tave_app_1.senapool.user.service;

public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;
    String sendTempPwMessage(String to) throws Exception;
    String sendFindUserMessage(String userId,String to) throws Exception;
}
