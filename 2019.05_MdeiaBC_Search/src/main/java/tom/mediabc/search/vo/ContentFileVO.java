package tom.mediabc.search.vo;

import lombok.Data;

@Data
public class ContentFileVO {
	
	private int contentFileSeq;
	private int metaSeq;
	private String contentPath;
	private long   contentSize;
	private String contentClass;
}
