package org.mulis.chat.controller;

import org.apache.log4j.Logger;
import org.mulis.chat.model.ChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
@RequestMapping("/")
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
    public void login(@RequestBody LoginRequestBody request) {

        logger.debug("Request /login");
        logger.debug("request=" + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        if (model.isSigned(request.getNickname())) {

            if (model.isLogged(request.getNickname())) {

                result = ChatSystemMessage.USER_ALREADY_LOGGED_IN.toString();

            } else {

                model.login(request.getNickname());
                result = ChatSystemMessage.USER_LOGGED_IN.toString();

            }

        } else {

            model.addUser(request.getNickname(), request.getColor());
            result = ChatSystemMessage.USER_SIGNED_IN_AND_LOGGED_IN.toString();

        }

        model.postMessage(model.getAdmin(), model.getUser(request.getNickname()), result);
        logger.debug("result=" + result);

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestBody LogoutRequestBody request) {

        logger.debug("Request /logout");
        logger.debug("request=" + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        if (model.isSigned(request.getNickname())) {

            if (model.isLogged(request.getNickname())) {

                model.logout(request.getNickname());
                result = ChatSystemMessage.USER_LOGGED_OUT.toString();

            } else {

                result = ChatSystemMessage.USER_ALREADY_LOGGED_OUT.toString();

            }

        } else {

            result = ChatSystemMessage.USER_NOT_SIGNED_IN.toString();

        }

        model.postMessage(model.getAdmin(), model.getUser(request.getNickname()), result);
        logger.debug("result=" + result);

    }

    @RequestMapping(value = "/post/message", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void postMessage(@RequestBody PostMessageRequestBody request) {

        logger.debug("Request /post/message");
        logger.debug("request=" + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        if (model.isSigned(request.getSenderNickname())) {

            if (model.isLogged(request.getSenderNickname())) {

                if (model.isSigned(request.getReceiverNickname())) {

                    if (model.isLogged(request.getReceiverNickname())) {

                        model.postMessage(
                                request.getSenderNickname(),
                                request.getReceiverNickname(),
                                request.getText());

                        result = ChatSystemMessage.MESSAGE_POSTED.toString();

                    } else {

                        result = ChatSystemMessage.RECEIVER_NOT_LOGGED_IN.toString();
                        model.postMessage(model.getAdmin(), model.getUser(request.getSenderNickname()), result);

                    }

                } else {

                    result = ChatSystemMessage.RECEIVER_NOT_SIGNED_IN.toString();
                    model.postMessage(model.getAdmin(), model.getUser(request.getSenderNickname()), result);

                }

            } else {

                result = ChatSystemMessage.SENDER_NOT_LOGGED_IN.toString();

            }

        } else {

            result = ChatSystemMessage.SENDER_NOT_SIGNED_IN.toString();

        }

        logger.debug("result=" + result);

    }

    @RequestMapping(value = "/get/messages", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public GetMessagesResponseBody getMessages(@RequestBody GetMessagesRequestBody request) {

        logger.debug("Request /get/messages");
        logger.debug("request=" + request);

        String result = ChatSystemMessage.SYSTEM_STATE_UNKNOWN.toString();

        GetMessagesResponseBody response = new GetMessagesResponseBody();

        if (model.isSigned(request.getNickname())) {

            if (model.isLogged(request.getNickname())) {

                response = new GetMessagesResponseBody(model.getMessages(request.getNickname(), request.getIndex()));

            } else {

                result = ChatSystemMessage.USER_NOT_LOGGED_IN.toString();

            }

        } else {

            result = ChatSystemMessage.USER_NOT_SIGNED_IN.toString();

        }

        logger.debug("result=" + result);
        logger.debug("response=" + response);

        return response;

    }

}
