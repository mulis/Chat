package org.mulis.chat.controller;

import org.apache.log4j.Logger;
import org.mulis.chat.model.ChatMessage;
import org.mulis.chat.model.ChatModel;
import org.mulis.chat.model.ChatUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;

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

    @RequestMapping(value = "/post/message", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Date addMessage(@RequestParam("userFrom") ChatUser userFrom, @RequestParam("userTo") ChatUser userTo, @RequestParam("text") String text) {

        logger.debug("Request /post/message");

        ChatMessage message = new ChatMessage(userFrom, userTo, text);
        model.addMessage(message);

        return message.getDate();

    }

    @RequestMapping(value = "/get/messages/{nickname}/{time}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String getMessages(@PathVariable("time") long time, @PathVariable("nickname") String nickname) {

        logger.debug("Request /get/messages");
        logger.debug("time=" + time);
        logger.debug("nickname=" + nickname);

        return model.getMessagesAfter(new Date(time), model.getUser(nickname)).toString();

    }

}
