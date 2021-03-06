package com.chanaka.fluppy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


import java.util.Random;



public class Fluppy extends ApplicationAdapter {
	SpriteBatch batch;
	Texture bg;
	Texture[] birds;
	Texture topTube;
	Texture bottomTube;
	Texture gameOver;
	Circle birdCover;
	Rectangle[] topPipeCover;
	Rectangle[] bottomPipeCover;
	ShapeRenderer shapeRenderer;
	BitmapFont text;
	Texture reset;

	int flapState  =0;
	float birdY    =0;
	float velocity =0;

	int gameState;
	float gravity =0.5f;
	float gap =250;
	float maxTubeOffSet;
	Random randomGenerator;

	float tubeVelocity =1;
	int numberOfTubes = 3;
	float[] tubeX= new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];


	float distanceBetweenTube;
	int score=0;
	int scoringTobe =0;



	@Override
	public void create () {
		batch = new SpriteBatch();
		bg =new Texture("bg.png");
		birds =new Texture[2];
		birds[0] = new Texture("bird1.png");
		birds[1] = new Texture("bird3.png");
		gameOver = new Texture("gameOver.png");
		reset =new Texture("reset.png") ;


		birdCover =new Circle();
		shapeRenderer = new ShapeRenderer();
		bottomPipeCover = new Rectangle[numberOfTubes];
		topPipeCover = new Rectangle[numberOfTubes];
		text = new BitmapFont();
		text.setColor(Color.BLACK);
		text.getData().setScale(5);



		topTube =new Texture("up.png");
		bottomTube = new Texture("down1.png");
		maxTubeOffSet = Gdx.graphics.getHeight()/2- gap/2 - 100;
		randomGenerator = new Random();
		distanceBetweenTube =Gdx.graphics.getWidth();

		beginGame();

	}


	public  void beginGame(){


		birdY = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;
		for(int i =0;i<numberOfTubes;i++){

			tubeOffset[i] =(randomGenerator.nextFloat() -0.5f)*(Gdx.graphics.getHeight() - gap - 200);
			tubeX[i] = Gdx.graphics.getWidth()/2 -topTube.getWidth()/2 +Gdx.graphics.getWidth()+i * distanceBetweenTube;
			topPipeCover[i] = new Rectangle();
			bottomPipeCover[i] = new Rectangle();
		}
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		if(gameState == 1) {

			if (tubeX[scoringTobe] < Gdx.graphics.getWidth()){
				score ++;
				Gdx.app.log("score",String.valueOf(score));

				if(scoringTobe < numberOfTubes - 1){
					scoringTobe++;
				}else{
					scoringTobe =0;
				}
			}

			if(Gdx.input.justTouched()){
				velocity = -10;


			}


			for(int i =0;i<numberOfTubes;i++) {
				if(tubeX[i] < - topTube.getWidth()){
					tubeX[i] += numberOfTubes * distanceBetweenTube;
					tubeOffset[i] =(randomGenerator.nextFloat() -0.5f)*(Gdx.graphics.getHeight() - gap - 200);
				}else{
					tubeX[i] = tubeX[i] - tubeVelocity;


				}

				tubeX[i] = tubeX[i] - tubeVelocity;

				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);
				topPipeCover[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],topTube.getWidth(),topTube.getHeight());
				bottomPipeCover[i] = new Rectangle(tubeX[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],bottomTube.getWidth(),bottomTube.getHeight());
			}

			if(birdY > 0) {
				velocity = velocity + gravity;
				birdY -= velocity;
			}else {
				gameState = 2;
			}
		}else if(gameState ==0){
			if(Gdx.input.justTouched()){
				gameState =1;
			}

		} else if(gameState == 2){
			Rectangle textureBounds=new Rectangle(Gdx.graphics.getWidth()/2 -reset.getWidth()/2,Gdx.graphics.getHeight()/2 +140,reset.getWidth(),reset.getHeight());


			batch.draw(gameOver,Gdx.graphics.getWidth()/2 - gameOver.getWidth()/2,Gdx.graphics.getHeight()/2 -gameOver.getHeight()/2);
			batch.draw(reset,Gdx.graphics.getWidth()/2 -reset.getWidth()/2,Gdx.graphics.getHeight()/2 -200);
			if(Gdx.input.isTouched()){
				Vector2 temp = new Vector2(Gdx.input.getX(), Gdx.input.getY());
				Gdx.app.log("hhhhh","hhhhh");


				    Gdx.app.log("hhhhh","44444");
					if(textureBounds.contains(temp.x,temp.y)){
						Gdx.app.log("done","Touched");
						gameState =1;
						beginGame();
						scoringTobe =0;
						score=0;
						velocity =0;

					}else {

						Gdx.app.log("aaa",String.valueOf(temp.x+" "+temp.y));
					}
			}
		}

		if (flapState == 0) {
			flapState = 1;
		} else {
			flapState = 0;
		}




		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);

		text.draw(batch,String.valueOf(score),50,100);

		birdCover.set(Gdx.graphics.getWidth() /2 ,birdY +birds[flapState].getHeight()/2,birds[flapState].getWidth() /2);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(birdCover.x,birdCover.y,birdCover.radius);
		for(int i =0;i<numberOfTubes;i++) {
			//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],topTube.getWidth(),topTube.getHeight());
			//shapeRenderer.rect(tubeX[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],bottomTube.getWidth(),bottomTube.getHeight());
			if(Intersector.overlaps(birdCover,topPipeCover[i]) || Intersector.overlaps(birdCover,bottomPipeCover[i])){
				gameState =2;
			}
		}

		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
