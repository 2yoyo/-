package com.qf.entity;

public class GoodsType {

	private Integer id;
	
	// ${g.gname}-->g.getGanme();
	private String gname;
	
	private Integer gparentid;
	
	private String gpic;
	
	private String gparentname; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public Integer getGparentid() {
		return gparentid;
	}

	public void setGparentid(Integer gparentid) {
		this.gparentid = gparentid;
	}

	public String getGpic() {
		return gpic;
	}

	public void setGpic(String gpic) {
		this.gpic = gpic;
	}

	@Override
	public String toString() {
		return "GoodsType [id=" + id + ", gname=" + gname + ", gparentid=" + gparentid + ", gpic=" + gpic + "]";
	}

	public String getGparentname() {
		return gparentname;
	}

	public void setGparentname(String gparentname) {
		this.gparentname = gparentname;
	}
	
	
	
}
