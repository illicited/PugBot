package core;

import java.sql.*;

public class Database {
	
	private static Connection conn = null;
	
	public Database(){
		try{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:app_data/test.db");
			createTables();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void createTables(){
		try{
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "DiscordServer("
					+ "id INTEGER NOT NULL, "
					+ "name VARCHAR(50) NOT NULL, "
					+ "PRIMARY KEY (id)"
					+ ")");
			
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Player("
					+ "id INTEGER NOT NULL, "
					+ "name VARCHAR(50) NOT NULL, "
					+ "PRIMARY KEY (id)"
					+ ")");
			
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Queue("
					+ "name VARCHAR(50) NOT NULL, "
					+ "serverId INTEGER NOT NULL, "
					+ "PRIMARY KEY (name, serverId), "
					+ "FOREIGN KEY (serverId) REFERENCES Server(id)"
					+ ")");
			
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "Game("
					+ "timestamp INTEGER NOT NULL, "
					+ "queueName VARCHAR(50) NOT NULL, "
					+ "serverId INTEGER NOT NULL, "
					+ "PRIMARY KEY (timestamp, queueName, serverId), "
					+ "FOREIGN KEY (serverId) REFERENCES Queue(serverId)"
					+ ")");
			
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
					+ "PlayerGame("
					+ "playerId INTEGER NOT NULL, "
					+ "timestamp INTEGER NOT NULL, "
					+ "serverId INTEGER NOT NULL, "
					+ "pickOrder INTEGER, "
					+ "captain INTEGER DEFAULT 0, "
					+ "PRIMARY KEY (playerId, timestamp, serverId), "
					+ "FOREIGN KEY (playerId) REFERENCES Player(id), "
					+ "FOREIGN KEY (serverId) REFERENCES Game(serverId), "
					+ "FOREIGN KEY (timestamp) REFERENCES Game(timestamp)"
					+ ")");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void insertDiscordServer(Integer id, String name){
		try{
			PreparedStatement pStatement = conn.prepareStatement("INSERT OR IGNORE INTO DiscordServer VALUES(?, ?)");
			pStatement.setInt(1, id);
			pStatement.setString(2, name);
			
			pStatement.execute();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void insertPlayer(Integer id, String name){
		try{
			PreparedStatement pStatement = conn.prepareStatement("INSERT OR IGNORE INTO Player VALUES(?, ?)");
			pStatement.setInt(1, id);
			pStatement.setString(2, name);
			
			pStatement.execute();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void insertQueue(Integer serverId, String Name){
		try{
			PreparedStatement pStatement = conn.prepareStatement("INSERT OR IGNORE INTO Queue VALUES(?, ?)");
			pStatement.setString(1, Name);
			pStatement.setInt(2, serverId);
			
			pStatement.execute();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void insertGame(Long timestamp, String queueName, Integer serverId){
		try{
			PreparedStatement pStatement = conn.prepareStatement("INSERT OR IGNORE INTO Game VALUES(?, ?, ?)");
			pStatement.setLong(1, timestamp);
			pStatement.setString(2, queueName);
			pStatement.setInt(3, serverId);
			
			pStatement.execute();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void insertPlayerGame(Integer playerId, Long timestamp, Integer serverId){
		try{
			PreparedStatement pStatement = conn.prepareStatement("INSERT OR IGNORE INTO PlayerGame (playerId, timestamp, serverId) VALUES(?, ?, ?)");
			pStatement.setInt(1, playerId);
			pStatement.setLong(2, timestamp);
			pStatement.setInt(3, serverId);
			
			pStatement.execute();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void updatePlayerGamePickOrder(Integer playerId, Long timestamp, Integer serverId, Integer pickOrder){
		try{
			PreparedStatement pStatement = conn.prepareStatement("UPDATE PlayerGame SET pickOrder = ? "
					+ "WHERE playerId = ? AND timestamp = ? AND serverId = ?");
			pStatement.setInt(1, pickOrder);
			pStatement.setInt(2, playerId);
			pStatement.setLong(3, timestamp);
			pStatement.setInt(4, serverId);
			
			pStatement.execute();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void updatePlayerGameCaptain(Integer playerId, Long timestamp, Integer serverId, boolean captain){
		Integer captainInt = 0;
		if(captain){
			captainInt = 1;
		}
		try{
			PreparedStatement pStatement = conn.prepareStatement("UPDATE PlayerGame SET captain = ? "
					+ "WHERE playerId = ? AND timestamp = ? AND serverId = ?");
			
			pStatement.setInt(1, captainInt);
			pStatement.setInt(2, playerId);
			pStatement.setLong(3, timestamp);
			pStatement.setInt(4, serverId);
			
			pStatement.execute();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
}
