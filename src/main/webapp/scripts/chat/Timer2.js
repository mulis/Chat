Chat.Timer = function(chat, expression, context) {

    var me = this;

    this.chat = chat;

    this.minInterval = 1000;
    this.maxInterval = 8000;
    this.interval = this.minInterval;

    this.expression = expression;
    this.context = context;

    this.identifier = null;

}

Chat.Timer.prototype.start = function() {

    var me = this;

    this.identifier = setInterval(
        function () {
            me.expression.call(me.context);
        },
        this.interval
    );

}

Chat.Timer.prototype.stop = function() {

    clearInterval(this.identifier);

}

 Chat.Timer.prototype.restart = function() {

     this.stop();
     this.start();

 }

Chat.Timer.prototype.increaseInterval = function() {

    var nextInterval = this.interval * 2;
    this.interval = (nextInterval > this.maxInterval) ? this.maxInterval : nextInterval;
    this.restart();

}

Chat.Timer.prototype.increaseIntervalToMax = function() {

     this.interval = this.maxInterval;
     this.restart();

}

Chat.Timer.prototype.decreaseInterval = function() {

    var nextInterval = this.interval / 2;
    this.interval = (nextInterval < this.minInterval) ? this.minInterval : nextInterval;
    this.restart();

}

Chat.Timer.prototype.decreaseIntervalToMin = function() {

    this.interval = this.minInterval;
    this.restart();

}
