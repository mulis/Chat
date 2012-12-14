Chat.Model = function(chat) {

    var me = this;

    this.chat = chat;

    this.messages = [];
    this.firstNewMessageIndex = this.messages.length;

    this.users = [];

}

Chat.Model.prototype.addNewMessages = function(messages) {
    if (messages) {
        for (var i = 0; i < messages.length; i++) {
            this.messages[this.messages.length] = messages[i];
        }
    }
}

Chat.Model.prototype.getNewMessages = function() {
    var newMessages = [];
    for (var i = this.firstNewMessageIndex; i < this.messages.length; i++) {
        newMessages[newMessages.length] = this.messages[i];
    }
    this.firstNewMessageIndex = this.messages.length;
    return newMessages;
}

Chat.Model.prototype.setUsers = function(users) {
    this.users = users;
}

Chat.Model.prototype.getUsers = function() {
    return this.users;
}

Chat.Model.prototype.getUsersByNickname = function(nicknames) {

    var foundedUsers = new Array(nicknames.length);

    for (var i = 0; i < this.users.length; i++) {
        var user = this.users[i];
        for (var j = 0; j < nicknames.length; j++) {
            if (user.nickname == nicknames[j]) {
                foundedUsers[j] = user;
            }
        }
    }

    return foundedUsers;

}
