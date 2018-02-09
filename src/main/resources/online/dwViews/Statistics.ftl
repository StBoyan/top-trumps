
<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps</title>
    	  <meta charset="utf-8">
           <meta name="viewport" content="width=device-width, initial-scale=1">
           <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
           <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
           <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
           <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    	    <link rel="stylesheet" href="navbar.css" type"text/css"
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">
    	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

	</head>


    //<body background="/content/images/background.jpg" id="bg" alt="Background Image" onload="initalize()"> <!-- Call the initalize method when the page loads -->
	<body onload="initialize();">
     <nav class="navbar navbar-expand-sm bg-info navbar-dark">
       <ul class="navbar-nav">
         <li class="nav-item active">
           <a class="nav-link" href="http://localhost:7777/toptrumps/">Home</a>
         </li>
         <li class="nav-item">
           <a class="nav-link" href="http://localhost:7777/toptrumps/game">Play New Game</a>
         </li>
       </ul>
     </nav>


         <div class="container">
<div class="justify-content-center">
            <br></br>
            <br></br>
            <br></br>
            <br></br>
           <center> <h1 style="color:white;">Previous Games </h1></center>
			<table class="table table-striped stats-table">
			  <tbody bgcolor="ffffff">
			    <tr>
			      <th scope="row">Games Played</th>
			      <td id="games">0</td>
			    </tr>
			    <tr>
			      <th scope="row">Human Wins</th>
			      <td id="human_wins">0</td>
			    </tr>
			    <tr>
			      <th scope="row">Computer Wins</th>
			      <td id="computer_wins">0</td>
			    </tr>
			    <tr>
			      <th scope="row">Average Draws</th>
			      <td id="average_draws">0</td>
			    </tr>
			    <tr>
			      <th scope="row">Maximum Rounds Played</th>
			      <td id="max_rounds">0</td>
			    </tr>
			  </tbody>
			</table>
		</div>

		<br />






		
		<script type="text/javascript">

		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
			// Method that is called on page load
			

			// -----------------------------------------
			// Add your other Javascript methods Here

			        function newGame() {
             window.location='http://localhost:7777/toptrumps/game';
                       			}
		
			// This is a reusable method for creating a CORS request. Do not edit this.
			function createCORSRequest(method,url) {
  				var xhr = new XMLHttpRequest();
  				if ("withCredentials" in xhr) {

    				// Check if the XMLHttpRequest object has a "withCredentials" property.
    				// "withCredentials" only exists on XMLHTTPRequest2 objects.
    				xhr.open(method, url, true);

  				} else if (typeof XDomainRequest != "undefined") {

    				// Otherwise, check if XDomainRequest.
    				// XDomainRequest only exists in IE, and is IE's way of making CORS requests.
    				xhr = new XDomainRequest();
    				xhr.open(method, url);

 				 } else {

    				// Otherwise, CORS is not supported by the browser.
    				xhr = null;

  				 }
  				 return xhr;
			}
		
		</script>
		
		<!-- Here are examples of how to call REST API Methods -->

		<script type="text/javascript">

        			// Method that is called on page load
        			function initalize() {
        				Database();

        			}

        			// -----------------------------------------
        			// Add your other Javascript methods Here
        			// -----------------------------------------
        			function newGame() {
        				window.location='http://localhost:7777/toptrumps/game';
        			}
        			function createCORSRequest(method, url) {
        				var xhr = new XMLHttpRequest();
        				if ("withCredentials" in xhr) {
        					// Check if the XMLHttpRequest object has a "withCredentials" property.
        					// "withCredentials" only exists on XMLHTTPRequest2 objects.
        					xhr.open(method, url, true);
        				} else if (typeof XDomainRequest != "undefined") {
        					// Otherwise, check if XDomainRequest.
        					// XDomainRequest only exists in IE, and is IE's way of making CORS requests.
        					xhr = new XDomainRequest();
        					xhr.open(method, url);
        				} else {
        					// Otherwise, CORS is not supported by the browser.
        					xhr = null;
        				}
        				return xhr;
        			}
        		</script>
					<script type="text/javascript">
					$(function(){
				
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/database"); // Request type and URL+parameters
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					var jsonObject = JSON.parse(responseText);
					$("#games").html(jsonObject["gamesPlayed"]);
					$("#computer_wins").html(jsonObject["ComputerWins"]);
					$("#human_wins").html(jsonObject["HumanWins"]);
					$("#max_rounds").html(jsonObject["MaxRounds"]);
					$("#average_draws").html(jsonObject["AverageDraws"]);
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();	
				
				
				
			});
					
			
						
						
						
					</script>
        		

        	</body>
        	</html>