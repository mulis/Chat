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
        chat.events.LOGIN_SUCCESS,
        function(aEvent) {
            me.clearMessageInput();
        }
    );

    $(this.chat.element).bind(
        chat.events.LOGOUT_SUCCESS,
        function(aEvent) {
            me.clearMessageInput();
        }
    );

    $(this.chat.element).bind(
        chat.events.POST_MESSAGE_SUCCESS,
        function(aEvent) {
//            me.clearMessageInput();
        }
    );

    $(this.chat.element).bind(
        chat.events.GET_MESSAGES_SUCCESS,
        function(aEvent) {
            me.updateMessagesView();
        }
    );

    $(this.chat.element).bind(
        chat.events.GET_USERS_SUCCESS,
        function(aEvent) {
            me.updateUsersView();
        }
    );

    $(this.chat.element).bind(
        chat.events.SERVICE_ERROR,
        function(aEvent) {
            me.updateMessagesView();
        }
    );

}

Chat.View.prototype = new Chat.AbstractView();

Chat.View.prototype.initControls = function() {

    var me = this;

    this.controls = {};
    this.controls.messageInput = this.chat.element.find(".chat-message-input");
    this.controls.messagesOutput = this.chat.element.find(".chat-messages-output");
    this.controls.usersOutput = this.chat.element.find(".chat-users-output");
    this.controls.statusOutput = this.chat.element.find(".chat-status-output");

    this.controls.messageInput.bind(
        "keypress",
        function(aEvent) {
            var keycode = (aEvent.keyCode ? aEvent.keyCode : aEvent.which);
            if(keycode == "13"){
                me.parseInput(me.controls.messageInput.val());
                aEvent.preventDefault();
            }
        }
    );

}

Chat.View.prototype.clearMessageInput = function() {
    this.controls.messageInput.val("");
}


Chat.View.prototype.updateMessagesView = function() {
    var newMessages = this.chat.model.getNewMessages();
    for (var i = 0; i < newMessages.length; i++) {
        this.printMessage(this.parseOutputMessage(newMessages[i]));
    }
//    this.controls.messagesOutput.scrollTop(this.controls.messagesOutput[0].scrollHeight);
}

Chat.View.prototype.printMessage = function(message) {
    this.controls.messagesOutput.append("<div>" + message + "</div>");
}

Chat.View.prototype.clearMessagesOutput = function() {
    this.controls.messagesOutput.text("");
}

Chat.View.prototype.parseInput = function(input) {

    input = input.trim();

    if (input.search("\\.") == 0) {
        this.parseCommand(input);
    }
    else {
        this.parseSendMessage(input);
    }

}

Chat.View.prototype.parseCommand = function(input) {

    var parts = input.split(" ");
    var parsedCommand = parts.shift();
    var arguments = parts;

    for (var i = 0; i < Chat.Command.length; i++) {
        if ("." + Chat.Command[i].name == parsedCommand) {
            Chat.Command[i].run.apply(this.chat, parts);
        }
    }

}

Chat.View.prototype.parseSendMessage = function(input) {

    try {

        if (this.chat.user.logged == true) {

            var message = new Chat.Message();

            if (input.search("@") == 0) {
                message.senderNickname = this.chat.user.nickname;
                message.receiverNickname = input.split(" ")[0].substring(1);
                message.text = input.substring(message.receiverNickname.length + 1);
            }
            else {
                message.senderNickname = this.chat.user.nickname;
                message.receiverNickname = "";
                message.text = input;
            }

            this.chat.element.trigger(this.chat.events.POST_MESSAGE_ATTEMPT, message);

        }
        else {
            throw("You must login before post message.");
        }

    }
    catch (exception) {
        this.printMessage(exception);
    }

}

Chat.View.prototype.parseOutputMessage = function(envelope) {

    var output = "";

    if (envelope.date) {
        output += "[" + new Date(envelope.date).toLocaleTimeString() + "]";
    }

    if (envelope.message) {

        var users = this.chat.model.getUsersByNickname([envelope.message.senderNickname, envelope.message.receiverNickname]);

        if (envelope.message.senderNickname) {
            if (users[0]) {
                output += " " + this.colorizeUser(users[0]);
            }
            else {
                output += " " + envelope.message.senderNickname;
            }
        }

        if (envelope.message.receiverNickname) {
            if (users[1]) {
                output += " @" + this.colorizeUser(users[1]);
            }
            else {
                output += " " + envelope.message.receiverNickname;
            }
        }

        if (envelope.message.text) {
            output += ". " + this.parseOutputMessageText(envelope.message.text);
        }

    }

    return output;

}

Chat.View.prototype.parseOutputMessageText = function(text) {

    text = text.replace(/\*([^\*]+)\*/g, '<b>$1</b>');
    text = text.replace(/\_([^\_]+)\_/g, '<u>$1</u>');
    text = text.replace(/\-([^\-]+)\-/g, '<s>$1</s>');
    text = text.replace(/\[([^\|].+)\|(.+)\]/g, '<a href="$2">$1</a>');

    return text;

}

Chat.View.prototype.updateUsersView = function() {
    this.clearUsersOutput();
    var users = this.chat.model.getUsers();
    for (var i = 0; i < users.length; i++) {
        this.printUser(this.colorizeUser(users[i]));
    }
//    this.controls.usersOutput.scrollTop(this.controls.usersOutput[0].scrollHeight);
}

Chat.View.prototype.printUser = function(user) {
    this.controls.usersOutput.append("<div>" + user + "</div>");
}

Chat.View.prototype.clearUsersOutput = function() {
    this.controls.usersOutput.text("");
}

Chat.View.prototype.colorizeUser = function(user) {

    var output = '';
    var style = '';

    if (user) {

        if (user.color) {
            style += 'color:' + user.color + ';';
        }

        if (!user.logged) {
            style += 'font-style:italic;';
        }

        if (user.nickname) {
            output += '<span style="'+ style + '">' + user.nickname + '</span>';
        }

    }

    return output;

}

Chat.View.prototype.printStatus = function(status) {
    this.controls.statusOutput.html('<div>' + status + '</div>');
}
