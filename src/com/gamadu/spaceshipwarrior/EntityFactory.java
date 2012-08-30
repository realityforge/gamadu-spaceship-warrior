package com.gamadu.spaceshipwarrior;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.math.MathUtils;
import com.gamadu.spaceshipwarrior.components.Bounds;
import com.gamadu.spaceshipwarrior.components.ColorAnimation;
import com.gamadu.spaceshipwarrior.components.DeleteTimer;
import com.gamadu.spaceshipwarrior.components.Health;
import com.gamadu.spaceshipwarrior.components.ParallaxStar;
import com.gamadu.spaceshipwarrior.components.Player;
import com.gamadu.spaceshipwarrior.components.Position;
import com.gamadu.spaceshipwarrior.components.ScaleAnimation;
import com.gamadu.spaceshipwarrior.components.Sprite;
import com.gamadu.spaceshipwarrior.components.Velocity;

public class EntityFactory {
	
	public static Entity createPlayer(World world, float x, float y) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.x = x;
		position.y = y;
		e.addComponent(position);
		
		Sprite sprite = new Sprite();
		sprite.name = "fighter";
		sprite.r = 93/255f;
		sprite.g = 255/255f;
		sprite.b = 129/255f;
		e.addComponent(sprite);
		
		Velocity velocity = new Velocity();
		velocity.vectorX = 0;
		velocity.vectorY = 0;
		e.addComponent(velocity);
		
		Bounds bounds = new Bounds();
		bounds.radius = 33;
		e.addComponent(bounds);
		
		e.addComponent(new Player());
		
		world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER_SHIP);
		
		return e;
	}
	
	public static Entity createPlayerBullet(World world, float x, float y) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.x = x;
		position.y = y;
		e.addComponent(position);
		
		Sprite sprite = new Sprite();
		sprite.name = "bullet";
		e.addComponent(sprite);
		
		Velocity velocity = new Velocity();
		velocity.vectorY = 800;
		e.addComponent(velocity);
		
		Bounds bounds = new Bounds();
		bounds.radius = 5;
		e.addComponent(bounds);
		
		DeleteTimer dt = new DeleteTimer();
		dt.timer = 5;
		e.addComponent(dt);

		world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER_BULLETS);
		
		return e;
	}
	
	public static Entity createEnemyShip(World world, String name, float health, float x, float y, float velocityX, float velocityY) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.x = x;
		position.y = y;
		e.addComponent(position);
		
		Sprite sprite = new Sprite();
		sprite.name = name;
		sprite.r = 255/255f;
		sprite.g = 0/255f;
		sprite.b = 142/255f;
		e.addComponent(sprite);
		
		Velocity velocity = new Velocity();
		velocity.vectorX = velocityX;
		velocity.vectorY = velocityY;
		e.addComponent(velocity);
		
		Bounds bounds = new Bounds();
		bounds.radius = 40;
		e.addComponent(bounds);
		
		Health h = new Health();
		h.health = h.maximumHealth = health;
		e.addComponent(h);
		
		world.getManager(GroupManager.class).add(e, Constants.Groups.ENEMY_SHIPS);
		
		return e;
	}
	
	public static Entity createExplosion(World world, float x, float y, float scale) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.x = x;
		position.y = y;
		e.addComponent(position);
		
		Sprite sprite = new Sprite();
		sprite.name = "particle";
		sprite.scale = scale;
		e.addComponent(sprite);
		
		DeleteTimer dt = new DeleteTimer();
		dt.timer = 0.5f;
		e.addComponent(dt);
		
		
		ScaleAnimation scaleAnimation = new ScaleAnimation();
		scaleAnimation.active = true;
		scaleAnimation.max = scale;
		scaleAnimation.min = scale/100f;
		scaleAnimation.speed = -3.0f;
		scaleAnimation.repeat = false;
		e.addComponent(scaleAnimation);
		
		return e;
	}	
	
	public static Entity createStar(World world) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.x = MathUtils.random(-SpaceshipWarrior.FRAME_WIDTH/2, SpaceshipWarrior.FRAME_WIDTH/2);
		position.y = MathUtils.random(-SpaceshipWarrior.FRAME_HEIGHT/2, SpaceshipWarrior.FRAME_HEIGHT/2);
		e.addComponent(position);
		
		Sprite sprite = new Sprite();
		sprite.name = "star";
		sprite.scale = MathUtils.random(0.2f, 0.4f);
		sprite.a = MathUtils.random(0.1f, 0.75f);
		e.addComponent(sprite);
		
		Velocity velocity = new Velocity();
		velocity.vectorY = MathUtils.random(-10f, -60f);
		e.addComponent(velocity);
		
		e.addComponent(new ParallaxStar());
		
		ColorAnimation colorAnimation = new ColorAnimation();
		colorAnimation.alphaAnimate = true;
		colorAnimation.alphaSpeed = MathUtils.random(0.2f, 1f);
		colorAnimation.alphaMin = 0.1f;
		colorAnimation.alphaMax = 0.2f;
		e.addComponent(colorAnimation);
		
		return e;
	}

}
