package cn.ryanwu.registry;

import java.util.List;

public interface ZkClient {

	void create(String url, boolean ephemeral) throws Exception;

	void delete(String url) throws Exception;

	void watchChild(String url, ChildListener listener) throws Exception;

	List<String> getChild(String url) throws Exception;

}
