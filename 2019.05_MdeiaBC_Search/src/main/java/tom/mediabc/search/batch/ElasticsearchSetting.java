package tom.mediabc.search.batch;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticsearchSetting {


	private static TransportClient client;
	private Map<String, Object> json = null;

	public void elasticSetting() throws Exception {
		Settings settings = Settings.builder().put("client.transport.sniff", false)
				.put("cluster.name", "elastic") // cx-cluster
				.build();
		client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch.url"), 9300));
//		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.100.8"), 9300));
	}
	
	public void close() {
		// on shutdown
		client.close();
	}

	public static IndexResponse insert(Map<String, Object> json, String index, String type, String id) {
		json = new HashMap<String, Object>();
		if (id == null || "".equals(id)) {
			return client.prepareIndex(index, type).setSource(json).get();
		}
		return client.prepareIndex(index, type, id).setSource(json).get();
	}

	public void upsert(Map<String, Object> exist, Map<String, Object> notExist, String index, String type, String id)
			throws InterruptedException, ExecutionException {
		IndexRequest indexRequest = new IndexRequest(index, type, id).source(notExist);
		UpdateRequest updateRequest = new UpdateRequest(index, type, id).doc(exist).upsert(indexRequest);
		client.update(updateRequest);
	}

	public void update(Map<String, Object> json, String index, String type, String id)
			throws InterruptedException, ExecutionException {
		json = new HashMap<String, Object>();
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(index);
		updateRequest.type(type);
		updateRequest.id(id);
		updateRequest.doc(json);
		client.update(updateRequest).get();
	}
	
	public GetResponse search(String index, String type, String id) {
		return client.prepareGet(index, type, id).get();
	}
	
	public DeleteResponse remove(String index, String type, String id) {
		return client.prepareDelete(index, type, id).get();
	}
	
	
}
