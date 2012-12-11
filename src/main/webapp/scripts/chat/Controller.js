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
        chat.events.LOGOUT_ATTEMPT,
        function() {
            me.logout(me.chat.user);
        }
    )

    $(this.chat.element).bind(
        chat.events.POST_MESSAGE_ATTEMPT,
        function(event, message) {
            me.postMessage(me.chat.user, message);
        }
    )

    $(this.chat.element).bind(
        chat.events.GET_MESSAGES_ATTEMPT,
        function(event, message) {
            me.getMessages(me.chat.user);
        }
    )

}

Chat.Controller.prototype.login = function(user) {

    var me = this;

    $.ajax({
        "type" : "POST",
        "url" : me.chat.serviceUrl + "/login",
        "data" : JSON.stringify({
            nickname : user.nickname,
            password : user.password
        }),
        "contentType" : "application/json",
        "dataType" : "text",
        "processData" : false
    })
    .success(function(data) {
        me.chat.user.logged = true;
        me.chat.element.trigger(me.chat.events.LOGIN_SUCCESS);
     })
    .error(function(data) {
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

}

Chat.Controller.prototype.logout = function(user) {

    var me = this;

    $.ajax({
        "type" : "POST",
        "url" : me.chat.serviceUrl + "/logout",
        "data" : JSON.stringify({
            nickname : user.nickname,
            password : user.password
        }),
        "contentType" : "application/json",
        "dataType" : "text",
        "processData" : false
    })
    .success(function(data) {
        me.chat.user.logged = false;
        me.chat.element.trigger(me.chat.events.LOGOUT_SUCCESS);
     })
    .error(function(data) {
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

}

Chat.Controller.prototype.postMessage = function(user, message) {

    var me = this;

    $.ajax({
        "type" : "POST",
        "url" : me.chat.serviceUrl + "/post/message",
        "data" : JSON.stringify({
            message : message,
            password : user.password,
        }),
        "contentType" : "application/json",
        "dataType" : "text",
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

Chat.Controller.prototype.getMessages = function(user) {

    var me = this;

    $.ajax({
        "type" : "GET",
        "url" : me.chat.serviceUrl + "/get/messages",
        "data" : JSON.stringify({
            nickname : user.nickname,
            password : user.password,
            lastMessageIndex : user.lastMessageIndex
        }),
        "contentType" : "application/json",
        "dataType" : "json",
        "processData" : false
    }).success(function(data) {
        me.chat.model.addNewMessages(data);
        me.chat.element.trigger(me.chat.events.GET_MESSAGES_SUCCESS);
     })
    .error(function(data) {
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

}

