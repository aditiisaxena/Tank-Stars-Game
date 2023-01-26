package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class SelectTank implements Screen {

    final CustomTankStars game;
    private final Texture blueTank;
    private final Rectangle Tank;
    private final Texture rightClick;
    private final Rectangle rClick;
    private final Texture leftClick;
    private final Rectangle lClick;
    private final Texture play;
    private final Rectangle playButton;
    private final TextureRegion blueWallTexture;
    private final TextureRegion blueCircleTexture;
    private final TextureRegion bannerTexture;
    private final Texture rab;
    private final Rectangle rabb;
    private final Texture lab;
    private final Rectangle labb;
    private final Texture pab;
    private final Rectangle pabb;
    private final TextureRegion backgroundTexture;
    private final OrthographicCamera camera;
    private final Sound click;
    final Music music;
    public SelectTank(final CustomTankStars game, final Music music) {
        this.game = game;
        this.music = music;
        rightClick = new Texture(Gdx.files.internal("right_click.png"));
        leftClick = new Texture(Gdx.files.internal("left_click.png"));
        blueTank = new Texture(Gdx.files.internal("blue_tank.png"));
        Texture blueCircle = new Texture(Gdx.files.internal("blue_circle.png"));
        blueCircleTexture = new TextureRegion(blueCircle, 0, 0, 1054, 1054);
        Texture backgroundImage = new Texture(Gdx.files.internal("tank_bg.png"));
        backgroundTexture = new TextureRegion(backgroundImage, 0, 0, 1200, 630);
        Texture banner = new Texture(Gdx.files.internal("banner.png"));
        bannerTexture = new TextureRegion(banner, 0, 0, 1224, 242);
        Texture blueWall = new Texture(Gdx.files.internal("blue_wall.png"));
        blueWallTexture = new TextureRegion(blueWall, 0, 0, 1712, 2690);
        play = new Texture(Gdx.files.internal("play.png"));
        click = Gdx.audio.newSound(Gdx.files.internal("click_sound.wav"));
        lab = new Texture(Gdx.files.internal("button_left_active.png"));
        rab = new Texture(Gdx.files.internal("button_right_active.png"));
        pab = new Texture(Gdx.files.internal("play_active.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        Tank = new Rectangle();
        Tank.x = 110;
        Tank.y = 120;
        Tank.width = 300;
        Tank.height = 300;

        rClick = new Rectangle();
        rClick.x = 700;
        rClick.y = 150;
        rClick.height = 50;
        rClick.width = 50;

        rabb = new Rectangle();
        rabb.x = 700;
        rabb.y = 150;
        rabb.height = 50;
        rabb.width = 50;

        lClick = new Rectangle();
        lClick.x = 550;
        lClick.y = 150;
        lClick.height = 50;
        lClick.width = 50;

        labb = new Rectangle();
        labb.x = 550;
        labb.y = 150;
        labb.height = 50;
        labb.width = 50;

        playButton = new Rectangle();
        playButton.x = 620;
        playButton.y = 30;
        playButton.height = 70;
        playButton.width = 70;

        pabb = new Rectangle();
        pabb.x = 620;
        pabb.y = 30;
        pabb.height = 70;
        pabb.width = 70;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (Gdx.input.isTouched()) {
                    Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(touch);
                    if (rClick.contains(touch.x, touch.y)) {
                        click.play(0.2f);
                        game.setScreen(new YellowTank(game, music));
                    } else if (lClick.contains(touch.x, touch.y)) {
                        click.play(0.2f);
                        game.setScreen(new GreenTank(game, music));
                    } else if (playButton.contains(touch.x, touch.y)) {
                        click.play(0.2f);
                        game.setScreen(new GameScreen(game, music));
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0,0, 800, 480);
        game.batch.draw(blueWallTexture, 500, 0, 300, 480);
        game.batch.draw(blueCircleTexture, 550, 200, 200, 200);
        game.batch.draw(bannerTexture, 550, 400, 200, 50);
        game.batch.draw(blueTank, 585, 240, 125, 125);
        game.batch.draw(blueTank, Tank.x, Tank.y, Tank.width, Tank.height);
        game.batch.draw(rightClick, rClick.x, rClick.y, rClick.width, rClick.height);
        game.batch.draw(leftClick, lClick.x, lClick.y, lClick.width, lClick.height);
        game.batch.draw(play, playButton.x, playButton.y, playButton.width, playButton.height);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            Tank.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            Tank.x += 200 * Gdx.graphics.getDeltaTime();
        if (Tank.x < 80)
            Tank.x = 80;
        if (Tank.x > 200)
            Tank.x = 200;

        Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touch);
        if (rClick.contains(touch.x, touch.y)) {
            game.batch.begin();
            game.batch.draw(rab, rabb.x, rabb.y, rabb.width, rabb.height);
            game.batch.end();
        } else if (lClick.contains(touch.x, touch.y)) {
            game.batch.begin();
            game.batch.draw(lab, labb.x, labb.y, labb.width, labb.height);
            game.batch.end();
        } else if (pabb.contains(touch.x, touch.y)) {
            game.batch.begin();
            game.batch.draw(pab, pabb.x, pabb.y, pabb.width, pabb.height);
            game.batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);

    }

    @Override
    public void dispose() {
    }
}
