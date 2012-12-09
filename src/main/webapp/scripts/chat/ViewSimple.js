Chat.View = function(chat) {

    var me = this;

    this.chat = chat;
    this.name = "ViewSimple";
    this.loadTemplate(this.name);

    $(this.chat.element).bind(
        chat.events.VIEW_TEMPLATE_LOAD,
        function(aEvent) {
            me.initControls();
        }
    );

    $(this.chat.element).bind(
        chat.events.RECEIVE_MESSAGES_READY,
        function(aEvent) {
            me.updateView();
        }
    );

    $(this.chat.element).bind(
        chat.events.SERVICE_ERROR,
        function(aEvent) {
            me.updateView();
        }
    );

}

Chat.View.prototype = new Chat.AbstractView();

Chat.View.prototype.initControls = function() {

    var me = this;

    this.controls = {
         input : this.chat.element.find(".chat-input"),
         output : this.chat.element.find(".chat-output")
     };

    this.controls.input.bind(
        "keypress",
        function(aEvent) {
            var keycode = (aEvent.keyCode ? aEvent.keyCode : aEvent.which);
            if(keycode == "13"){
                me.parseInput(me.controls.input.val());
            }
        }
    );

}

Chat.View.prototype.updateView = function() {
    var output = this.controls.output;
    var newMessages = this.chat.model.getNewMessages();
    for (var i = 0; i < newMessages.length; i++) {
        output.append(newMessages[i] + "\n");
    }
    //output.scrollTop(output[0].scrollHeight - output.height());
}

Chat.View.prototype.clearOutput = function() {
    this.controls.output.text("");
}

Chat.View.prototype.parseInput = function(message) {

    message = message.trim();

    if (message.search(".") == 0) {
        this.parseCommand(message);
    }
    else {
        this.parseSendMessage(message);
    }

}

Chat.View.prototype.parseCommand = function(message) {

    try {

        var parts = message.split(" ");

        if (parts[0] == ".help") {

            this.controls.output.append("Available commands:");
            this.controls.output.append(".help - print help");
            this.controls.output.append(".login nickname password color - logging in to chat");
            this.controls.output.append(".logout - logging out from chat");

        }

        else if (parts[0] == ".login") {

            if (this.chat.user.logged == false) {

                if (parts.length == 3) {
                    this.chat.user.nickname = parts[1];
                    this.chat.user.password = parts[2];
                }
                else if (parts.length == 4) {
                    this.chat.user.nickname = parts[1];
                    this.chat.user.password = parts[2];
                    this.chat.user.color = parts[3];
                }
                else {
                    throw("Wrong login command arguments number.");
                }

                this.chat.element.trigger(me.chat.events.LOGIN_ATTEMPT);

            }
            else {
                throw("You must logout before login.");
            }

        }

        else if (parts[0] == ".logout") {

            if (this.chat.user.logged == true) {
                this.chat.element.trigger(me.chat.events.LOGOUT_ATTEMPT);
            }
            else {
                throw("You must login before logout.");
            }

        }

    }
    catch (exception) {
        this.controls.output.append(exception);
    }

}

Chat.View.prototype.parseSendMessage = function(message) {

    var sendMessage = new Chat.SendMessage();

    if (message.search("@") == 0) {
        sendMessage.senderNickname = this.user.nickname;
        sendMessage.receiverNickname = message.split(" ")[0];
        sendMessage.text = message.substring(this.sendMessage.receiverNickname.length + 1);
    }
    else {
        sendMessage.senderNickname = this.user.nickname;
        sendMessage.receiverNickname = "";
        sendMessage.text = message;
    }

    this.chat.element.trigger(me.chat.events.SEND_MESSAGE_ATTEMPT, sendMessage);

}

