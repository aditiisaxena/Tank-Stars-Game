package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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

public class LeaveGame implements Screen {
    final CustomTankStars game;
    private final TextureRegion backgroundTexture;
    private final TextureRegion leaveGameTexture;
    private final TextureRegion terrainTexture;
    private final Texture pause;
    private final Rectangle pauseButton;
    private final Texture tank1;
    private final Texture tank2;
    private final Rectangle tank1bound;
    private final Rectangle tank2bound;
    private final Texture yes;
    private final Rectangle yesbound;
    private final Texture no;
    private final Rectangle nobound;
    private final Texture healthBar1;
    private final Rectangle healthBar1Bounds;
    private final Texture healthBar2;
    private final Rectangle healthBar2Bounds;
    private final OrthographicCamera camera;
    private final Sound click;
    final Music music;
    final Music theme;
    public LeaveGame(final CustomTankStars game, final Music music, final Music theme) {
        this.game = game;
        this.music = music;
        this.theme = theme;
        Texture backgroundImage = new Texture(Gdx.files.internal("game.png"));
        backgroundTexture = new TextureRegion(backgroundImage, 0, 0, 1400, 788);
        Texture terrain = new Texture(Gdx.files.internal("game_screen1.png"));
        terrainTexture = new TextureRegion(terrain, 0, 0, 577, 433);
        pause = new Texture(Gdx.files.internal("button_pause.png"));
        healthBar1 = new Texture(Gdx.files.internal("health_bar1.png"));
        healthBar2 = new Texture(Gdx.files.internal("health_bar2.png"));
        tank1 = new Texture(Gdx.files.internal("yellow_tank.png"));
        tank2 = new Texture(Gdx.files.internal("green_tank_flip.png"));
        yes = new Texture(Gdx.files.internal("yes.png"));
        no = new Texture(Gdx.files.internal("no.png"));
        Texture leaveGame = new Texture(Gdx.files.internal("leave_game.png"));
        leaveGameTexture = new TextureRegion(leaveGame, 0, 0, 938, 771);

        click = Gdx.audio.newSound(Gdx.files.internal("click_sound.wav"));

        pauseButton = new Rectangle();
        pauseButton.x = 740;
        pauseButton.y = 410;
        pauseButton.width = 50;
        pauseButton.height = 50;


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

        yesbound = new Rectangle();
        yesbound.x = 330;
        yesbound.y = 185;
        yesbound.width = 50;
        yesbound.height = 50;

        nobound = new Rectangle();
        nobound.x = 430;
        nobound.y = 185;
        nobound.width = 50;
        nobound.height = 50;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (Gdx.input.isTouched()) {
                    Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(touch);
                    if (yesbound.contains(touch.x, touch.y)) {
                        click.play(0.2f);
                        game.setScreen(new MainMenu(game, music, theme));
                    } else if (nobound.contains(touch.x, touch.y)) {
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
        game.batch.draw(terrainTexture, 0,-100, 800, 480);
        game.batch.draw(leaveGameTexture, 280,170, 250, 200);
        game.batch.draw(pause, pauseButton.x, pauseButton.y, pauseButton.width, pauseButton.height);
        game.batch.draw(healthBar1, healthBar1Bounds.x, healthBar1Bounds.y, healthBar1Bounds.width, healthBar1Bounds.height);
        game.batch.draw(healthBar2, healthBar2Bounds.x, healthBar2Bounds.y, healthBar2Bounds.width, healthBar2Bounds.height);
        game.batch.draw(tank1, tank1bound.x, tank1bound.y, tank1bound.width, tank1bound.height);
        game.batch.draw(tank2, tank2bound.x, tank2bound.y, tank2bound.width, tank2bound.height);
        game.batch.draw(yes, yesbound.x, yesbound.y, yesbound.width, yesbound.height);
        game.batch.draw(no, nobound.x, nobound.y, nobound.width, nobound.height);
        game.batch.end();
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

    }
}
