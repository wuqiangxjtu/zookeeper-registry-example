package cn.ryanwu.registry;

public class Provider {
	
	private Entity entity;
	
	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Provider(Entity entity) {
		this.entity = entity;
	}

}
