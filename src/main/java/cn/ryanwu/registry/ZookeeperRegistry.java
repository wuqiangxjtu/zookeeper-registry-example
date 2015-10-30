package cn.ryanwu.registry;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

public class ZookeeperRegistry implements Registry {
	
	private static final String ROOT = "/zookeeper-registry-example";
	
	public static final int PROVIDER = 0;
	public static final int CONSUMER = 1;
	
	private ZkClient client;
	
	
	public ZookeeperRegistry(String url) {
		this.client = new CuratorZkClient(url);
		
	}

	public void register(Entity entity) throws Exception {
		client.create(getUrl(entity), true);
	}

	public void unregister(Entity entity) throws Exception {
		client.delete(getUrl(entity));
	}

	public void subscribe(Entity entity, ChildListener listener) throws Exception {
		client.watchChild(getProviderUrl(entity), listener);
	}

	public void unsubscribe(Entity entity, ChildListener listener)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private String getUrl(Entity entity) {
		if(ZookeeperRegistry.PROVIDER == entity.getType()) {
			return getProviderNodeUrl(entity);
		}else{
			return getConsumerNodeUrl(entity);
		}
	}
	
	private String getProviderUrl(Entity entity) {
		return ROOT + "/" + "provider/" + entity.getService();
	}
	
	private String getProviderNodeUrl(Entity entity) {
		return getProviderUrl(entity) + "/" + entity.getNode();
	}
	
	private String getConsumerUrl(Entity entity) {
		return ROOT + "/" + "consumer/" + entity.getService()  + "/" + entity.getNode();
	}
	
	private String getConsumerNodeUrl(Entity entity) {
		return getConsumerUrl(entity) + "/" + entity.getNode();
	}


	
	
}
