package com.gamadu.spaceshipwarrior.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.gamadu.spaceshipwarrior.SpaceshipWarrior;
import com.gamadu.spaceshipwarrior.components.ParallaxStar;
import com.gamadu.spaceshipwarrior.components.Position;

public class ParallaxStarRepeatingSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> pm;

	public ParallaxStarRepeatingSystem() {
		super(Aspect.getAspectFor(ParallaxStar.class, Position.class));
	}

	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);
		
		if(position.y < -SpaceshipWarrior.FRAME_HEIGHT/2) {
			position.y = SpaceshipWarrior.FRAME_HEIGHT/2;
		}
	}

}
