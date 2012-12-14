Chat = function(elementId, serviceUrl) {

    this.element = $("#" + elementId);
    this.serviceUrl = serviceUrl;
    this.events = {
        VIEW_TEMPLATE_LOAD : "view-template-load",
        INPUT_READY : "input-ready",
        LOGIN_ATTEMPT : "login-attempt",
        LOGIN_SUCCESS : "login-success",
        LOGIN_FAILURE : "login-failure",
        LOGOUT_ATTEMPT : "logout-attempt",
        LOGOUT_SUCCESS : "logout-success",
        LOGOUT_FAILURE : "logout-failure",
        POST_MESSAGE_ATTEMPT : "post-message-attempt",
        POST_MESSAGE_SUCCESS : "post-message-success",
        POST_MESSAGE_FAILURE : "post-message-failure",
        GET_MESSAGES_ATTEMPT : "get-messages-attempt",
        GET_MESSAGES_SUCCESS : "get-messages-success",
        GET_MESSAGES_FAILURE : "get-messages-failure",
        GET_USERS_ATTEMPT : "get-users-attempt",
        GET_USERS_SUCCESS : "get-users-success",
        GET_USERS_FAILURE : "get-users-failure",
        SERVICE_ERROR : "service-error"
    };

    this.user = new Chat.User();

    this.model = new Chat.Model(this);
    this.view = new Chat.View(this);
    this.controller = new Chat.Controller(this);

    this.messagesTimer = new Chat.Timer(
        this,
        function() {
            this.element.trigger(this.events.GET_MESSAGES_ATTEMPT);
        },
        this
    );

    this.usersTimer = new Chat.Timer(
        this,
        function() {
            this.element.trigger(this.events.GET_USERS_ATTEMPT);
        },
        this
    );

    this.usersTimer.minInterval = 3000;
    this.usersTimer.maxInterval = 3000;

    Chat.Command.wire(this);

}

Chat.User = function() {

    this.nickname = "";
    this.password = "";
    this.color = "";
    this.logged = false;
    this.lastMessageIndex = 0;

}

Chat.Message = function() {

    this.senderNickname = "";
    this.receiverNickname = "";
    this.text = "";

}
