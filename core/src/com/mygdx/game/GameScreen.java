package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends CustomTankStars implements Screen {
    final CustomTankStars game;
    private final TextureRegion backgroundTexture;
    private final TextureRegion terrainTexture;
    private final Texture pause;
    private final Rectangle pauseButton;
    private final Texture pab;
    private final Rectangle pabb;
    private final Texture tank1;
    private final Texture tank2;
    private final Rectangle tank1bound;
    private final Rectangle tank2bound;
    private final Texture healthBar1;
    private final Rectangle healthBar1Bounds;
    private final Texture healthBar2;
    private final Rectangle healthBar2Bounds;
    private final OrthographicCamera camera;
    private final Sound click;
    private final Music theme;
    private final Sprite tank1sprite;
    final Music music;
    public GameScreen(final CustomTankStars game, final Music music) {
        this.game = game;
        this.music = music;
        if (music.isPlaying()) {
            music.stop();
        }
        Texture backgroundImage = new Texture(Gdx.files.internal("game.png"));
        backgroundTexture = new TextureRegion(backgroundImage, 0, 0, 1400, 788);
        Texture terrain = new Texture(Gdx.files.internal("game_screen1.png"));
        terrainTexture = new TextureRegion(terrain, 0, 0, 577, 433);
        pause = new Texture(Gdx.files.internal("button_pause.png"));
        healthBar1 = new Texture(Gdx.files.internal("health_bar1.png"));
        healthBar2 = new Texture(Gdx.files.internal("health_bar2.png"));
        tank1 = new Texture(Gdx.files.internal("yellow_tank.png"));
        tank2 = new Texture(Gdx.files.internal("green_tank_flip.png"));
        pab = new Texture(Gdx.files.internal("button_active_pause.png"));

        click = Gdx.audio.newSound(Gdx.files.internal("click_sound.wav"));
        theme = Gdx.audio.newMusic(Gdx.files.internal("battle.mp3"));
        theme.setLooping(true);

        pauseButton = new Rectangle();
        pauseButton.x = 740;
        pauseButton.y = 410;
        pauseButton.width = 50;
        pauseButton.height = 50;

        pabb = new Rectangle();
        pabb.x = 740;
        pabb.y = 410;
        pabb.width = 50;
        pabb.height = 50;

        healthBar1Bounds = new Rectangle();
        healthBar1Bounds.x = 530;
        healthBar1Bounds.y = 410;
        healthBar1Bounds.width = 200;
        healthBar1Bounds.height = 50;

        healthBar2Bounds = new Rectangle();
        healthBar2Bounds.x = 70;
        healthBar2Bounds.y = 410;
        healthBar2Bounds.width = 200;
        healthBar2Bounds.height = 50;

        tank1bound = new Rectangle();
        tank1bound.x = 130;
        tank1bound.y = 125;
        tank1bound.width = 100;
        tank1bound.height = 100;

        tank2bound = new Rectangle();
        tank2bound.x = 550;
        tank2bound.y = 95;
        tank2bound.width = 100;
        tank2bound.height = 100;

        tank1sprite = new Sprite(new Texture(Gdx.files.internal("yellow_tank.png")));
        tank1sprite.rotate(45f);
        tank1sprite.setPosition(tank1bound.x,tank1bound.y);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {
        theme.play();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (Gdx.input.isTouched()) {
                    Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(touch);
                    if (pauseButton.contains(touch.x, touch.y)) {
                        click.play(0.2f);
                        game.setScreen(new LeaveGame(game, music, theme));
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
        game.batch.draw(terrainTexture, 0,-100, 800, 480);
        game.batch.draw(pause, pauseButton.x, pauseButton.y, pauseButton.width, pauseButton.height);
        game.batch.draw(healthBar1, healthBar1Bounds.x, healthBar1Bounds.y, healthBar1Bounds.width, healthBar1Bounds.height);
        game.batch.draw(healthBar2, healthBar2Bounds.x, healthBar2Bounds.y, healthBar2Bounds.width, healthBar2Bounds.height);
        game.batch.draw(tank1, tank1bound.x, tank1bound.y, tank1bound.width, tank1bound.height);
        game.batch.draw(tank2, tank2bound.x, tank2bound.y, tank2bound.width, tank2bound.height);
        game.batch.end();

        Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touch);
        if (pauseButton.contains(touch.x, touch.y)) {
            game.batch.begin();
            game.batch.draw(pab, pabb.x, pabb.y, pabb.width, pabb.height);
            game.batch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if ((170 < tank1bound.x && tank1bound.x <= 270)) {
                tank1bound.x -= 200 * Gdx.graphics.getDeltaTime();
                tank1bound.y += 80 * Gdx.graphics.getDeltaTime();
                if (tank1bound.y > 125)
                    tank1bound.y = 125;
            }
            if (100 < tank1bound.x && tank1bound.x <= 170) {
                tank1bound.x -= 200 * Gdx.graphics.getDeltaTime();
                if (tank1bound.x > 170)
                    tank1bound.x = 170;
                if (tank1bound.x < 100)
                    tank1bound.x = 100;
            }
            if (50 < tank1bound.x && tank1bound.x <= 100) {
                tank1bound.x -= 200 * Gdx.graphics.getDeltaTime();
                tank1bound.y -= 80 * Gdx.graphics.getDeltaTime();
                if (tank1bound.x < 50)
                    tank1bound.x = 50;
                if (tank1bound.x > 100)
                    tank1bound.x = 100;
            }
            if (5 < tank1bound.x && tank1bound.x <= 50) {
                tank1bound.x -= 200 * Gdx.graphics.getDeltaTime();
                if (tank1bound.x < 5)
                    tank1bound.x = 5;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if ((170 <= tank1bound.x && tank1bound.x < 270)) {
                tank1bound.x += 200 * Gdx.graphics.getDeltaTime();
                tank1bound.y -= 80 * Gdx.graphics.getDeltaTime();
                if (tank1bound.x > 270)
                    tank1bound.x = 270;
                if (tank1bound.x < 170)
                    tank1bound.x = 170;
                if (tank1bound.y < 80)
                    tank1bound.y = 80;
            }
            if ((100 <= tank1bound.x && tank1bound.x < 170)) {
                tank1bound.x += 200 * Gdx.graphics.getDeltaTime();
                if (tank1bound.x > 170)
                    tank1bound.x = 170;
                if (tank1bound.x < 100)
                    tank1bound.x = 100;
            }
            if (50 <= tank1bound.x && tank1bound.x < 100) {
                tank1bound.x += 200 * Gdx.graphics.getDeltaTime();
                tank1bound.y += 80 * Gdx.graphics.getDeltaTime();
                if (tank1bound.y > 125)
                    tank1bound.y = 125;
            }
            if (5 <= tank1bound.x && tank1bound.x < 50) {
                tank1bound.x += 200 * Gdx.graphics.getDeltaTime();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if ((170 < tank2bound.x && tank2bound.x <= 270)) {
                tank2bound.x -= 200 * Gdx.graphics.getDeltaTime();
                tank2bound.y += 80 * Gdx.graphics.getDeltaTime();
                if (tank2bound.y > 125)
                    tank2bound.y = 125;
            }
            if (100 < tank2bound.x && tank2bound.x <= 170) {
                tank2bound.x -= 200 * Gdx.graphics.getDeltaTime();
                if (tank2bound.x > 170)
                    tank2bound.x = 170;
                if (tank2bound.x < 100)
                    tank2bound.x = 100;
            }
            if (50 < tank2bound.x && tank2bound.x <= 100) {
                tank2bound.x -= 200 * Gdx.graphics.getDeltaTime();
                tank2bound.y -= 80 * Gdx.graphics.getDeltaTime();
                if (tank2bound.x < 50)
                    tank2bound.x = 50;
                if (tank2bound.x > 100)
                    tank2bound.x = 100;
            }
            if (5 < tank2bound.x && tank2bound.x <= 50) {
                tank2bound.x -= 200 * Gdx.graphics.getDeltaTime();
                if (tank2bound.x < 5)
                    tank2bound.x = 5;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if ((170 <= tank2bound.x && tank2bound.x < 270)) {
                tank2bound.x += 200 * Gdx.graphics.getDeltaTime();
                tank2bound.y -= 80 * Gdx.graphics.getDeltaTime();
                if (tank2bound.x > 270)
                    tank2bound.x = 270;
                if (tank2bound.x < 170)
                    tank2bound.x = 170;
                if (tank2bound.y < 80)
                    tank2bound.y = 80;
            }
            if ((100 <= tank2bound.x && tank2bound.x < 170)) {
                tank2bound.x += 200 * Gdx.graphics.getDeltaTime();
                if (tank2bound.x > 170)
                    tank2bound.x = 170;
                if (tank2bound.x < 100)
                    tank2bound.x = 100;
            }
            if (50 <= tank2bound.x && tank2bound.x < 100) {
                tank2bound.x += 200 * Gdx.graphics.getDeltaTime();
                tank2bound.y += 80 * Gdx.graphics.getDeltaTime();
                if (tank2bound.y > 125)
                    tank2bound.y = 125;
            }
            if (5 <= tank2bound.x && tank2bound.x < 50) {
                tank2bound.x += 200 * Gdx.graphics.getDeltaTime();
            }
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

    }

    @Override
    public void dispose() {
        tank1.dispose();
        tank2.dispose();
        theme.dispose();
        click.dispose();

    }
}
