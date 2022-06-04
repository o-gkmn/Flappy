package com.zgr.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.zgr.game.Flappy;
import com.zgr.sprites.Bird;
import com.zgr.sprites.Tube;

public class PlayState extends State{
	private static final int TUBE_SPACING = 125;
	private static final int TUBE_COUNT = 4;
	private static final int GROUND_Y_OFFSET = -50;
	
	private Bird bird;
	private Texture background;
	private Texture ground;
	private Vector2 groundPos1, groundPos2;
	
	private Array<Tube> tubes;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		bird = new Bird(50,150);
		camera.setToOrtho(false, Flappy.WIDTH / 2, Flappy.HEIGHT / 2);
		background = new Texture("bg.png");
		ground = new Texture("ground.png");
		
		groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSET);
		groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
		
		tubes = new Array<Tube>();
		for(int i = 1; i<=TUBE_COUNT; ++i) {
			tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
		}
	}

	@Override
	public void handleInput() {
		if(Gdx.input.justTouched()) {
			bird.jump();
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
		updateGround();
		bird.update(dt);
		camera.position.x = bird.getPosition().x + 80;
		
		for(int i = 0; i< tubes.size; ++i) {
			Tube tube = tubes.get(i);
			
			if(camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
				tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
			}

			if(tube.collides(bird.getBounds())) {
				gsm.set(new MenuState(gsm));
			}
		
		}
		if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
			gsm.set(new MenuState(gsm));
		}
		camera.update();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
		sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
		for(Tube tube : tubes) {
			sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
			sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
			

		}
		
		sb.draw(ground, groundPos1.x, groundPos1.y, ground.getWidth(), ground.getHeight());
		sb.draw(ground, groundPos2.x, groundPos2.y, ground.getWidth(), ground.getHeight());
		
		sb.end();
	}
	
	public void updateGround() {
		if(camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
			groundPos1.add(ground.getWidth() * 2, 0);
		}
		if(camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
			groundPos2.add(ground.getWidth() * 2, 0);
		}
	}
	

	@Override
	public void dispose() {
		background.dispose();
		bird.dispose();
		ground.dispose();
		for(Tube tube : tubes)
			tube.dispose();
		System.out.println("Play state disposed");
	}

}
