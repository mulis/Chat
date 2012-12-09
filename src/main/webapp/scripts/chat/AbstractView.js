Chat.AbstractView = function() {
}

Chat.AbstractView.prototype.loadTemplate = function(name) {

    var me = this;

    for (var i = 0; i < document.scripts.length; i++) {
        var scriptSrc = document.scripts.item(i).src;
        if (scriptSrc.indexOf(name) > -1) {
            name = scriptSrc.substring(0, scriptSrc.lastIndexOf(".js")) + ".tmpl";
        }
    }

    $.get(
        name
    )
    .success(function(data) {
        me.chat.element.html(data);
        me.chat.element.trigger(me.chat.events.VIEW_TEMPLATE_LOAD);
     })
    .error(function(data) {
        me.chat.element.text("Chat view template file " + name + ".tmpl loading error.");
    })
    .complete(function(data) {
    })

}
