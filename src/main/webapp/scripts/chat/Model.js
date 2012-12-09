Chat.Model = function(chat) {

    var me = this;

    this.chat = chat;

    this.messages = [];
    this.firstNewMessageIndex = 0;

}

Chat.Model.prototype.addNewMessages = function(data) {
    if (data) {
        for (var i = 0; i < data.length; i++) {
            this.messages[this.messages] = data[i];
        }
    }
}

Chat.Model.prototype.getNewMessages = function() {
    var newMessages = [];
    for (var i = this.firstNewMessageIndex; i < this.messages.length; i++) {
        newMessages[newMessages.length] = this.messages[i];
    }
    return newMessages;
}
