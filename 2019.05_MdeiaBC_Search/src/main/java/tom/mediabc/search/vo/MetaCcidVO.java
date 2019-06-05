package tom.mediabc.search.vo;

import java.util.Date;

import lombok.Data;

@Data
public class MetaCcidVO {

	
	public static final String CLASS_BASIC     = "basic";
	public static final String CLASS_EXTENSION = "ext";
	
	private String ccid;
	private String version;
	private Date regDate;
}
