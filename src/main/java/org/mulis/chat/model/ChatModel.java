package org.mulis.chat.model;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
public class ChatModel {

    static Logger logger = Logger.getLogger(ChatModel.class.getName());

    private final HashMap<String, ChatUser> reservedUsers = new HashMap<String, ChatUser>() {{
        put(ReservedChatUser.ADMIN.getUser().getNickname(), ReservedChatUser.ADMIN.getUser());
        put(ReservedChatUser.ANY.getUser().getNickname(), ReservedChatUser.ANY.getUser());
    }};

    private final ConcurrentSkipListMap<String, ChatUser> users = new ConcurrentSkipListMap<String, ChatUser>(reservedUsers);

    private final ConcurrentSkipListMap<Integer, ChatPostedMessage> messages = new ConcurrentSkipListMap<Integer, ChatPostedMessage>();

    private int messageIndex = -1;

    ChatModel() {
        signin("mulis", "slim", "green");
        //login("mulis");
        postMessage(getUser("mulis").getNickname(), "", "hello everybody");
        postMessage(getUser("mulis").getNickname(), "somebody", "hello somebody");
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
        addUser(new ChatUser(nickname, password, color, false, -1));
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
        ChatUser user = getUser(nickname);
        user.setLogged(false);
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

    public int getMessageIndex() {
        return messageIndex;
    }

    public ChatPostedMessage postMessage(String senderNickname, String receiverNickname, String text) {
        return postMessage(new ChatMessage(senderNickname, receiverNickname, text));
    }

    public ChatPostedMessage postMessage(ChatMessage message) {

        logger.debug("postMessage");

        ChatPostedMessage postedMessage = new ChatPostedMessage(++messageIndex, new Date(), message);
        messages.put(postedMessage.getIndex(), postedMessage);

        logger.debug("postedMessage: " + postedMessage);

        return postedMessage;

    }

    public Collection<ChatUser> getUsers() {
        return users.values();
    }

    public Collection<ChatPostedMessage> getMessages() {
        return this.messages.values();
    }

    public Collection<ChatPostedMessage> getNewMessages(String nickname) {

        logger.debug("getNewMessages");
        logger.debug("nickname: " + nickname);

        ChatUser user = getUser(nickname);

        Collection<ChatPostedMessage> userMessages = this.messages.tailMap(user.getLastMessageIndex(), false).values();
        user.setLastMessageIndex(user.getLastMessageIndex() + userMessages.size());

        logger.debug("userMessages: " + userMessages.size());

        return userMessages;

    }

    public Collection<ChatPostedMessage> getMessages(String nickname, int fromId, int toId) {

        logger.debug("getMessages");
        logger.debug("nickname: " + nickname);
        logger.debug("fromId: " + fromId);
        logger.debug("toId: " + toId);

        ChatUser user = getUser(nickname);

        Collection<ChatPostedMessage> userMessages = this.messages.subMap(fromId, true, toId, true).values();

        Iterator<ChatPostedMessage> iterator = userMessages.iterator();
        ChatPostedMessage postedMessage;
        boolean match;

        while (iterator.hasNext()) {

            postedMessage = iterator.next();

            match = postedMessage.getMessage().getSenderNickname().equals(user.getNickname()) ||
                    postedMessage.getMessage().getReceiverNickname().equals(user.getNickname()) ||
                    postedMessage.getMessage().getReceiverNickname().equals(getAny().getNickname());

            if (!match) iterator.remove();

        }

        logger.debug("userMessages: " + userMessages.size());

        return userMessages;

    }

}
