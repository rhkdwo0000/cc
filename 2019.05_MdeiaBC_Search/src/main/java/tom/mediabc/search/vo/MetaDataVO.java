package tom.mediabc.search.vo;

import lombok.Data;

@Data
public class MetaDataVO {

	
	public static final String METATYPE_BASIC_MOVIEv01 = "basic-movie.v1";
	
	private int meta_seq;
	private String ccid;
	private String version;
	private String metaPath;
	private String metadata;
	private String metaType;
	private String metaClass;
	private String title;
	private String contentType;
	

	
}
