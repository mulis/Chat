Chat.Controller = function(chat) {

    var me = this;

    this.chat = chat;

    $(this.chat.element).bind(
        chat.events.LOGIN_ATTEMPT,
        function() {
            me.login(me.chat.user);
        }
    )

    $(this.chat.element).bind(
        chat.events.POST_MESSAGE_READY,
        function() {
            me.postMessage(me.chat.model.getPostMessage());
        }
    )

    $(this.chat.element).bind(
        chat.events.SEND_MESSAGE_ATTEMPT,
        function(event, message) {
            me.postMessage(message);
        }
    )

}

Chat.Controller.prototype.login = function(requestData) {

    var me = this;

    $.ajax({
        "type" : "POST",
        "url" : me.chat.serviceUrl + "/login",
        "data" : JSON.stringify(requestData),
        "contentType" : "application/json",
        "processData" : false
    })
    .success(function(data) {
        me.chat.element.trigger(me.chat.events.LOGIN_SUCCESS);
     })
    .error(function(data) {
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

}

Chat.Controller.prototype.logout = function(requestData) {

    var me = this;

    $.ajax({
        "type" : "POST",
        "url" : me.chat.serviceUrl + "/logout",
        "data" : JSON.stringify(requestData),
        "contentType" : "application/json",
        "processData" : false
    })
    .success(function(data) {
        me.chat.element.trigger(me.chat.events.LOGOUT_SUCCESS);
     })
    .error(function(data) {
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

}

Chat.Controller.prototype.postMessage = function(requestData) {

    var me = this;

    $.ajax({
        "type" : "POST",
        "url" : me.chat.serviceUrl + "/post/message",
        "data" : JSON.stringify(requestData),
        "contentType" : "application/json",
        "processData" : false
    })
    .success(function(data) {
        me.chat.element.trigger(me.chat.events.POST_MESSAGE_SUCCESS);
     })
    .error(function(data) {
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

}

Chat.Controller.prototype.getMessages = function(nickname, index) {

    var me = this;

    $.post(
        me.chat.serviceUrl + "/get/messages",
        {"nickname" : nickname, "index" : index}
    )
    .success(function(data) {
        me.chat.model.addNewMessages(data);
        me.chat.element.trigger(me.chat.events.GET_MESSAGES_SUCCESS);
     })
    .error(function(data) {
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

}
