package LapFarm.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity {

	@Id
	@Column(name = "Id", nullable = false)
	private int id;

	@Column(name = "Role", nullable = false, length = 20)
	private String role;

	// Constructors
	public RoleEntity() {
	}

	public RoleEntity(int id, String role) {
		this.id = id;
		this.role = role;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "RoleEntity{" + "id=" + id + ", role='" + role + '\'' + '}';
	}
}
