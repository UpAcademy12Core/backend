package pt.upacademy.coreFinalProject.models;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Entity_ implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	
	public enum Role {
		ADMIN, SUPERUSER, USER
	};

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}
	
	
	

}
