package net.thumbtack.buscompany.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "client")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Client extends User {
	private String email;
	private String phone;

	public Client(int id, String firstName, String lastName, String patronymic,
				 UserType userType, String login, String password, byte[] salt, String email, String phone) {
		super(id, firstName, lastName, patronymic, userType, login, password, salt);
		this.email = email;
		this.phone = phone;
	}
}
