package com.gamadu.spaceshipwarrior.systems;

import java.util.HashMap;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gamadu.spaceshipwarrior.SpaceshipWarrior;
import com.gamadu.spaceshipwarrior.components.Position;
import com.gamadu.spaceshipwarrior.components.Sprite;

public class SpriteRenderSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Sprite> sm;
	
	private HashMap<String, AtlasRegion> regions;
	private TextureAtlas textureAtlas;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Bag<AtlasRegion> regionsByEntity;
	private BitmapFont font;
	
	public SpriteRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectFor(Position.class, Sprite.class));
		this.camera = camera;
	}
	
	@Override
	protected void initialize() {
		regions = new HashMap<String, AtlasRegion>();
		textureAtlas = new TextureAtlas(Gdx.files.internal("textures/pack"), Gdx.files.internal("textures"));
		for(AtlasRegion r : textureAtlas.getRegions()) {
			regions.put(r.name, r);
		}
		
		batch = new SpriteBatch();
		
		regionsByEntity = new Bag<AtlasRegion>();
		
		
		Texture fontTexture = new Texture(Gdx.files.internal("fonts/normal_0.png"));
		fontTexture.setFilter(TextureFilter.Linear, TextureFilter.MipMapLinearLinear);
		TextureRegion fontRegion = new TextureRegion(fontTexture);
		font = new BitmapFont(Gdx.files.internal("fonts/normal.fnt"), fontRegion, false);
		font.setUseIntegerPositions(false);
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}

	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);
		Sprite sprite = sm.get(e);
		
		AtlasRegion spriteRegion = regionsByEntity.get(e.getId());
		batch.setColor(sprite.r, sprite.g, sprite.b, sprite.a);
		
		float posX = position.x-(spriteRegion.getRegionWidth()/2*sprite.scale);
		float posY = position.y-(spriteRegion.getRegionHeight()/2*sprite.scale);
		batch.draw(spriteRegion, posX, posY, 0, 0, spriteRegion.getRegionWidth(), spriteRegion.getRegionHeight(), sprite.scale, sprite.scale, 0);
		
		//GdxUtils.drawCentered(batch, spriteRegion, position.x, position.y);
	}
	
	@Override
	protected void end() {
		batch.setColor(1,1,1,1);
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), -(SpaceshipWarrior.FRAME_WIDTH/2)+20, SpaceshipWarrior.FRAME_HEIGHT/2-20);
		font.draw(batch, "Active entities: " + world.getEntityManager().getActiveEntityCount(), -(SpaceshipWarrior.FRAME_WIDTH/2)+20, SpaceshipWarrior.FRAME_HEIGHT/2-40);
		font.draw(batch, "Total created: " + world.getEntityManager().getTotalCreated(), -(SpaceshipWarrior.FRAME_WIDTH/2)+20, SpaceshipWarrior.FRAME_HEIGHT/2-60);
		font.draw(batch, "Total deleted: " + world.getEntityManager().getTotalDeleted(), -(SpaceshipWarrior.FRAME_WIDTH/2)+20, SpaceshipWarrior.FRAME_HEIGHT/2-80);
		batch.end();
	}
	
	@Override
	protected void inserted(Entity e) {
		Sprite sprite = sm.get(e);
		regionsByEntity.set(e.getId(), regions.get(sprite.name));
	}
	
	@Override
	protected void removed(Entity e) {
		regionsByEntity.set(e.getId(), null);
	}

}
