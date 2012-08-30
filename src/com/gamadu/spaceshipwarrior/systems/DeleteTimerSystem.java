package com.gamadu.spaceshipwarrior.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.gamadu.spaceshipwarrior.components.DeleteTimer;

public class DeleteTimerSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<DeleteTimer> dtm;

	public DeleteTimerSystem() {
		super(Aspect.getAspectFor(DeleteTimer.class));
	}

	@Override
	protected void process(Entity e) {
		DeleteTimer deleteTimer = dtm.get(e);
		deleteTimer.timer -= world.getDeltaFloat();
		
		if(deleteTimer.timer <= 0) {
			e.deleteFromWorld();
		}
	}

}
