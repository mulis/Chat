package org.mulis.chat.model;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatModel {

    static Logger logger = Logger.getLogger(ChatModel.class.getName());

    private final HashMap<String, ChatUser> reservedUsers = new HashMap<String, ChatUser>() {{
        put(ReservedChatUser.ADMIN.getUser().getNickname(), ReservedChatUser.ADMIN.getUser());
        put(ReservedChatUser.ANY.getUser().getNickname(), ReservedChatUser.ANY.getUser());
    }};

    private final HashMap<String, ChatUser> users = (HashMap<String, ChatUser>) Collections.synchronizedMap(new HashMap<String, ChatUser>(reservedUsers));

    private final LinkedList<ChatPostedMessage> messages = (LinkedList<ChatPostedMessage>) Collections.synchronizedList(new LinkedList<ChatPostedMessage>());

    private int messageIndex = -1;

    ChatModel() {
        signin("user1", "", "green");
        login("user1");
        postMessage(getUser("user1").getNickname(), "", "message 1");
        postMessage(getUser("user1").getNickname(), "user2", "message 2");
    }

    public ChatUser getUser(String nickname) {
        return users.get(nickname);
    }

    private void addUser(ChatUser user) {
        users.put(user.getNickname(), user);
    }

    private void removeUser(String nickname) {
        users.remove(nickname);
    }

    public boolean isNicknameReserved(String nickname) {
        return reservedUsers.containsKey(nickname);
    }

    public void signin(String nickname, String password, String color) {
        addUser(new ChatUser(nickname, password, color, true, messageIndex));
    }

    public void signout(String nickname) {
        removeUser(nickname);
    }

    public boolean isSigned(String nickname) {
        return users.containsKey(nickname);
    }

    public boolean isPasswordCorrect(String nickname, String password) {
        return getUser(nickname).getPassword().equals(password);
    }

    public void login(String nickname) {
        ChatUser user = getUser(nickname);
        user.setLogged(true);
        user.setLastMessageIndex(messageIndex);
    }

    public void logout(String nickname) {
        getUser(nickname).setLogged(false);
    }

    public boolean isLogged(String nickname) {
        return getUser(nickname).isLogged();
    }

    public void changePassword(String nickname, String password) {
        getUser(nickname).setPassword(password);
    }

    public void changeColor(String nickname, String color) {
        getUser(nickname).setColor(color);
    }

    public ChatUser getAdmin() {
        return ReservedChatUser.ADMIN.getUser();
    }

    public ChatUser getAny() {
        return ReservedChatUser.ANY.getUser();
    }

    public ChatPostedMessage postMessage(String senderNickname, String receiverNickname, String text) {
        return postMessage(new ChatMessage(senderNickname, receiverNickname, text));
    }

    public ChatPostedMessage postMessage(ChatMessage message) {
        ChatPostedMessage postedMessage = new ChatPostedMessage(++messageIndex, new Date(), message);
        messages.push(postedMessage);
        return postedMessage;
    }

    public LinkedList<ChatPostedMessage> getMessages() {
        return this.messages;
    }

    public LinkedList<ChatPostedMessage> getMessages(String nickname) {

        logger.debug("getMessages");
        logger.debug("nickname: " + nickname);

        ChatUser user = getUser(nickname);
        logger.debug("user: " + user);
        LinkedList<ChatPostedMessage> userMessages = new LinkedList<ChatPostedMessage>();
        Iterator<ChatPostedMessage> iterator = this.messages.descendingIterator();
        ChatPostedMessage message;

        while (iterator.hasNext()) {

            message = iterator.next();

            logger.debug("message: " + message);

            if (message.getIndex() > user.getLastMessageIndex()) {
                if (message.getMessage().getSenderNickname().equals(user.getNickname()) ||
                        message.getMessage().getReceiverNickname().equals(user.getNickname()) ||
                        message.getMessage().getReceiverNickname().equals(getAny().getNickname())) {
                    userMessages.push(message);
                }
            } else {
                break;
            }

        }

        user.setLastMessageIndex(user.getLastMessageIndex() + userMessages.size());

        return userMessages;

    }

    public LinkedList<ChatPostedMessage> getMessages(String nickname, int fromId, int toId) {

        logger.debug("getMessages");
        logger.debug("nickname: " + nickname);

        ChatUser user = getUser(nickname);
        logger.debug("user: " + user);
        LinkedList<ChatPostedMessage> userMessages = new LinkedList<ChatPostedMessage>();
        Iterator<ChatPostedMessage> iterator = this.messages.descendingIterator();
        ChatPostedMessage message;

        while (iterator.hasNext()) {

            message = iterator.next();

            logger.debug("message: " + message);

            if (message.getIndex() > user.getLastMessageIndex()) {
                if (message.getMessage().getSenderNickname().equals(user.getNickname()) ||
                        message.getMessage().getReceiverNickname().equals(user.getNickname()) ||
                        message.getMessage().getReceiverNickname().equals(getAny().getNickname())) {
                    userMessages.push(message);
                }
            } else {
                break;
            }

        }

        user.setLastMessageIndex(user.getLastMessageIndex() + userMessages.size());

        return userMessages;

    }

}
