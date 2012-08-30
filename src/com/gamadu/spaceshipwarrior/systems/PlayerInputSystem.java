package com.gamadu.spaceshipwarrior.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.TrigLUT;
import com.artemis.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.gamadu.spaceshipwarrior.EntityFactory;
import com.gamadu.spaceshipwarrior.components.Player;
import com.gamadu.spaceshipwarrior.components.Position;
import com.gamadu.spaceshipwarrior.components.Velocity;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {
	private static final float HorizontalThrusters = 300;
	private static final float HorizontalMaxSpeed = 300;
	private static final float VerticalThrusters = 200;
	private static final float VerticalMaxSpeed = 200;
	private static final float FireRate = 0.1f;
	
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	
	private boolean up, down, left, right;
	private boolean shoot;
	private float timeToFire;
	
	private float destinationX, destinationY;
	private OrthographicCamera camera;
	
	public PlayerInputSystem(OrthographicCamera camera) {
		super(Aspect.getAspectFor(Position.class, Velocity.class, Player.class));
		this.camera = camera;
	}
	
	@Override
	protected void initialize() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);
		Velocity velocity = vm.get(e);
		
		Ray ray = camera.getPickRay(Gdx.input.getX(), Gdx.input.getY());
		Vector3 ep = ray.getEndPoint(0);
		destinationX = ep.x;
		destinationY = ep.y;
		
		//float angleInRadians = Utils.angleInRadians(position.x, position.y, destinationX, destinationY);
		
		//position.x += TrigLUT.cos(angleInRadians) * 500f * world.getDeltaFloat();
		//position.y += TrigLUT.sin(angleInRadians) * 500f * world.getDeltaFloat();
		
		position.x = ep.x;
		position.y = ep.y;
		
		/*
		if(up) {
			velocity.vectorY = MathUtils.clamp(velocity.vectorY+(world.getDeltaFloat()*VerticalThrusters), -VerticalMaxSpeed, VerticalMaxSpeed);
		}
		if(down) {
			velocity.vectorY = MathUtils.clamp(velocity.vectorY-(world.getDeltaFloat()*VerticalThrusters), -VerticalMaxSpeed, VerticalMaxSpeed);
		}
		
		if(left) {
			velocity.vectorX = MathUtils.clamp(velocity.vectorX-(world.getDeltaFloat()*HorizontalThrusters), -HorizontalMaxSpeed, HorizontalMaxSpeed);
		}
		if(right) {
			velocity.vectorX = MathUtils.clamp(velocity.vectorX+(world.getDeltaFloat()*HorizontalThrusters), -HorizontalMaxSpeed, HorizontalMaxSpeed);
		}*/
		
		if(shoot) {
			if(timeToFire <= 0) {
				EntityFactory.createPlayerBullet(world, position.x-27, position.y+2).addToWorld();
				EntityFactory.createPlayerBullet(world, position.x+27, position.y+2).addToWorld();
				timeToFire = FireRate;
			}
		}
		if(timeToFire > 0) {
			timeToFire -= world.getDeltaFloat();
			if(timeToFire < 0) {
				timeToFire = 0;
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.A) {
			left = true;
		}
		else if(keycode == Input.Keys.D) {
			right = true;
		}
		else if(keycode == Input.Keys.W) {
			up = true;
		}
		else if(keycode == Input.Keys.S) {
			down = true;
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.A) {
			left = false;
		}
		else if(keycode == Input.Keys.D) {
			right = false;
		}
		else if(keycode == Input.Keys.W) {
			up = false;
		}
		else if(keycode == Input.Keys.S) {
			down = false;
		}
		
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if(button == Input.Buttons.LEFT) {
			shoot = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if(button == Input.Buttons.LEFT) {
			shoot = false;
		}
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
