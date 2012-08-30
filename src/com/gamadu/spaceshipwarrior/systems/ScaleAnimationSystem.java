package com.gamadu.spaceshipwarrior.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.gamadu.spaceshipwarrior.components.ScaleAnimation;
import com.gamadu.spaceshipwarrior.components.Sprite;

public class ScaleAnimationSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<ScaleAnimation> sa;
	@Mapper
	ComponentMapper<Sprite> sm;

	public ScaleAnimationSystem() {
		super(Aspect.getAspectFor(ScaleAnimation.class));
	}

	@Override
	protected void process(Entity e) {
		ScaleAnimation scaleAnimation = sa.get(e);
		if (scaleAnimation.active) {
			Sprite sprite = sm.get(e);

			sprite.scale += scaleAnimation.speed * world.getDeltaFloat();

			if (sprite.scale > scaleAnimation.max) {
				sprite.scale = scaleAnimation.max;
				scaleAnimation.active = false;
			} else if (sprite.scale < scaleAnimation.min) {
				sprite.scale = scaleAnimation.min;
				scaleAnimation.active = false;
			}
		}
	}

}
