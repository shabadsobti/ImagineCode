// Create the canvas
var canvas = document.createElement("canvas");
var ctx = canvas.getContext("2d");
canvas.width = 350;
canvas.height = 250;
document.body.appendChild(canvas);


// Background image
var bgReady = false;
var bgImage = new Image();
bgImage.onload = function () {
	bgReady = true;
};
bgImage.src = "images/background.png";

// Background-top image
var bgTopReady = false;
var bgTopImage = new Image();
bgTopImage.onload = function () {
	bgTopReady = true;
};
bgTopImage.src = "images/background-top.png";



// Hero image
var heroReady = false;
var heroImage = new Image();
heroImage.onload = function () {
	heroReady = true;
};
heroImage.src = "images/hero.png";

// Monster image
var monsterReady = false;
var monsterImage = new Image();
monsterImage.onload = function () {
	monsterReady = true;
};
monsterImage.src = "images/monster.png";

// Background-bottom image
var bgBottomReady = false;
var bgBottomImage = new Image();
bgBottomImage.onload = function () {
	bgBottomReady = true;
};
bgBottomImage.src = "images/background-top.png";



// Game objects
var hero = {
	speed: 256, // movement in pixels per second
	x: 0,
	y: 0
};
var monster = {
	x: 0,
	y: 0
};
var monstersCaught = 0;



// Reset the game when the player catches a monster
var reset = function () {
	hero.x = canvas.width / 2;
	hero.y = canvas.height / 2;

	monster.x = 296;
	monster.y = 240;
};

function moveRight(){
	hero.x += 30;
}



var down = function(){

	hero.y += 30;

}

function showAndroidDialog(stars) {
            JsHandler.repeatLessonModal(stars);
}

function giveStars(stars) {
            JsHandler.giveStars(stars);
}

function showNextLessonModal(stars) {
            JsHandler.successModal(stars);
}



var up = function(){

	hero.y -= 30;

}

var left = function(){
		hero.x -= 30;
}


// Update game objects
var update = function () {


//
// if (hero.y <= 220){
// 	reset();
// 	alert("You died")
// }
//
// if (hero.y >= 260){
// 	reset();
// 	alert("You died")
// }
//
if (hero.x >= monster.x){
		++monstersCaught;


		reset();
		giveStars(monstersCaught);

		if (monstersCaught >=3){
            showNextLessonModal(monstersCaught);

		}
		else{
		showAndroidDialog(monstersCaught);
		}
}
	// Are they touching?
	if (
		hero.x <= (monster.x + 32)
		&& monster.x <= (hero.x + 32)
		&& hero.y <= (monster.y + 32)
		&& monster.y <= (hero.y + 32)
	) {
		++monstersCaught;
		reset();
	}
};



// Draw everything
var render = function () {

	if (bgReady) {
		ctx.drawImage(bgImage, 0, 0);
	}

	if (bgTopReady) {
		ctx.drawImage(bgTopImage, 0, 0);

	}




	if (heroReady) {
		ctx.drawImage(heroImage, hero.x , hero.y );
	}

	if (bgBottomReady) {
		ctx.drawImage(bgBottomImage, 0, 270);

	}

	if (monsterReady) {
		ctx.drawImage(monsterImage, 295, canvas.height / 2 - 20);
	}

	// Score
	ctx.fillStyle = "rgb(250, 250, 250)";
	ctx.font = "15px Helvetica";
	ctx.textAlign = "left";
	ctx.textBaseline = "top";
	ctx.fillText("Goblins caught: " + monstersCaught, 32, 32);
};

// The main game loop
var main = function () {


	update();
	render();



	// Request to do this again ASAP
	requestAnimationFrame(main);
};

// Cross-browser support for requestAnimationFrame
var w = window;
requestAnimationFrame = w.requestAnimationFrame || w.webkitRequestAnimationFrame || w.msRequestAnimationFrame || w.mozRequestAnimationFrame;

// Let's play this game!

reset();
main();
