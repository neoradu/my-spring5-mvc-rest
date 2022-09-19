package guru.springframework.api.v1.model;

import lombok.Builder;

@Builder
public class ErrorDTO {
	private String message;
	private String url;
	private String extraInfo;
	public ErrorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ErrorDTO(String message, String url, String extraInfo) {
		super();
		this.message = message;
		this.url = url;
		this.extraInfo = extraInfo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}




	
}
