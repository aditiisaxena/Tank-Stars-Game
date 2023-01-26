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

import static java.lang.System.exit;

public class ExitPage implements Screen  {
    final CustomTankStars game;
    private final TextureRegion backgroundRegion;
    private final Sound click;
    private final OrthographicCamera camera;
    private final Texture yes;
    private final Rectangle yesbound;
    private final Texture no;
    private final Rectangle nobound;
    final Music music;
    private final TextureRegion exitGameTexture;
    public ExitPage(final CustomTankStars game, final Music music) {
        this.game = game;
        this.music = music;
        Texture background = new Texture(Gdx.files.internal("bg_menu.png"));
        backgroundRegion = new TextureRegion(background, 0, 0, 1400, 788);
        Texture leaveGame = new Texture(Gdx.files.internal("exit_game.png"));
        exitGameTexture = new TextureRegion(leaveGame, 0, 0, 939, 771);
        yes = new Texture(Gdx.files.internal("yes.png"));
        no = new Texture(Gdx.files.internal("no.png"));
        click = Gdx.audio.newSound(Gdx.files.internal("click_sound.wav"));

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
                        exit(0);
                    } else if (nobound.contains(touch.x, touch.y)) {
                        click.play(0.2f);
                        game.setScreen(new MainMenu(game, music, null));
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
        game.batch.draw(exitGameTexture, 280,170, 250, 200);
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
