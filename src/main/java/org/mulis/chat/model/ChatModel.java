package org.mulis.chat.model;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

@Service
public class ChatModel {

    private final HashMap<String, ChatUser> reservedUsers = new HashMap<String, ChatUser>() {{
        put(ReservedChatUser.ADMIN.getUser().getNickname(), ReservedChatUser.ADMIN.getUser());
        put(ReservedChatUser.ANY.getUser().getNickname(), ReservedChatUser.ANY.getUser());
    }};

    private final HashMap<String, ChatUser> users = new HashMap<String, ChatUser>(reservedUsers);

    private final LinkedList<ChatMessageEnvelope> messageEnvelopes = new LinkedList<ChatMessageEnvelope>();

    private int messageIndex = 0;

    ChatModel() {
        ChatUser user1 = new ChatUser("user1", "", "green", false, 0);
        addUser(user1);
        postMessage(user1.getNickname(), null, "message 1");
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
        getUser(nickname).setLogged(true);
    }

    public void logout(String nickname) {
        getUser(nickname).setLogged(false);
    }

    public boolean isLogged(String nickname) {
        return getUser(nickname).isLogged();
    }

    public ChatUser getAdmin() {
        return ReservedChatUser.ADMIN.getUser();
    }

    public ChatUser getAny() {
        return ReservedChatUser.ANY.getUser();
    }

    public ChatMessageEnvelope postMessage(String senderNickname, String receiverNickname, String text) {
        return postMessage(new ChatMessage(senderNickname, receiverNickname, text));
    }

    public ChatMessageEnvelope postMessage(ChatMessage message) {
        ChatMessageEnvelope envelope = new ChatMessageEnvelope(messageIndex++, new Date(), message);
        messageEnvelopes.push(envelope);
        return envelope;
    }

    public LinkedList<ChatMessageEnvelope> getMessages(String nickname) {

        ChatUser user = getUser(nickname);
        LinkedList<ChatMessageEnvelope> userEnvelopes = new LinkedList<ChatMessageEnvelope>();
        Iterator<ChatMessageEnvelope> iterator = this.messageEnvelopes.iterator();
        ChatMessageEnvelope envelope;

        while (iterator.hasNext()) {

            envelope = iterator.next();

            if (envelope.getIndex() > user.getLastMessageIndex()) {
                if (envelope.getMessage().getSenderNickname().equals(user.getNickname()) ||
                        envelope.getMessage().getReceiverNickname().equals(user.getNickname()) ||
                        envelope.getMessage().getReceiverNickname().equals(getAny().getNickname())) {
                    userEnvelopes.push(envelope);
                }
            } else {
                break;
            }
        }

        return userEnvelopes;

    }

}
