/**
* Player Entity
*/

var actionNum = 0;
//var topickUp;
//var pickUpReady = false;


game.CoinEntity = me.CollectableEntity.extend({
  // extending the init function is not mandatory
  // unless you need to add some extra initialization
  init: function (x, y, settings) {
    // call the parent constructor
    this._super(me.CollectableEntity, 'init', [x, y , settings]);

  },

  // this function is called by the engine, when
  // an object is touched by something (here collected)
  onCollision : function (response, other) {
    // do something when collected

    // make sure it cannot be collected "again"
    this.body.setCollisionMask(me.collision.types.NO_OBJECT);
    game.data.score += 1;
    // remove it
    me.game.world.removeChild(this);
    JsHandler.successModal(3);
    JsHandler.giveStars(3);

    return false
  }
});

game.PlayerEntity = me.Entity.extend({

  /**
   * constructor
   */
  init:function (x, y, settings) {
      // call the constructor
      this._super(me.Entity, 'init', [x, y, settings]);

      this.body.setVelocity(2, 2);

      me.game.viewport.follow(this.pos, me.game.viewport.AXIS.BOTH, 0.1);

      this.alwaysUpdate = true;

      this.renderable.addAnimation("walk_right", [3, 7, 11, 15]);
      this.renderable.addAnimation("walk_left", [1, 5, 9, 13]);
      this.renderable.addAnimation("walk_up", [2, 6, 10, 14]);
      this.renderable.addAnimation("walk_down", [0, 4, 8, 12]);

      this.renderable.addAnimation("stand_r", [3]);
      this.renderable.addAnimation("stand_l", [1]);
      this.renderable.addAnimation("stand_u", [2]);
      this.renderable.addAnimation("stand_d", [0]);

      this.renderable.setCurrentAnimation("stand_r");

      //this.anchorPoint.set(-0, 0);



  },

  /**
   * update the entity
   */
  update: function (dt) {

      if(game.data.bump != game.data.score){
                  actionList = [];
                  actionNum = 0;
                  game.data.score = 0;
                  game.data.bump = 0;
                  game.onload();
              }

      if (actionNum == 0 && actionList.length != 0){
          actionNum = actionList[0];

      }



      if (game.data.score == 10){
          console.log(game.data.score);
          game.data.score = 0;
      }


      if (actionNum == 1) {
          // flip the sprite on horizontal axis

          // update the entity velocity
          this.body.vel.x -= this.body.accel.x * me.timer.tick;

          // change to the walking animation
          if (!this.renderable.isCurrentAnimation("walk_left")) {
              this.renderable.setCurrentAnimation("walk_left");
          }

          frameNumL += 1;

          if(frameNumL == 17) {
              actionNum = 0;
              actionList.shift();
              frameNumL = 0;
              this.renderable.setCurrentAnimation("stand_l");
              this.body.vel.x = 0;
          }


      }
      else if (actionNum == 2) {

          // update the entity velocity
          this.body.vel.x += this.body.accel.x * me.timer.tick;

          // change to the walking animation
          if (!this.renderable.isCurrentAnimation("walk_right")) {
              this.renderable.setCurrentAnimation("walk_right");
          }

          frameNumR += 1;

          if(frameNumR == 17){
              actionNum = 0;
              actionList.shift();
              frameNumR = 0;
              this.renderable.setCurrentAnimation("stand_r");
              this.body.vel.x = 0;
          }
      }

      else if (actionNum == 3) {


          // make sure we are not already jumping or falling
          this.body.vel.y -= this.body.accel.y * me.timer.tick;

          // change to the walking animation
          if (!this.renderable.isCurrentAnimation("walk_up")) {
              this.renderable.setCurrentAnimation("walk_up");
          }

          frameNumU += 1;

          if(frameNumU == 17){
              actionNum = 0;
              actionList.shift();
              frameNumU = 0;
              this.renderable.setCurrentAnimation("stand_u");
              this.body.vel.y = 0;
          }


      }

      else if (actionNum == 4) {

          // make sure we are not already jumping or falling
          this.body.vel.y += this.body.accel.y * me.timer.tick;

          // change to the walking animation
          if (!this.renderable.isCurrentAnimation("walk_down")) {
              this.renderable.setCurrentAnimation("walk_down");
          }

          frameNumD += 1;

          if(frameNumD == 17){
              actionNum = 0;
              actionList.shift();
              frameNumD = 0;
              this.renderable.setCurrentAnimation("stand_d");
              this.body.vel.y = 0;
          }


      }

      else {
          this.body.vel.x = 0;
          this.body.vel.y = 0;

          // change to the standing animation
          this.renderable.setCurrentAnimation("stand_d");
      }

      // apply physics to the body (this moves the entity)
      this.body.update(dt);

      // handle collisions against other shapes
      me.collision.check(this);

      // return true if we moved or if the renderable was updated
      return (this._super(me.Entity, 'update', [dt]) || this.body.vel.x !== 0 || this.body.vel.y !== 0);
  },

  /**
   * colision handler
   * (called when colliding with other objects)
   */
  onCollision: function (response, other) {
      // Make all other objects solid
      game.data.bump += 1;
      return true;
  }



});
