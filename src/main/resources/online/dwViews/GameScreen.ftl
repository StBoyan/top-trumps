 <!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>Top Trumps</title>
</head>
<body background="/content/images/background.jpg" id="bg" alt="Background Image">
<div class="container-fluid">
    <div class="row" id="firstRow"></div>
    <div class="row" id="secondRow"></div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script>
    var game;
    var gameStarted;

    $(document).ready(function () {
        startGame();
    });


//Initializes a new game and displays an error message if something in the game logic goes wrong
    function startGame() {
        $.ajax({
            type: "GET",
            url: "/toptrumps/restart", // will restart the game every time the page is loaded
            success: function (data) {
                game = data;
                console.log(game);
                startRoundLogic();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("Status: " + textStatus);
                alert("Error: " + errorThrown);
            }
        });
    }

// Handles each round
    function playRound() {
        $.ajax({
            type: "GET",
            url: "/toptrumps/play",
            success: function (data) {
                game = data;
                console.log(game);
                startRoundLogic();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("Status: " + textStatus);
                alert("Error: " + errorThrown);
            }
        });
    }

    function playRoundWithCategory(category) {
        $.ajax({
            type: "GET",
            url: "/toptrumps/play?" + category,
            success: function (data) {
                game = data;
                console.log(game);
                startRoundLogic();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("Status: " + textStatus);
                alert("Error: " + errorThrown);
            }
        });
    }


// Illustrates the game components in the game view
// if the game is started it revalidates the view
// then it illustrates the information for the round
// represented in the top left corner
// along with the cards of each player
    function drawGame() {
    // Revalidates the page view
        if (gameStarted) {
            emptyElements();
        }
        drawRound();
        let playerIndex = 0;
        for (player in game.players) {
            if (player != null) {
 //               drawHumanPlayer(game.players[player]);
                drawPlayer(game.players[player], playerIndex);
                playerIndex++;
            }
        }
        if (game.activePlayer == 0) {
            drawCategories();
        }
        gameStarted = true;
    }

// Illustrates the information for each round of the game
    function drawRound() {

        let html = '<div class="col-3 text-white">';
        html += '<p>Current round is:  ' + (game.roundsPlayed + 1) + '</p>';
        html += '<p>Active player:  ' + (game.activePlayer + 1) + '</p>';
        html += '<p>Communal Pile:  ' + (game.communalPile.length) + '</p>';
        if (game.communalPile.length > 0) {
            html += '<p>Cards in communal pile:</p>';
            for (card in game.communalPile){
                html += '<p>' + game.communalPile[card].description + '</p>';
            }
        }

        $("#firstRow").append(html);
    }

// Creates a player's card
    function drawPlayer(player, playerIndex) {
    try {
        let html = '<div class="card text-black bg-light m-3" style="width: 9rem;">' +
                '<div class="card-body">' +
                '<h5 class="card-title">Player: ' + (playerIndex + 1) + '</h5>' +
                '<p class="card-text">Cards left: ' + player.playerDeck.length + '</p>' +
                '<p class="card-text">You drew: ' + player.firstCard.description + '</p>' +
                '</div>' +
                '<ul class="list-group list-group-flush">';
        for (card in player.firstCard.catsValues) {
            html += '<li class="list-group-item">' + game.categoryLabels[card] + ': ' + player.firstCard.catsValues[card] + '</li>';
        }

        $("#firstRow").append(html);
        }
        catch (err){
        }

    }

// Creates a button group for each category, used for the human player to enter a category
    function drawCategories() {
        let html = '<div class="col align="middle" ">';

        html += '<div class="btn-group" role="group">';


        for (label in game.categoryLabels) {
            html += '<button type="button" class="btn btn-secondary" value="'+ label +'" onclick="playRoundWithCategory(this.value)">' + game.categoryLabels[label] + '</button>';
        }

        $("#secondRow").append(html);
    }


// Creates a message for informing the user with the category chosen (when AI's turn)
    function buildAIActionMessage() {
        let message = "Player: " + (game.activePlayer + 1) + " chose to play the round with category: " + getActivePlayerTopCattegory();
        return message;
    }

// Returns an AI's highest category
    function getActivePlayerTopCattegory() {
        return game.categoryLabels[game.players[game.activePlayer].bestCategory];
    }

// Revalidates the page view
    function emptyElements() {
        $("#firstRow").empty();
        $("#secondRow").empty();
    }

// Maintains user communication throughout each round
    function startRoundLogic() {
        if (game != null && game.players[0] != null) {
            drawGame();
            if (game.activePlayer != 0) { // if it is an AI's turn
                let alertMessage = buildAIActionMessage();
                setTimeout(function () { // delays the response to simulate a thinking process
                    alert(alertMessage);
                    playRound();
                }, 2000);
            }
            if (game.roundsPlayed > 0) {
                if (game.topTrumpsRound.roundWinner == 0) {
                    alert("You won this round! It is your turn to choose a category!")
                } else {
                    alert("Player " + (game.topTrumpsRound.roundWinner + 1) + " won this round!")
                }
            }
        } else {
            alert ("You lost!");
        }
    }
     function drawHumanPlayer(player){
     let html = '<div class="card text-black bg-light m-3" style="width: 9rem;">' +
                     '<div class="card-body">' +
                     '<h5 class="card-title">Player: 1' + '</h5>' +
                     '<p class="card-text">Cards left: ' + player.playerDeck.length + '</p>' +
                     '<p class="card-text">You drew: ' + player.firstCard.description + '</p>' +
                     '</div>' +
                     '<ul class="list-group list-group-flush">';
             for (card in player.firstCard.catsValues) {
                 html += '<li class="list-group-item">' + game.categoryLabels[card] + ': ' + player.firstCard.catsValues[card] + '</li>';
             }

             $("#firstRow").append(html);
     }
</script>
</body>
</html>