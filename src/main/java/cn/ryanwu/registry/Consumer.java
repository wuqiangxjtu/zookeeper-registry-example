package cn.ryanwu.registry;

import java.util.HashSet;
import java.util.Set;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

public class Consumer implements ChildListener {

	private Set<String> providers;

	private Entity entity;

	public Consumer(String service, String node) {
		this.entity = new Entity();
		this.entity.setService(service);
		this.entity.setNode(node);
		this.entity.setType(ZookeeperRegistry.CONSUMER);
		this.providers = new HashSet<String>();
	}

	public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
			throws Exception {
		switch (event.getType()) {
		case CHILD_ADDED:
			// System.out.println("CHILD_ADD," + event.getData().getPath());
			providers.add(event.getData().getPath());
			break;
		case CHILD_UPDATED:
			// System.out.println("CHILD_UPDATEED" + event.getData().getPath());
			providers.add(event.getData().getPath());
			break;
		case CHILD_REMOVED:
			// System.out.println("CHILD_REMOVED," + event.getData().getPath());
			providers.remove(event.getData().getPath());
			break;
		default:
			break;
		}

	}

	public void printProviders() {
		System.out
				.println("------------->Print providres by consumer:start<----------");
		for (String url : providers) {
			System.out.println(url);
		}
		System.out
				.println("------------->Print providres by consumer:end<----------");
	}

	public Set<String> getProviders() {
		return providers;
	}

	public Entity getEntity() {
		return entity;
	}

}
