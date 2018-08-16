var actionList = [];
var frameNumR = 0;
var frameNumL = 0;
var frameNumU = 0;
var frameNumD = 0;

var frameNum = 0;
/* game namespace */


var moveRight = function () {
    actionList.push(2);
};

var moveLeft = function () {
    actionList.push(1);
};

var moveUp = function () {
    actionList.push(3);
};

var moveDown = function () {
    actionList.push(4);
};

var pickUpCoin = function () {
    actionList.push(5)
};


var game = {
    /**
     * an object where to store game global data
     */
    data: {
        score: 0,
        bump: 0
    },



    // Run on page load.
    onload: function () {
        // Initialize the video.
        if (!me.video.init(430, 300, { wrapper: "screen", scale: "auto", scaleMethod: "flex-width" })) {
            alert("Your browser does not support HTML5 canvas.");
            return;
        }

        // add "#debug" to the URL to enable the debug Panel
        if (me.game.HASH.debug === true) {
            window.onReady(function () {
                me.plugin.register.defer(this, me.debug.Panel, "debug", me.input.KEY.V);
            });
        }

        // Initialize the audio.
        me.audio.init("mp3,ogg");

        // Set a callback to run when loading is complete.
        me.loader.onload = this.loaded.bind(this);

        // Load the resources.
        me.loader.preload(game.resources);

        // Initialize melonJS and display a loading screen.
        me.state.change(me.state.LOADING);
    },

    // Run on game resources loaded.
    loaded: function () {
        // set the "Play/Ingame" Screen Object
        me.state.set(me.state.PLAY, new game.PlayScreen());

        // register our player entity in the object pool
        me.pool.register("mainPlayer", game.PlayerEntity);
        me.pool.register("CoinEntity", game.CoinEntity);

        me.state.change(me.state.PLAY);
    },


};
