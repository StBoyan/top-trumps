package online.dwResources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import commandline.models.Game;
import commandline.models.Database;
import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;



@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 *
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {

    private Game game;
    private int NUMBER_OF_PLAYERS;
    /**
     * A Jackson Object writer. It allows us to turn Java objects
     * into JSON strings easily.
     */
    private ObjectWriter oWriter;
    private String deckFile;

    /**
     * Contructor method for the REST API. This is called first. It provides
     * a TopTrumpsJSONConfiguration from which you can get the location of
     * the deck file and the number of AI players.
     *
     * @param conf
     */
    public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
        oWriter = new ObjectMapper().writer();
        try {
            deckFile = conf.getDeckFile();
            NUMBER_OF_PLAYERS = conf.getNumAIPlayers() + 1;
            game = new Game(NUMBER_OF_PLAYERS, conf.getDeckFile());


        } catch (FileNotFoundException exception) {
            //TODO
        }
        // ----------------------------------------------------
        // Add relevant initalization here
        // ----------------------------------------------------
    }

    // ----------------------------------------------------
    // Add relevant API methods here
    // ----------------------------------------------------

    @GET
    @Path("/helloJSONList")
    /**
     * Here is an example of a simple REST get request that returns a String.
     * We also illustrate here how we can convert Java objects to JSON strings.
     * @return - List of words as JSON
     * @throws IOException
     */
    public String helloJSONList() throws IOException {

        List<String> listOfWords = new ArrayList<String>();
        listOfWords.add("Hello");
        listOfWords.add("World!");


        // We can turn arbatory Java objects directly into JSON strings using
        // Jackson seralization, assuming that the Java objects are not too complex.
        String listAsJSONString = oWriter.writeValueAsString(listOfWords);

        return listAsJSONString;
    }

    @GET
    @Path("/helloWord")
    /**
     * Here is an example of how to read parameters provided in an HTML Get request.
     * @param Word - A word
     * @return - A String
     * @throws IOException
     */
    public String helloWord(@QueryParam("Word") String Word) throws IOException {
        return "Hello " + Word;
    }

    @GET
    @Path("/start")

	/** Creates a new game **/
    public Game startGame() {
        if (game.getRoundsPlayed() == 0) {
            game.dealCards();
        }
        return game;
    }

    @GET
    @Path("/restart")
	/** Restarts the game **/
    public Game restart() throws FileNotFoundException {
        game = new Game(NUMBER_OF_PLAYERS, deckFile);
        game.dealCards();
        return game;
    }

    @GET
    @Path("/play")
	/** Handles a single round
	 * if it is human's turn it accepts the category passed,
	 * unless the human player has been eliminated
	 * else it takes an AI's chosen category and plays the round;
	 * Accordingly detects the round winner and informs if a player has been eliminated
	 * **/
    public Game playRoundWithCategory(@QueryParam("category") int category) {
        int roundCategory;
        if (game.getActivePlayer() == 0) {
            roundCategory = category;
        } else {
            roundCategory = game.chooseCategoryAI();
        }

        int rndWinner = game.playRound(roundCategory);

        if (rndWinner != -1) {
            game.winnerTakeCards(rndWinner);
        }

        game.removeEliminatedPlayers();

        //If game has finished write results to database
        if (game.isFinished()) {
            Database db = new Database();
            db.connect();
            db.insertData(game.getRoundsPlayed(), game.getDrawStat(), game.getActivePlayer(), game.getPlayerWonRoundStat());
            db.disconnect();
        }

        return game;
    }

    @GET
    @Path("/game")
	/** Initialises a new game **/
    public Game game() {
        return game;
    }

    @GET
    @Path("/categoryValues")
	/** Converts the array of categories into a JSON string **/
    public String categoryValues() throws JsonProcessingException {
        String categories = oWriter.writeValueAsString(game.getHumanPlayerCard());
        return categories;
    }
    @GET
    @Path("/database")
	/** Converts the array of categories into a JSON string **/
    public String databaseValues() throws JsonProcessingException {
    	Database a = new Database();
    	ArrayList<Integer> Values = new ArrayList<Integer>();
		a.connect();
		Map<String, String> gameStats = new HashMap<String , String>();
        gameStats.put("gamesPlayed", ""+a.getGamesPlayed());
        gameStats.put("HumanWins", ""+a.getHumanWins() );
        gameStats.put("ComputerWins", ""+a.getComputerWins());
        gameStats.put("AverageDraws", ""+a.getAverageDraws());
        gameStats.put("MaxRounds", ""+a.getMaxRounds());
        a.disconnect();

        // Convert a Map into JSON string.
        Gson gson = new Gson();
        String json = gson.toJson(gameStats);

        return json;
    }

}
