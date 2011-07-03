package com.vta.gtrack.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries ( {
		@NamedQuery(name="VtaBusInfo.getBusForRoute",query="SELECT a from VtaBusInfo a where a.status != 'Complete' and  a.routeId  = :routeId  and a.dbTimeStamp = (select max(dbTimeStamp) from VtaBusInfo b where b.routeId = a.routeId and b.busId = a.busId )")
})
@Table(name = "VtaBusInfo")
public class VtaBusInfo implements Serializable {
	private static final long serialVersionUID = -2580765606121034L;
	public enum Status
	{In_Route,At_Stop,Complete,Out_Of_Service};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;
	

	@Column(nullable = false, length = 32)
	protected String busId;
	
	@Column(nullable = false, length = 6)
	protected String routeId;
	
	@Column(nullable = false)
	protected double latitude ;
	
	@Column(nullable = false)
	protected double longitude ;
	
	@Column(nullable = false, length = 20)
	protected String status;
	
	@Column(nullable = false)
	protected int passCount;
	
	@Column(nullable =false)
	protected String lastUpdtTime;
	
	@Column(nullable =false)
	protected Date dbTimeStamp;
	
	@Column(nullable =false)
	protected String stop_Name;
	
	public String getStop_Name() {
		return stop_Name;
	}
	public void setStop_Name(String stop_Name) {
		this.stop_Name = stop_Name;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getOperationalCode() {
		return operationalCode;
	}
	public void setOperationalCode(String operationalCode) {
		this.operationalCode = operationalCode;
	}


	@Column(nullable =false)
	protected String driverId;
	
	@Column(nullable =false)
	protected String operationalCode;
	
	public String getBusId() {
		return busId;
	}	
	public void setBusId(String bid) {
		this.busId = bid;
	}
	
	public String getRouteId() {
		return routeId;
	}	
	public void setRouteId(String rid) {
		this.routeId = rid;
	}
	public double getLatitude() {
		return latitude;
	}	
	public void setLatitude(float lat) {
		this.latitude = lat;
	}
	
	public double getLongiitude() {
		return longitude;
	}	
	public void setLongitude(float lon) {
		this.longitude = lon;
	}
	
	public String getStatus() {
		return status;
	}	
	public void setStatus(String sts) {
		this.status = sts.toString();
	}
	
	public int getPassCount() {
		return passCount;
	}	
	public void setPassCount(int pct) {
		this.passCount = pct;
	}
	
	
	public String getLastUpdtTime(){
		return this.lastUpdtTime;
	}
	public void setLastUpdtTime(String lut){
		this.lastUpdtTime = lut;
	}

	
	public VtaBusInfo()
	{
	
	}


	public VtaBusInfo(String bid,String rid, double lon, double lat, String sts, int pct, String dat, String stp_name, String driver_Id, String op_code)
	{
	this.busId=bid;
	this.routeId=rid;
	this.longitude=lon;
	this.latitude=lat;
	this.status=sts;
	this.passCount=pct;			
	this.lastUpdtTime=dat;			
	this.dbTimeStamp = new Date();
	this.stop_Name=stp_name;
	this.driverId=driver_Id;	
	this.operationalCode=op_code;
	}
}
