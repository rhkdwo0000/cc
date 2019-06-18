package tom.mediabc.search.batch;

import java.util.Map;

import org.json.simple.JSONObject;

public class CCIndexer {
	
	private ElasticsearchSetting elasticsearchSetting = new ElasticsearchSetting();
	private Map<String, Object> json = null;
	
	public void ElasticsearchInsert(JSONObject jsonObject ,String ccid , String version , String metaSeq) {
		json.put("ccid", ccid);
		json.put("version", version);
		json.put("metaSeq", metaSeq);
		json.put("copyright_cline", jsonObject.get("copyright_cline"));
		json.put("country", jsonObject.get("copyright_cline"));
		json.put("original_spoken_locale", jsonObject.get("original_spoken_locale"));
		json.put("production_company", jsonObject.get("production_company"));
		json.put("ratings", jsonObject.get("ratings"));
		json.put("theatrical_release_date", jsonObject.get("theatrical_release_date"));
		json.put("title", jsonObject.get("title"));
		json.put("vender_id", jsonObject.get("vender_id"));
		json.put("cast", jsonObject.get("cast"));
		json.put("crew", jsonObject.get("crew"));
		json.put("genres", jsonObject.get("genres"));
		
		elasticsearchSetting.insert(json, "ccontents", "cc", ccid);
	}
	
	public void ElasticsearchUpdate(JSONObject jsonObject ,String ccid) {
		
		
		
	}
	
	public void ElasticsearchDelete(JSONObject jsonObject ,String ccid) {
		
		
		
	}
	
	public void ElasticsearchSearch(JSONObject jsonObject) {
		
		
		
	}
	
	
	
	
	
}
