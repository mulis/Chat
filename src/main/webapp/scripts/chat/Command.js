Chat.Command = [
{

    name : "login",
    arguments : "nickname password color",
    description : "logging in to chat",
    run : function() {

        try {

            if (this.user.logged == false) {

                if (arguments.length == 2) {
                    this.user.nickname = arguments[0];
                    this.user.password = arguments[1];
                } else if (arguments.length == 3) {
                    this.user.nickname = arguments[0];
                    this.user.password = arguments[1];
                    this.user.color = arguments[2];
                } else {
                    throw("Wrong login command arguments number.");
                }

                this.element.trigger(this.events.LOGIN_ATTEMPT);

            } else {
                throw("You must logout before login.");
            }

        } catch (exception) {
            this.view.printMessage(exception);
        }

    }

},

{

    name : "logout",
    arguments : "",
    description : "logging out from chat",
    run : function() {

        try {

            if (this.user.logged == true) {
                this.element.trigger(this.events.LOGOUT_ATTEMPT);
            }
            else {
                throw("You must login before logout.");
            }

        } catch (exception) {
            this.view.printMessage(exception);
        }

   }

},

{

    name : "clear",
    arguments : "",
    description : "clear output",
    run : function() {

        this.view.clearMessagesOutput();

    }

},

{

    name : "help",
    arguments : "",
    description : "print help",
    run : function() {

        this.view.printMessage("Available commands:");
        for (var i = 0; i < Chat.Command.length; i++) {
            this.view.printMessage("." + Chat.Command[i].name + " " + Chat.Command[i].arguments + " - " + Chat.Command[i].description);
        }

   }

}]

Chat.Command.wire = function(chat) {
    for (var i = 0; i < Chat.Command.length; i++) {
        chat[Chat.Command[i].name] = Chat.Command[i].run;
    }
}
