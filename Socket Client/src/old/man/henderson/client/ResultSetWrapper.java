package old.man.henderson.client;

import java.sql.ResultSet;

public class ResultSetWrapper implements  java.io.Serializable {
	public ResultSet rs;
	public ResultSetWrapper(ResultSet rs){
		this.rs = rs;
	}
}
