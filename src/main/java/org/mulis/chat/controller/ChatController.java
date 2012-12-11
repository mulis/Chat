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
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    String test() {

        logger.debug("Request /test");

        return "Test OK";

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    String login(@RequestBody final LoginRequest request) throws ForbiddenException {

        logger.debug("Request /login");
        logger.debug("request: " + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        if (model.isNicknameReserved(request.getNickname())) {
            throw new ForbiddenException(ChatSystemMessage.USER_NICKNAME_IS_RESERVED.format(request.getNickname()));
        } else if (!model.isSigned(request.getNickname())) {
            result = ChatSystemMessage.USER_SIGNED_IN_AND_LOGGED_IN.format(request.getNickname());
            model.signin(request.getNickname(), request.getPassword(), request.getColor());
        } else if (!model.isPasswordCorrect(request.getNickname(), request.getPassword())) {
            throw new ForbiddenException(ChatSystemMessage.USER_PASSWORD_NOT_CORRECT.format(request.getNickname()));
        } else {

            if (model.isLogged(request.getNickname())) {
                result = ChatSystemMessage.USER_ALREADY_LOGGED_IN.format(request.getNickname());
            } else {
                result = ChatSystemMessage.USER_LOGGED_IN.format(request.getNickname());
            }

            model.login(request.getNickname());

        }

        model.postMessage(model.getAdmin().getNickname(), request.getNickname(), result);

        logger.debug("result: " + result);

        return result;

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    String logout(@RequestBody final LogoutRequest request) throws ForbiddenException {

        logger.debug("Request /logout");
        logger.debug("request: " + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        if (model.isNicknameReserved(request.getNickname())) {
            throw new ForbiddenException(ChatSystemMessage.USER_NICKNAME_IS_RESERVED.format(request.getNickname()));
        } else if (!model.isSigned(request.getNickname())) {
            throw new ForbiddenException(ChatSystemMessage.USER_NICKNAME_IS_RESERVED.format(request.getNickname()));
        } else if (!model.isPasswordCorrect(request.getNickname(), request.getPassword())) {
            throw new ForbiddenException(ChatSystemMessage.USER_PASSWORD_NOT_CORRECT.format(request.getNickname()));
        } else {

            if (model.isLogged(request.getNickname())) {
                result = ChatSystemMessage.USER_LOGGED_OUT.format(request.getNickname());
            } else {
                result = ChatSystemMessage.USER_ALREADY_LOGGED_OUT.format(request.getNickname());
            }

            model.logout(request.getNickname());

        }

        model.postMessage(model.getAdmin().getNickname(), request.getNickname(), result);

        logger.debug("result: " + result);

        return result;

    }

    @RequestMapping(value = "/post/message", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    String postMessage(@RequestBody final PostMessageRequest request) throws ForbiddenException {

        logger.debug("Request /post/message");
        logger.debug("request: " + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        if (model.isNicknameReserved(request.getMessage().getSenderNickname())) {
            throw new ForbiddenException(ChatSystemMessage.SENDER_NICKNAME_IS_RESERVED.format(request.getMessage().getSenderNickname()));
        } else if (!model.isSigned(request.getMessage().getSenderNickname())) {
            throw new ForbiddenException(ChatSystemMessage.SENDER_NOT_SIGNED_IN.format(request.getMessage().getSenderNickname()));
        } else if (!model.isPasswordCorrect(request.getMessage().getSenderNickname(), request.getPassword())) {
            throw new ForbiddenException(ChatSystemMessage.USER_PASSWORD_NOT_CORRECT.format(request.getMessage().getSenderNickname()));
        } else if (!model.isLogged(request.getMessage().getSenderNickname())) {
            throw new ForbiddenException(ChatSystemMessage.SENDER_NOT_LOGGED_IN.format(request.getMessage().getSenderNickname()));
        } else if (!model.isSigned(request.getMessage().getReceiverNickname())) {
            throw new ForbiddenException(ChatSystemMessage.RECEIVER_NOT_SIGNED_IN.format(request.getMessage().getReceiverNickname()));
        } else if (!model.isLogged(request.getMessage().getReceiverNickname())) {
            throw new ForbiddenException(ChatSystemMessage.RECEIVER_NOT_LOGGED_IN.format(request.getMessage().getReceiverNickname()));
        } else {
            result = ChatSystemMessage.USER_POST_MESSAGE.format(request.getMessage().getSenderNickname());
            model.postMessage(request.getMessage());
        }

        model.postMessage(model.getAdmin().getNickname(), request.getMessage().getSenderNickname(), result);

        logger.debug("result: " + result);

        return result;

    }

    @RequestMapping(value = "/get/messages", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    LinkedList<ChatMessageEnvelope> getMessages(@RequestBody final GetMessagesRequest request) {

        logger.debug("Request /get/messages");
        logger.debug("request: " + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        LinkedList<ChatMessageEnvelope> messages = new LinkedList<ChatMessageEnvelope>();

        if (model.isNicknameReserved(request.getNickname())) {
            throw new ForbiddenException(ChatSystemMessage.USER_NICKNAME_IS_RESERVED.format(request.getNickname()));
        } else if (!model.isSigned(request.getNickname())) {
            throw new ForbiddenException(ChatSystemMessage.USER_NOT_SIGNED_IN.format(request.getNickname()));
        } else if (!model.isPasswordCorrect(request.getNickname(), request.getPassword())) {
            throw new ForbiddenException(ChatSystemMessage.USER_PASSWORD_NOT_CORRECT.format(request.getNickname()));
        } else if (!model.isLogged(request.getNickname())) {
            throw new ForbiddenException(ChatSystemMessage.USER_NOT_LOGGED_IN.format(request.getNickname()));
        } else {
            result = ChatSystemMessage.USER_GET_MESSAGES.format(request.getNickname());
        }

        model.postMessage(model.getAdmin().getNickname(), request.getNickname(), result);

        logger.debug("result: " + result);

        return model.getMessages(request.getNickname());

    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleForbiddenException(ForbiddenException exception) {
        return exception.getMessage();
    }

}
