package xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DB {
	private String name;
	private String connection;
	private String user;
	private String password;
}
