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

public class MainMenu implements Screen {
    final CustomTankStars game;
    private final TextureRegion backgroundRegion;
    private final Texture play;
    private final Rectangle playBar;
    private final Texture resume;
    private final Rectangle resumeBar;
    private final Texture exit;
    private final TextureRegion menuTexture;
    private final Texture rate;
    private final Rectangle exitBar;
    private final Rectangle rateBar;
    private final OrthographicCamera camera;
    private final Sound click;
    final Music music;
    final Music theme;

    public MainMenu(final CustomTankStars game, final Music music, final Music theme) {
        this.game = game;
        this.music = music;
        this.theme = theme;
        if (theme != null && theme.isPlaying()) {
            theme.stop();
        }
        Texture background = new Texture(Gdx.files.internal("bg_menu.png"));
        backgroundRegion = new TextureRegion(background, 0, 0, 1400, 788);
        Texture menu = new Texture(Gdx.files.internal("main_menu.png"));
        menuTexture = new TextureRegion(menu, 0, 0, 1136, 1621);
        play = new Texture(Gdx.files.internal("button_play.png"));
        resume = new Texture(Gdx.files.internal("button_resume.png"));
        exit = new Texture(Gdx.files.internal("button_exit.png"));
        rate = new Texture(Gdx.files.internal("button_rate.png"));
        click = Gdx.audio.newSound(Gdx.files.internal("click_sound.wav"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        playBar = new Rectangle();
        playBar.x = 371;
        playBar.y = 263;
        playBar.width = 124;
        playBar.height = 27;

        resumeBar = new Rectangle();
        resumeBar.x = 371;
        resumeBar.y = 209;
        resumeBar.width = 124;
        resumeBar.height = 27;

        rateBar = new Rectangle();
        rateBar.x = 371;
        rateBar.y = 152;
        rateBar.width = 124;
        rateBar.height = 27;

        exitBar = new Rectangle();
        exitBar.x = 371;
        exitBar.y = 97;
        exitBar.width = 124;
        exitBar.height = 27;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (Gdx.input.isTouched()) {
                    Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(touch);
                    if (playBar.contains(touch.x, touch.y)) {
                        click.play(0.2f);
                        game.setScreen(new SelectTank(game, music));
                    } else if (exitBar.contains(touch.x, touch.y)) {
                        click.play(0.2f);
                        game.setScreen(new ExitPage(game, music));
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
        game.batch.draw(backgroundRegion, 0,0, 800, 480);
        game.batch.draw(menuTexture, 300,50, 267, 380);
        game.batch.draw(play, playBar.x, playBar.y, playBar.width, playBar.height);
        game.batch.draw(rate, rateBar.x, rateBar.y, rateBar.width, rateBar.height);
        game.batch.draw(resume, resumeBar.x, resumeBar.y, resumeBar.width, resumeBar.height);
        game.batch.draw(exit, exitBar.x, exitBar.y, exitBar.width, exitBar.height);
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
