package commandline;

import java.sql.*;
/**
 * This class is called by the controller when a game is finished, so it can store data.
 * Or when the user wants to display the game statistics, The database executes queries and returns values.
 * @param args
 */
public class Database {
	private Connection connection = null;
	private int NewGame; 	
	
	/**
	 * Private integers genereted from the game.
 	 * @param args
	 */
	
	private int draws = 17;
	private int TotalRounds = 62;
	private int p1Rounds = 15;
	private int p2Rounds = 10;
	private int p3Rounds = 10;
	private int p4Rounds = 5;
	private int p5Rounds = 5;
	private int winner = 1;
	
	/**
	 * Connected to the database.
 	 * @param args
	 */
	public void connect() {
		String dbname = "postgres";
		String username = "postgres";
		String password = "skata";

		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, username, password);
		} catch (SQLException e) {
			System.err.println("Connection Failed!");
			e.printStackTrace();
			return;
		}
		if (connection != null) {
			System.out.println("Connection successful");
		} else {
			System.err.println("Failed to make connection!");
		}

	}
	
	/**
	 * Disconnect to the database.
 	 * @param args
	 */
	public void disconnect() {
		try {
			connection.close();
			System.out.println("Connection closed");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection could not be closed – SQLexception");
		}

	}
	
	/**
	 * This method is called when a game is over , so it can add the results of a game to the database.
 	 * @param args
	 */
	public void InsertData() {
		

		Statement stmtid = null;
		String queryid = "select max(game_id) as max from toptrumps.game";

		//Make a new game id.

		try {
			stmtid = connection.createStatement();
			ResultSet rsid = stmtid.executeQuery(queryid);
			while (rsid.next()) {

				NewGame = rsid.getInt("max") + 1;

			}

			String query2 = "INSERT INTO toptrumps.Game VALUES (" + NewGame + "," + TotalRounds + "," + draws + ")";
			// executing the insert command
			PreparedStatement pstmt = connection.prepareStatement(query2);
			pstmt.executeUpdate();

			String query3 = "INSERT INTO toptrumps.Results VALUES (" + NewGame + "," + winner + "," + p1Rounds + ","
					+ p2Rounds + "," + p3Rounds + "," + p4Rounds + "," + p5Rounds + ")";
			// executing the insert command
			PreparedStatement pstmt2 = connection.prepareStatement(query3);
			pstmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query ");

		}

	}
	/**
	 * This method returns the Number of games played overall.
 	 * @param args
	 */
	public int GamesPlayed() {
		Statement stmtid = null;
		String queryid = "select count(game_id) as games_played from toptrumps.results";

		int GameCount = 0;
		try {
			stmtid = connection.createStatement();
			ResultSet rsid = stmtid.executeQuery(queryid);
			while (rsid.next()) {

				GameCount = rsid.getInt("games_played");

			}
			System.out.println(GameCount + " Games were played in total");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query ");

		}
		return GameCount;

	}
	
	/**
	 * This method returns how many times the computer has won.
 	 * @param args
	 */
	public int ComputerWins() {
		Statement stmtid = null;
		String queryid = "select count(game_winner) as computer_wins from toptrumps.results  inner join toptrumps.players ON results.game_winner = players.player_id Where type = 'computer'";

		int ComputerWins = 0;
		try {
			stmtid = connection.createStatement();
			ResultSet rsid = stmtid.executeQuery(queryid);
			while (rsid.next()) {

				ComputerWins = rsid.getInt("computer_wins");

			}
			System.out.println("The computer(s) won " + ComputerWins + " games in total.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query ");

		}
		return ComputerWins;

	}
	
	/**
	 * This method returns how many times the human has won.
 	 * @param args
	 */
	public int HumanWins() {
		Statement stmtid = null;
		String queryid = "select count(game_winner) as Human_wins from toptrumps.results  inner join toptrumps.players ON results.game_winner = players.player_id Where type = 'human'";

		int HumanWins = 0;
		try {
			stmtid = connection.createStatement();
			ResultSet rsid = stmtid.executeQuery(queryid);
			while (rsid.next()) {

				HumanWins = rsid.getInt("Human_wins");

			}
			System.out.println("The Human player won " + HumanWins + " games in total.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query ");

		}
		return HumanWins;

	}
	
	/**
	 * This method returns the average number of draws.
 	 * @param args
	 */
	public double AverageDraws() {

		Statement stmtid = null;
		String queryid = "SELECT\r\n" + " to_char(\r\n" + " AVG (draws),\r\n" + " '99999999999999999D99'\r\n"
				+ " ) AS average_draws\r\n" + "FROM\r\n" + " toptrumps.game;";

		double AverageDraws = 0;
		try {
			stmtid = connection.createStatement();
			ResultSet rsid = stmtid.executeQuery(queryid);
			while (rsid.next()) {

				AverageDraws = rsid.getDouble("average_draws");

			}
			System.out.println("The average number of draws is " + AverageDraws);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query ");

		}
		return AverageDraws;
	}
	
	/**
	 * This main method returns the largest number of rounds played in a single game.
 	 * @param args
	 */
	
	public int MaxRounds() {
		Statement stmtid = null;
		String queryid = "select  max(rounds) as max_rounds from toptrumps.game ";

		int max = 0;
		try {
			stmtid = connection.createStatement();
			ResultSet rsid = stmtid.executeQuery(queryid);
			while (rsid.next()) {

				max = rsid.getInt("max_rounds");

			}
			System.out.println("The largest number of rounds played in a single game is " + max);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query ");

		}
		return max;
	}

	public static void main(String[] args) {
		Database a = new Database();
		
		a.connect();
		System.out.println(a.GamesPlayed() + " Games were played in total");
		System.out.println("The Human player won " + a.HumanWins() + " games in total.");
		System.out.println("The computer(s) won " + a.ComputerWins() + " games in total.");
		System.out.println("The average number of draws is " + a.AverageDraws());
		System.out.println("The largest number of rounds played in a single game is " + a.MaxRounds());
		a.disconnect();
	}
}
