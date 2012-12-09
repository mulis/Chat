Chat = function(elementId, serviceUrl) {

    this.element = $("#" + elementId);
    this.serviceUrl = serviceUrl;
    this.events = {
        VIEW_TEMPLATE_LOAD : "view-controls-ready",
        INPUT_MADE : "input-made",
        LOGIN_ATTEMPT : "login-try",
        LOGIN_SUCCESS : "login-success",
        LOGOUT_ATTEMPT : "logout-try",
        LOGOUT_SUCCESS : "logout-success",
        SEND_MESSAGE_ATTEMPT : "post-message-ready",
        SEND_MESSAGE_SUCCESS : "post-message-success",
        GET_MESSAGES_SUCCESS : "get-messages-success",
        RECEIVE_MESSAGES_READY : "receive-messages-ready",
        SERVICE_ERROR : "service-error"
    };

    this.model = new Chat.Model(this);
    this.view = new Chat.View(this);
    this.controller = new Chat.Controller(this);

    this.user = new Chat.User();

}

Chat.User = function() {

    this.nickname = "";
    this.password = "";
    this.color = "";
    this.logged = false;

}

Chat.SendMessage = function() {

    this.senderNickname = "";
    this.receiverNickname = "";
    this.text = "";

}
