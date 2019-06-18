package tom.mediabc.search.batch;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class CCIndexerJsonParse {

	private JSONParser jsonParser = null;
	private JSONObject jsonObject = null;
	private JSONArray jsonArray = null;
	
	public String manifestParse() throws Exception {
		
		jsonObject = (JSONObject) jsonParser.parse(new FileReader("/manifest.json"));
		jsonArray = (JSONArray) jsonObject.get("basic_meta");
		
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = (JSONObject) jsonArray.get(i);
			
			if (jsonObject.get("type").equals("manifest")) {
				return (String)jsonObject.get("type");
			}
		}
		return null;
	}
	
	
	public JSONObject basicMetaParse( String basicMetapath) throws Exception {
		 jsonObject = (JSONObject) jsonParser.parse(new FileReader(basicMetapath));
		
		 
		 jsonObject =  (JSONObject)jsonObject.get("metadata");
		 
		 jsonObject.get("vender_id");
		 jsonObject.get("country");
		 jsonObject.get("original_spoken_locale");
		 jsonObject.get("title");
		 jsonObject.get("production_company");
		 jsonObject.get("copyright_cline");
		 jsonObject.get("theatrical_release_date");
		 jsonObject.get("genre");
		 jsonObject.get("rating");
		 jsonObject.get("cast");
		 jsonObject.get("crew");
		 
		 
		 return null;
	}
	
	
	
}
