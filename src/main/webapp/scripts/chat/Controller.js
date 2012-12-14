Chat.Controller = function(chat) {

    var me = this;

    this.chat = chat;

    $(this.chat.element).bind(
        "click",
        function(aEvent) {
            if (me.chat.user.logged) {
                me.chat.messagesTimer.decreaseIntervalToMin();
            }
        }
    );

    $(this.chat.element).bind(
        chat.events.LOGIN_ATTEMPT,
        function() {
            me.login(me.chat.user);
        }
    )

    $(this.chat.element).bind(
        chat.events.LOGIN_SUCCESS,
        function() {
            me.chat.usersTimer.start();
            me.chat.messagesTimer.start();
        }
    )

    $(this.chat.element).bind(
        chat.events.LOGOUT_ATTEMPT,
        function() {
            me.logout(me.chat.user);
        }
    )

    $(this.chat.element).bind(
        chat.events.LOGOUT_SUCCESS,
        function() {
            me.chat.messagesTimer.stop();
            me.chat.usersTimer.stop();
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

    $(this.chat.element).bind(
        chat.events.GET_USERS_ATTEMPT,
        function(event, message) {
            me.getUsers(me.chat.user);
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
            password : user.password,
            color : user.color
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
        if (result.status) {
            me.chat.view.printStatus(result.status);
        }
     })
    .error(function(data) {
        me.chat.messagesTimer.increaseInterval();
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
            me.chat.messagesTimer.stop();
            me.chat.element.trigger(me.chat.events.LOGOUT_SUCCESS);
        }
        else {
            me.chat.element.trigger(me.chat.events.LOGOUT_FAILURE);
        }
        if (result.status) {
            me.chat.view.printStatus(result.status);
        }
     })
    .error(function(data) {
        me.chat.messagesTimer.increaseInterval();
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
        if (result.status) {
            me.chat.view.printStatus(result.status);
        }
     })
    .error(function(data) {
        me.chat.messagesTimer.increaseInterval();
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

    me.chat.messagesTimer.decreaseIntervalToMin();

}

Chat.Controller.prototype.getMessages = function(user) {

    var me = this;

    $.ajax({
        "type" : "POST",
        "url" : me.chat.serviceUrl + "/get/messages/new",
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
                me.chat.messagesTimer.decreaseIntervalToMin();
            }
            else {
                me.chat.messagesTimer.increaseInterval();
            }
            me.chat.element.trigger(me.chat.events.GET_MESSAGES_SUCCESS);
        }
        else {
            me.chat.messagesTimer.increaseInterval();
            me.chat.element.trigger(me.chat.events.GET_MESSAGES_FAILURE);
        }
        if (result.status) {
            me.chat.view.printStatus(result.status);
        }
     })
    .error(function(data) {
        me.chat.messagesTimer.increaseInterval();
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

}

Chat.Controller.prototype.getUsers = function(user) {

    var me = this;

    $.ajax({
        "type" : "POST",
        "url" : me.chat.serviceUrl + "/get/users",
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
            if (result.users.length > 0) {
                me.chat.model.setUsers(result.users);
                me.chat.usersTimer.decreaseIntervalToMin();
            }
            else {
                me.chat.usersTimer.increaseInterval();
            }
            me.chat.element.trigger(me.chat.events.GET_USERS_SUCCESS);
        }
        else {
            me.chat.usersTimer.increaseInterval();
            me.chat.element.trigger(me.chat.events.GET_USERS_FAILURE);
        }
        if (result.status) {
            me.chat.view.printStatus(result.status);
        }
     })
    .error(function(data) {
        me.chat.usersTimer.increaseInterval();
        me.chat.element.trigger(me.chat.events.SERVICE_ERROR);
    })
    .complete(function(data) {
    })

}

