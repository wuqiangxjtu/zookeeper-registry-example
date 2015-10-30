package cn.ryanwu.registry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ZookeeperRegistryTest {
	
	
	
	@Test
	public void test_provider_register() throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		Entity entity = new Entity();
		entity.setService("test_provider_register");
		entity.setNode("node1");
		entity.setType(ZookeeperRegistry.PROVIDER);
		Registry registry =  new ZookeeperRegistry("127.0.0.1:2181");
		registry.register(entity);
		latch.await(180, TimeUnit.SECONDS);
		
	}
	
	@Test
	public void test_provider_unregister() throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		Entity entity = new Entity();
		entity.setService("test_provider_register");
		entity.setNode("node1");
		entity.setType(ZookeeperRegistry.PROVIDER);
		Registry registry =  new ZookeeperRegistry("127.0.0.1:2181");
		registry.register(entity);
		System.out.println("-----------------> register");
		Thread.sleep(30000);
		registry.unregister(entity);
		System.out.println("-----------------> unregister");
		latch.await(180, TimeUnit.SECONDS);
	}
	
	@Test
	public void test_provider_register_consumer_watch() throws Exception{
		CountDownLatch latch = new CountDownLatch(1);
		ZkClient zkClient = new CuratorZkClient("127.0.0.1:2181");
		
		//provider注册
		Entity entity = new Entity();
		entity.setService("test_provider_register_consumer_watch");
		entity.setNode("node1");
		entity.setType(ZookeeperRegistry.PROVIDER);
		Registry provider1Registry =  new ZookeeperRegistry("127.0.0.1:2181");
		provider1Registry.register(entity);
		System.out.println("-----------------> node1注册");
		System.out.println("providers: " + zkClient.getChild("/zookeeper-registry-example/provider/test_provider_register_consumer_watch"));
		
		//consumer订阅
		Consumer consumer = new Consumer("test_provider_register_consumer_watch", "node1");
		Registry consumerRegistry =  new ZookeeperRegistry("127.0.0.1:2181");
		System.out.println("-----------------> consumer订阅");
		consumerRegistry.subscribe(entity, consumer);
		Thread.sleep(2000);
		consumer.printProviders();
		
		//provider2注册
		Registry provider2Registry =  new ZookeeperRegistry("127.0.0.1:2181");
		entity.setNode("node2");
		provider2Registry.register(entity);
		System.out.println("-----------------> node2注册");
//		System.out.println(zkClient.getChild("/zookeeper-registry-example/provider/test_provider_register_consumer_watch"));
		Thread.sleep(2000);
		consumer.printProviders();
		
		//provider3注册
		Registry provider3Registry =  new ZookeeperRegistry("127.0.0.1:2181");
		entity.setNode("node3");
		provider3Registry.register(entity);
		System.out.println("-----------------> node3注册");
//		System.out.println(zkClient.getChild("/zookeeper-registry-example/provider/test_provider_register_consumer_watch"));
		Thread.sleep(2000);
		consumer.printProviders();
		
		//provider3 退出
		System.out.println("-----------------> node2退出");
		provider3Registry.unregister(entity);
		Thread.sleep(2000);
		consumer.printProviders();
		
		latch.await();
	}

}
