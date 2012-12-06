package org.mulis.chat.model;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

@Service
public class ChatModel {

    private final HashMap<String, ChatUser> users = new HashMap<String, ChatUser>();
    private final LinkedList<ChatMessage> messages = new LinkedList<ChatMessage>();

    ChatModel() {
        ChatUser user1 = new ChatUser("user1", 0);
        addUser(user1);
        addMessage(user1, null, "message 1");
    }

    public void addUser(String nickname, int color) {
        users.put(nickname, new ChatUser(nickname, color));
    }

    public void addUser(ChatUser user) {
        users.put(user.getNickname(), user);
    }

    public ChatUser getUser(String nickname) {
        return users.get(nickname);
    }

    public Date addMessage(ChatUser userFrom, ChatUser userTo, String text) {
        ChatMessage message = new ChatMessage(userFrom, userTo, text);
        return addMessage(message);
    }

    public Date addMessage(ChatMessage message) {
        messages.addLast(message);
        return message.getDate();
    }

    public LinkedList<ChatMessage> getMessagesAfter(Date date, ChatUser userFor) {

        LinkedList<ChatMessage> massagesAfter = new LinkedList<ChatMessage>();
        Iterator<ChatMessage> iterator = messages.descendingIterator();
        ChatMessage message;

        while (iterator.hasNext()) {

            message = iterator.next();

            if (message.getDate().after(date)) {
                if (message.getUserTo() != null && !message.getUserTo().equals(userFor)) {
                    continue;
                }
                massagesAfter.addFirst(message);
            } else {
                break;
            }
        }

        return massagesAfter;

    }

}
