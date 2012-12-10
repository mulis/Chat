package org.mulis.chat.controller;

import org.apache.log4j.Logger;
import org.mulis.chat.controller.dto.GetMessagesRequest;
import org.mulis.chat.controller.dto.LoginRequest;
import org.mulis.chat.controller.dto.LogoutRequest;
import org.mulis.chat.controller.dto.PostMessageRequest;
import org.mulis.chat.model.ChatMessageEnvelope;
import org.mulis.chat.model.ChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.LinkedList;

@Controller
//@RequestMapping("/")
public class ChatController {

    static Logger logger = Logger.getLogger(ChatController.class.getName());

    @Inject
    private ChatModel model;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String test() {

        logger.debug("Request /test");

        return "Test OK";

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ChatMessageEnvelope login(@RequestBody final LoginRequest request) {

        logger.debug("Request /login");
        logger.debug("request: " + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        if (model.isNicknameReserved(request.getNickname())) {
            result = ChatSystemMessage.USER_NICKNAME_IS_RESERVED.toString();
        } else if (!model.isSigned(request.getNickname())) {
            model.signin(request.getNickname(), request.getPassword(), request.getColor());
            result = ChatSystemMessage.USER_SIGNED_IN_AND_LOGGED_IN.toString();
        } else if (!model.isPasswordCorrect(request.getNickname(), request.getPassword())) {
            result = ChatSystemMessage.USER_PASSWORD_NOT_CORRECT.toString();
        } else {

            if (model.isLogged(request.getNickname())) {
                result = ChatSystemMessage.USER_ALREADY_LOGGED_IN.toString();
            } else {
                result = ChatSystemMessage.USER_LOGGED_IN.toString();
            }

            model.login(request.getNickname());

        }

        logger.debug("result: " + result);

        return model.postMessage(model.getAdmin().getNickname(), request.getNickname(), result);

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ChatMessageEnvelope logout(@RequestBody final LogoutRequest request) {

        logger.debug("Request /logout");
        logger.debug("request: " + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        if (model.isNicknameReserved(request.getNickname())) {
            result = ChatSystemMessage.USER_NICKNAME_IS_RESERVED.toString();
        } else if (!model.isSigned(request.getNickname())) {
            result = ChatSystemMessage.USER_NOT_SIGNED_IN.toString();
        } else if (!model.isPasswordCorrect(request.getNickname(), request.getPassword())) {
            result = ChatSystemMessage.USER_PASSWORD_NOT_CORRECT.toString();
        } else {

            if (model.isLogged(request.getNickname())) {
                result = ChatSystemMessage.USER_LOGGED_OUT.toString();
            } else {
                result = ChatSystemMessage.USER_ALREADY_LOGGED_OUT.toString();
            }

            model.logout(request.getNickname());

        }

        logger.debug("result: " + result);

        return model.postMessage(model.getAdmin().getNickname(), request.getNickname(), result);

    }

    @RequestMapping(value = "/post/message", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ChatMessageEnvelope postMessage(@RequestBody final PostMessageRequest request) {

        logger.debug("Request /post/message");
        logger.debug("request: " + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        if (model.isNicknameReserved(request.getMessage().getSenderNickname())) {
            result = ChatSystemMessage.SENDER_NICKNAME_IS_RESERVED.toString();
        } else if (!model.isSigned(request.getMessage().getSenderNickname())) {
            result = ChatSystemMessage.SENDER_NOT_SIGNED_IN.toString();
        } else if (!model.isPasswordCorrect(request.getMessage().getSenderNickname(), request.getPassword())) {
            result = ChatSystemMessage.USER_PASSWORD_NOT_CORRECT.toString();
        } else if (!model.isLogged(request.getMessage().getSenderNickname())) {
            result = ChatSystemMessage.SENDER_NOT_LOGGED_IN.toString();
        } else if (!model.isSigned(request.getMessage().getReceiverNickname())) {
            result = ChatSystemMessage.RECEIVER_NOT_SIGNED_IN.toString();
        } else if (!model.isLogged(request.getMessage().getReceiverNickname())) {
            result = ChatSystemMessage.RECEIVER_NOT_LOGGED_IN.toString();
        } else {
            model.postMessage(request.getMessage());
            result = ChatSystemMessage.USER_POST_MESSAGE.toString();
        }

        logger.debug("result: " + result);

        return model.postMessage(model.getAdmin().getNickname(), request.getMessage().getSenderNickname(), result);

    }

    @RequestMapping(value = "/get/messages", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public LinkedList<ChatMessageEnvelope> getMessages(@RequestBody final GetMessagesRequest request) {

        logger.debug("Request /get/messages");
        logger.debug("request: " + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        LinkedList<ChatMessageEnvelope> messages = new LinkedList<ChatMessageEnvelope>();

        if (model.isNicknameReserved(request.getNickname())) {
            result = ChatSystemMessage.USER_NICKNAME_IS_RESERVED.toString();
        } else if (!model.isSigned(request.getNickname())) {
            result = ChatSystemMessage.USER_NOT_SIGNED_IN.toString();
        } else if (!model.isPasswordCorrect(request.getNickname(), request.getPassword())) {
            result = ChatSystemMessage.USER_PASSWORD_NOT_CORRECT.toString();
        } else if (!model.isLogged(request.getNickname())) {
            result = ChatSystemMessage.USER_NOT_LOGGED_IN.toString();
        } else {
            result = ChatSystemMessage.USER_GET_MESSAGES.toString();
        }

        logger.debug("result: " + result);

        model.postMessage(model.getAdmin().getNickname(), request.getNickname(), result);

        return model.getMessages(request.getNickname());

    }

}
