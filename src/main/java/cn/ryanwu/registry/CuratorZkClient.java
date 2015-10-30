package cn.ryanwu.registry;

import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class CuratorZkClient implements ZkClient{
	
	private CuratorFramework client;
	
	public CuratorZkClient(String url) {
		RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.newClient(url, 5000, 3000,retry);
		client.start();
	}

	public void create(String url, boolean ephemeral) throws Exception {
		CreateMode mode = ephemeral ? CreateMode.EPHEMERAL : CreateMode.PERSISTENT;
		client.create().creatingParentsIfNeeded().withMode(mode).forPath(url);

	}

	public void delete(String url) throws Exception {
		client.delete().forPath(url);
	}

	public void watchChild(String url, ChildListener listener) throws Exception {
		PathChildrenCache cache = new PathChildrenCache(client, url, true);
		cache.start(StartMode.POST_INITIALIZED_EVENT);
		cache.getListenable().addListener(listener);
	}

	public List<String> getChild(String url) throws Exception {
		return client.getChildren().forPath(url);

	}
	
	

}
