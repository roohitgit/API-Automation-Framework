package models;

import java.util.Map;

public class ObjectResponse {

	 private String id;
	    private String name;
	    private Map<String, Object> data;
	    private String createdAt;

	    public String getId() { return id; }
	    public String getName() { return name; }
	    public Map<String, Object> getData() { return data; }
	    public String getCreatedAt() { return createdAt; }
}
