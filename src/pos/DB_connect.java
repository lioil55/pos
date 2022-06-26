package pos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_connect {
	private static final String dbDriver = "com.mysql.cj.jdbc.Driver";
	private static final String dbURL = "jdbc:mysql://localhost:3306/pos?servertimezone=UTC&useUnicode=true&characterEncoding=UTF8";
	private static final String id = "root";
	private static final String pw = "1234";
	private static Connection conn = null;
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		connect();
		 Thread.sleep(2000);
		close();	
	}
	public static Connection connect() {
		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbURL, id, pw);
			
			if(conn != null)
				System.out.println("db 연결 성공!");
			else
				System.out.println("db 연결 실패!");
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
		
	}
	
public static void close() {
	
		try {
			if(conn != null) {
				conn.close();
				System.out.println("db 연결 해제!");
			}
		}catch(Exception e) {
			System.out.println("db close() 에러");
		}
		
	}

}
