Chat.Controller = function(chat) {

    var me = this;

    this.chat = chat;

    $(this.chat.element).bind(
        chat.events.LOGIN_ATTEMPT,
        function() {
            me.login(new Chat.Controller.RequestDataObjects.LoginRequestData(me.chat.user));
        }
    )

    $(this.chat.element).bind(
        chat.events.LOGOUT_ATTEMPT,
        function() {
            me.logout(new Chat.Controller.RequestDataObjects.LogoutRequestData(me.chat.user));
        }
    )

    $(this.chat.element).bind(
        chat.events.POST_MESSAGE_ATTEMPT,
        function(event, message) {
            me.postMessage(new Chat.Controller.RequestDataObjects.PostMessageRequestData(me.chat.user, message));
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
        me.chat.user.logged = true;
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
        me.chat.user.logged = false;
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

Chat.Controller.prototype.getMessages = function(requestData) {

    var me = this;

    $.ajax({
        "type" : "GET",
        "url" : me.chat.serviceUrl + "/get/messages",
        "data" : JSON.stringify(requestData),
        "contentType" : "application/json",
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

Chat.Controller.RequestDataObjects = function() {}

Chat.Controller.RequestDataObjects.LoginRequestData = function(user) {
    return {
        nickname : user.nickname,
        password : user.password
    };
}

Chat.Controller.RequestDataObjects.LogoutRequestData = function(user) {
    return {
        nickname : user.nickname,
        password : user.password
    };
}

Chat.Controller.RequestDataObjects.PostMessageRequestData = function(user, message) {
    return {
        message : message,
        password : user.password,
    };
}

