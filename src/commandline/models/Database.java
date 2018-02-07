package commandline.models;

import java.sql.*;
/**
 * This class maintains the connection to a database where the game's persistent data
 * is stored. It has methods to retrieve and store information from the database.
 */
public class Database {
    private Connection connection;
    /* Denotes the game_id primary key */
    private int newGame;

    /**
     * Creates a Database object.
     */
    public Database() {
    connection = null;
    }

    /**
     * Establishes connection to the database.
     */
    public void connect() {
//        String dbName = "m_17_2353687g";
//        String username = "m_17_2353687g";
//        String password = "2353687g";
        String dbName = "postgres";
        String username = "postgres";
        String password = "postgres";

        try {
//            connection = DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/" + dbName, username, password);
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, username, password);
        } catch (SQLException e) {
            System.err.println("Connection to database failed!");
            e.printStackTrace();
        }

        if (connection == null)
            System.err.println("Failed to make connection to database!");
    }

    /**
     * Disconnects from the database.
     */
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Connection to database could not be closed!");
        }
    }

    /**
     * Inserts data from a game of Top Trumps into the database. First,
     * a game ID calculated and then the information is inserted under the
     * appropriate game ID.
     * @param totalRounds number of rounds played
     * @param draws number of draws
     * @param winner winner of the game
     * @param rndWins rounds won by each player
     */
    public void insertData(int totalRounds, int draws, int winner, int[] rndWins) {
        Statement stmtId = null;
        String queryId = "select max(game_id) as max from toptrumps.game";

        /* Retrieves the next game ID to be used */
        try {
            stmtId = connection.createStatement();
            ResultSet rsid = stmtId.executeQuery(queryId);
            while (rsid.next()) {
                newGame = rsid.getInt("max") + 1;
            }

            /* Updates game statistics table */
            String query2 = "INSERT INTO toptrumps.Game VALUES (" + newGame + "," + totalRounds + "," + draws + ")";
            PreparedStatement pstmt = connection.prepareStatement(query2);
            pstmt.executeUpdate();

            /* Updates player statistics table */
            String query3 = "INSERT INTO toptrumps.Results VALUES (" + newGame + "," + (winner + 1) + "," + rndWins[0] + ","
                    + rndWins[1] + "," + rndWins[2] + "," + rndWins[3] + "," + rndWins[4] + ")";
            PreparedStatement pstmt2 = connection.prepareStatement(query3);                     //TODO MAKE IT WORK WITH LESS THAN 5 PEOPLE
            pstmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the total number of games played from the
     * database.
     * @return int number of games played
     */
    public int getGamesPlayed() {
        Statement stmt = null;
        String query = "select count(game_id) as games_played from toptrumps.results";

        int gameCount = 0;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                gameCount = rs.getInt("games_played");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameCount;
    }

    /**
     * Retrieves the number of times the computer-controlled players
     * have won from the database.
     * @return int number of computer wins
     */
    public int getComputerWins() {
        Statement stmt = null;
        String query = "select count(game_winner) as computer_wins from toptrumps.results  inner join toptrumps.players ON results.game_winner = players.player_id Where type = 'computer'";

        int computerWins = 0;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                computerWins = rs.getInt("computer_wins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return computerWins;
    }

    /**
     * Retrieves the number of times the human player has won from
     * the database..
     * @return int number of human wins
     */
    public int getHumanWins() {
        Statement stmt = null;
        String query = "select count(game_winner) as Human_wins from toptrumps.results  inner join toptrumps.players ON results.game_winner = players.player_id Where type = 'human'";

        int humanWins = 0;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                humanWins = rs.getInt("Human_wins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return humanWins;
    }

    /**
     * Retrieves the average number of draws in a game from the
     * database.
     * @return double average number of draws
     */
    public double getAverageDraws() {
        Statement stmt = null;
        String query = "SELECT\r\n" + " to_char(\r\n" + " AVG (draws),\r\n" + " '99999999999999999D99'\r\n"
                + " ) AS average_draws\r\n" + "FROM\r\n" + " toptrumps.game;";

        double averageDraws = 0;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                averageDraws = rs.getDouble("average_draws");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return averageDraws;
    }

    /**
     * Retrieves the maximum number of rounds played in a game
     * from the database.
     * @return int max number of rounds
     */
    public int getMaxRounds() {
        Statement stmt = null;
        String query = "select max(rounds) as max_rounds from toptrumps.game ";

        int maxRounds = 0;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                maxRounds = rs.getInt("max_rounds");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxRounds;
    }
}