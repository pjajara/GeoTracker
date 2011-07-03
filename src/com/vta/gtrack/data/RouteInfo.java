package com.vta.gtrack.data;

import java.io.Serializable;
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
	@NamedQuery(name="RouteInfo.getCoordinates",query="SELECT r from RouteInfo r where r.route_id =:routeId)")
	})
@Table(name = "RouteInfo")
public class RouteInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, length = 32)
	protected String route_id;

	@Column(nullable = false, length = 32)
	protected String loc_name;
	
	@Column(nullable = false)
	protected float longitude ;
	
	@Column(nullable = false)
	protected float latitude ;
	
		
	public void setRouteID(String id){
		this.route_id = id;	
	}
	public String getRouteID(){
		return this.route_id;
	}
	
	public void setLoc_name(String loc_name){
		this.loc_name = loc_name;
	}
	public String getLoc_name(){
		return this.loc_name;
	}
	
	public void setLongitude(float longitude){
		this.longitude = longitude;
	}
	
	public float getLongitude(){
		return this.longitude;
	}
	
	public void setLatitude(float latitude){
		this.latitude = latitude;
	}
	
	public float getLatitude(){
		return this.latitude;
	}

	
	public RouteInfo()
	{
	
	}

	public RouteInfo(String route_id, String loc_name, float latitude, float longitude){
			setRouteID(route_id);
			setLoc_name(loc_name);
			setLongitude(longitude);
			setLatitude(latitude);
							
	}
}
