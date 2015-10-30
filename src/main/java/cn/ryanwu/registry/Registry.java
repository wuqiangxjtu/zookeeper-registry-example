package cn.ryanwu.registry;



public interface Registry {
    /**
     * 注册数据，比如：提供者地址，消费者地址，路由规则，覆盖规则，等数据。
     */
    void register(Entity entity) throws Exception;

    /**
     * 取消注册.
     */
    void unregister(Entity entity) throws Exception;

    /**
     * 订阅符合条件的已注册数据，当有注册数据变更时自动推送.
     * 
     */
    void subscribe(Entity entity, ChildListener listener) throws Exception;

    /**
     * 取消订阅.
     */
    void unsubscribe(Entity entity, ChildListener listener) throws Exception;

}
