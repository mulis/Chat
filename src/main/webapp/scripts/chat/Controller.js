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
        chat.events.LOGIN_SUCCESS,
        function() {
            me.chat.timer.start();
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
        var result = JSON.parse(data);
        if (result.code < 20) {
            me.chat.user.logged = true;
            me.chat.element.trigger(me.chat.events.LOGIN_SUCCESS);
        }
        else {
            me.chat.element.trigger(me.chat.events.LOGIN_FAILURE);
        }
     })
    .error(function(data) {
        me.chat.timer.increaseInterval();
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
        var result = JSON.parse(data);
        if (result.code < 20) {
            me.chat.user.logged = false;
            me.chat.timer.stop();
            me.chat.element.trigger(me.chat.events.LOGOUT_SUCCESS);
        }
        else {
            me.chat.element.trigger(me.chat.events.LOGOUT_FAILURE);
        }
     })
    .error(function(data) {
        me.chat.timer.increaseInterval();
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
        var result = JSON.parse(data);
        if (result.code < 20) {
            me.chat.element.trigger(me.chat.events.POST_MESSAGE_SUCCESS);
        }
        else {
            me.chat.element.trigger(me.chat.events.POST_MESSAGE_FAILURE);
        }
     })
    .error(function(data) {
        me.chat.timer.increaseInterval();
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

    me.chat.timer.setMinInterval();

}

Chat.Controller.prototype.getMessages = function(user) {

    var me = this;

    $.ajax({
        "type" : "POST",
        "url" : me.chat.serviceUrl + "/get/messages",
        "data" : JSON.stringify({
            nickname : user.nickname,
            password : user.password
        }),
        "contentType" : "application/json",
        "dataType" : "text",
        "processData" : false
    }).success(function(data) {
        var result = JSON.parse(data);
        if (result.code < 20) {
            if (result.messages.length > 0) {
                me.chat.model.addNewMessages(result.messages);
                me.chat.timer.setMinInterval();
            }
            else {
                me.chat.timer.increaseInterval();
            }
            me.chat.element.trigger(me.chat.events.GET_MESSAGES_SUCCESS);
        }
        else {
            me.chat.timer.increaseInterval();
            me.chat.element.trigger(me.chat.events.GET_MESSAGE_FAILURE);
        }
     })
    .error(function(data) {
        me.chat.timer.increaseInterval();
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

}

