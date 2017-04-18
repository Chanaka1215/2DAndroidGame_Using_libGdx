package com.chanaka.fluppy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import java.util.Random;

public class Fluppy extends ApplicationAdapter {
	SpriteBatch batch;
	Texture bg;
	Texture[] birds;
	Texture topTube;
	Texture bottomTube;

	int flapState =0;
	float birdY = 0;
	float velocity =0;

	int gameState;
	float gravity =2;
	float gap =300;
	float maxTubeOffSet;
	Random randomGenerator;
	float tubeOffset;



	@Override
	public void create () {
		batch = new SpriteBatch();
		bg =new Texture("bg.png");
		birds =new Texture[2];
		birds[0] = new Texture("bird1.png");
		birds[1] = new Texture("bird3.png");
		birdY = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;


		topTube =new Texture("up.png");
		bottomTube = new Texture("down1.png");
		maxTubeOffSet = Gdx.graphics.getHeight()/2- gap/2 - 100;
		randomGenerator = new Random();


	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		if(gameState != 0) {

			if(Gdx.input.justTouched()){
				velocity = -20;
				tubeOffset =(randomGenerator.nextFloat() -0.5f)*(Gdx.graphics.getHeight() - gap - 200);

			}

			batch.draw(topTube,Gdx.graphics.getWidth()/2 -topTube.getWidth()/2,Gdx.graphics.getHeight()/2 +gap/2 +tubeOffset);
			batch.draw(bottomTube,Gdx.graphics.getWidth()/2 - bottomTube.getWidth()/2,Gdx.graphics.getHeight()/2 - gap/2 -bottomTube.getHeight()+tubeOffset);

			if(birdY > 0 || velocity <0) {
				velocity = velocity + gravity;
				birdY -= velocity;
			}
		}else {
			if(Gdx.input.justTouched()){
				gameState =1;
			}

		}

		if (flapState == 0) {
			flapState = 1;
		} else {
			flapState = 0;
		}


		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
