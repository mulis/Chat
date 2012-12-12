package org.mulis.chat.controller;

import org.apache.log4j.Logger;
import org.mulis.chat.controller.dto.*;
import org.mulis.chat.model.ChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
//@RequestMapping("/")
public class ChatController {

    static Logger logger = Logger.getLogger(ChatController.class.getName());

    @Inject
    private ChatModel model;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ChatResponse test() {

        logger.debug("Request /test");

        ChatResponse response = new ChatResponse();
        response.setCode(0);
        response.setStatus("Test OK");

        return response;

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ChatResponse login(@RequestBody final LoginRequest request) throws ForbiddenException {

        logger.debug("Request /login");
        logger.debug("request: " + request);

        ChatResponse response = new ChatResponse();

        if (model.isNicknameReserved(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NICKNAME_IS_RESERVED.format(request.getNickname()));

        } else if (!model.isSigned(request.getNickname())) {

            response.setCode(ChatErrorCode.OK.getCode());
            response.setStatus(ChatStatus.USER_SIGNED_IN_AND_LOGGED_IN.format(request.getNickname()));

            model.signin(request.getNickname(), request.getPassword(), request.getColor());

        } else if (!model.isPasswordCorrect(request.getNickname(), request.getPassword())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_PASSWORD_NOT_CORRECT.format(request.getNickname()));

        } else {

            if (model.isLogged(request.getNickname())) {

                response.setCode(ChatErrorCode.IGNORE.getCode());
                response.setStatus(ChatStatus.USER_ALREADY_LOGGED_IN.format(request.getNickname()));

            } else {

                response.setCode(ChatErrorCode.OK.getCode());
                response.setStatus(ChatStatus.USER_LOGGED_IN.format(request.getNickname()));

            }

            model.login(request.getNickname());

        }

        model.postMessage(model.getAdmin().getNickname(), request.getNickname(), response.getStatus());

        logger.debug("response: " + response);

        return response;

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ChatResponse logout(@RequestBody final LogoutRequest request) throws ForbiddenException {

        logger.debug("Request /logout");
        logger.debug("request: " + request);

        ChatResponse response = new ChatResponse();

        if (model.isNicknameReserved(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NICKNAME_IS_RESERVED.format(request.getNickname()));

        } else if (!model.isSigned(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NOT_SIGNED_IN.format(request.getNickname()));

        } else if (!model.isPasswordCorrect(request.getNickname(), request.getPassword())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_PASSWORD_NOT_CORRECT.format(request.getNickname()));

        } else {

            if (model.isLogged(request.getNickname())) {

                response.setCode(ChatErrorCode.OK.getCode());
                response.setStatus(ChatStatus.USER_LOGGED_OUT.format(request.getNickname()));

            } else {

                response.setCode(ChatErrorCode.IGNORE.getCode());
                response.setStatus(ChatStatus.USER_ALREADY_LOGGED_OUT.format(request.getNickname()));

            }

            model.logout(request.getNickname());

        }

        model.postMessage(model.getAdmin().getNickname(), request.getNickname(), response.getStatus());

        logger.debug("response: " + response);

        return response;

    }

    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ChatResponse changePassword(@RequestBody final ChangePasswordRequest request) throws ForbiddenException {

        logger.debug("Request /change/password");
        logger.debug("request: " + request);

        ChatResponse response = new ChatResponse();

        if (model.isNicknameReserved(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NICKNAME_IS_RESERVED.format(request.getNickname()));

        } else if (!model.isSigned(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NOT_SIGNED_IN.format(request.getNickname()));

        } else if (!model.isPasswordCorrect(request.getNickname(), request.getPassword())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_PASSWORD_NOT_CORRECT.format(request.getNickname()));

        } else if (!model.isLogged(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NOT_LOGGED_IN.format(request.getNickname()));

        } else {

            response.setCode(ChatErrorCode.OK.getCode());
            response.setStatus(ChatStatus.USER_PASSWORD_CHANGED.format(request.getNickname()));

            model.changePassword(request.getNickname(), request.getNewPassword());

        }

        model.postMessage(model.getAdmin().getNickname(), request.getNickname(), response.getStatus());

        logger.debug("response: " + response);

        return response;

    }

    @RequestMapping(value = "/change/color", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ChatResponse changeColor(@RequestBody final ChangeColorRequest request) throws ForbiddenException {

        logger.debug("Request /change/color");
        logger.debug("request: " + request);

        ChatResponse response = new ChatResponse();

        if (model.isNicknameReserved(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NICKNAME_IS_RESERVED.format(request.getNickname()));

        } else if (!model.isSigned(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NOT_SIGNED_IN.format(request.getNickname()));

        } else if (!model.isPasswordCorrect(request.getNickname(), request.getPassword())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_PASSWORD_NOT_CORRECT.format(request.getNickname()));

        } else if (!model.isLogged(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NOT_LOGGED_IN.format(request.getNickname()));

        } else {

            response.setCode(ChatErrorCode.OK.getCode());
            response.setStatus(ChatStatus.USER_COLOR_CHANGED.format(request.getNickname()));

            model.changeColor(request.getNickname(), request.getColor());

        }

        model.postMessage(model.getAdmin().getNickname(), request.getNickname(), response.getStatus());

        logger.debug("response: " + response);

        return response;

    }

    @RequestMapping(value = "/post/message", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ChatResponse postMessage(@RequestBody final PostMessageRequest request) throws ForbiddenException {

        logger.debug("Request /post/message");
        logger.debug("request: " + request);

        ChatResponse response = new ChatResponse();

        if (model.isNicknameReserved(request.getMessage().getSenderNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.SENDER_NICKNAME_IS_RESERVED.format(request.getMessage().getSenderNickname()));

        } else if (!model.isSigned(request.getMessage().getSenderNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.SENDER_NOT_SIGNED_IN.format(request.getMessage().getSenderNickname()));

        } else if (!model.isPasswordCorrect(request.getMessage().getSenderNickname(), request.getPassword())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_PASSWORD_NOT_CORRECT.format(request.getMessage().getSenderNickname()));

        } else if (!model.isLogged(request.getMessage().getSenderNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.SENDER_NOT_LOGGED_IN.format(request.getMessage().getSenderNickname()));

        } else if (!model.isSigned(request.getMessage().getReceiverNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.RECEIVER_NOT_SIGNED_IN.format(request.getMessage().getReceiverNickname()));

        } else if (!model.isLogged(request.getMessage().getReceiverNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.RECEIVER_NOT_LOGGED_IN.format(request.getMessage().getReceiverNickname()));

        } else {

            response.setCode(ChatErrorCode.OK.getCode());
            response.setStatus(ChatStatus.USER_POST_MESSAGE.format(request.getMessage().getSenderNickname()));

            model.postMessage(request.getMessage());

        }

        model.postMessage(model.getAdmin().getNickname(), request.getMessage().getSenderNickname(), response.getStatus());

        logger.debug("response: " + response);

        return response;

    }

    @RequestMapping(value = "/get/messages", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GetMessagesResponse getMessages(@RequestBody final GetMessagesRequest request) {

        logger.debug("Request /get/postedMessages");
        logger.debug("request: " + request);

        GetMessagesResponse response = new GetMessagesResponse();

        if (model.isNicknameReserved(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NICKNAME_IS_RESERVED.format(request.getNickname()));

        } else if (!model.isSigned(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NOT_SIGNED_IN.format(request.getNickname()));

        } else if (!model.isPasswordCorrect(request.getNickname(), request.getPassword())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_PASSWORD_NOT_CORRECT.format(request.getNickname()));

        } else if (!model.isLogged(request.getNickname())) {

            response.setCode(ChatErrorCode.STOP.getCode());
            response.setStatus(ChatStatus.USER_NOT_LOGGED_IN.format(request.getNickname()));

        } else {

            response.setCode(ChatErrorCode.OK.getCode());
            response.setStatus(ChatStatus.USER_GET_MESSAGES.format(request.getNickname()));

        }

        model.postMessage(model.getAdmin().getNickname(), request.getNickname(), response.getStatus());

        response.setMessages(model.getMessages(request.getNickname()));

        logger.debug("response: " + response);

        return response;

    }

    @RequestMapping(value = "/get/messages/{nickname}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GetMessagesResponse getMessagesTest(@PathVariable String nickname) {

        logger.debug("Request /get/messages/{nickname}");

        GetMessagesResponse response = new GetMessagesResponse();
        response.setCode(0);
        response.setStatus("");
        response.setMessages(model.getMessages(nickname));

        return response;

    }

    @RequestMapping(value = "/get/messages/all", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GetMessagesResponse getMessagesTest() {

        logger.debug("Request /get/messages/all");

        GetMessagesResponse response = new GetMessagesResponse();
        response.setCode(0);
        response.setStatus("");
        response.setMessages(model.getMessages());

        return response;

    }

}
