package tom.mediabc.search.vo;

import lombok.Data;

@Data
public class MetaFilesVO {

	private int metaFileSeq;
	private String ccid;
	private String version;
	private String metaPath;
	private long   metaSize;
	private String metaClass;

	
}
