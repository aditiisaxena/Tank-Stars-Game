package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CustomTankStars extends Game {
	SpriteBatch batch;
	public BitmapFont font;
	public Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		music = Gdx.audio.newMusic(Gdx.files.internal("menu.mp3"));
		music.setLooping(true);
		music.play();
		this.setScreen(new StartScreen(this, music));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
