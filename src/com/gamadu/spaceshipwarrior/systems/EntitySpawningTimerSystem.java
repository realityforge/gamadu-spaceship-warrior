package com.gamadu.spaceshipwarrior.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.Timer;
import com.badlogic.gdx.math.MathUtils;
import com.gamadu.spaceshipwarrior.EntityFactory;
import com.gamadu.spaceshipwarrior.SpaceshipWarrior;
import com.gamadu.spaceshipwarrior.components.Sprite;

public class EntitySpawningTimerSystem extends EntitySystem {

	private Timer timer1;
	private Timer timer2;
	private Timer timer3;

	public EntitySpawningTimerSystem() {
		super(Aspect.getEmpty());
		
		timer1 = new Timer(2,true) {
			@Override
			public void execute() {
				EntityFactory.createEnemyShip(world, "enemy1", Sprite.Layer.ACTORS_3, 10, MathUtils.random(-SpaceshipWarrior.FRAME_WIDTH/2, SpaceshipWarrior.FRAME_WIDTH/2), SpaceshipWarrior.FRAME_HEIGHT/2+50, 0, -40).addToWorld();
			}
		};
		
		timer2 = new Timer(6,true) {
			@Override
			public void execute() {
				EntityFactory.createEnemyShip(world, "enemy2", Sprite.Layer.ACTORS_2, 20, MathUtils.random(-SpaceshipWarrior.FRAME_WIDTH/2, SpaceshipWarrior.FRAME_WIDTH/2), SpaceshipWarrior.FRAME_HEIGHT/2+100, 0, -30).addToWorld();
			}
		};
		
		timer3 = new Timer(12,true) {
			@Override
			public void execute() {
				EntityFactory.createEnemyShip(world, "enemy3", Sprite.Layer.ACTORS_1, 60, MathUtils.random(-SpaceshipWarrior.FRAME_WIDTH/2, SpaceshipWarrior.FRAME_WIDTH/2), SpaceshipWarrior.FRAME_HEIGHT/2+200, 0, -20).addToWorld();
			}
		};
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		timer1.update(world.getDeltaFloat());
		timer2.update(world.getDeltaFloat());
		timer3.update(world.getDeltaFloat());
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}


}
