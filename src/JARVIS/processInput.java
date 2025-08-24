package JARVIS;

public class processInput extends ok,openURL{

	 private void processInput(String input) {
	    	saveInputToDatabase(input);
	    	 displayOutput("Opening the site .. . . .");
	        switch (input.toLowerCase()) {
	            case "open my webapp":
	                openURL("https://bit.ly/stunystew");
	                break;
	            case "write a note":
	            case "notepad":
	                openNotepad();
	                break;
	            case "youtube":
	                openURL("https://youtube.com");
	                break;
	            case "javanotes":
	                openURL("https://java.antosh.in/"
	                		+ "");
	                break;
	            case "instagram":
	                openURL("https://instagram.com");
	                break;
	            case "github":
	                openURL("https://github.com");
	                break;
	            case "music":
	                openURL("https://youtu.be/dZqmlSGmlC4?si=5sT4DWsDhxkv8vtD");
	                break;
	            case "hi":
	            case "hello":
	                displayOutput("Hello, " + getUserInput() + "! How can I help you?");
	                break;
	            case "sveri":
	            case "college website":
	            	openURL("https://coe.sveri.ac.in/");
	            	break;
	            case "google":
	                openURL("https://google.com");
	                break;
	            case "facebook":
	                openURL("https://facebook.com");
	                break;
	            case "twitter":
	                openURL("https://twitter.com");
	                break;
	            case "wikipedia":
	                openURL("https://wikipedia.org");
	                break;
	            case "linkedin":
	                openURL("https://linkedin.com");
	                break;
	            case "reddit":
	                openURL("https://reddit.com");
	                break;
	            case "amazon":
	                openURL("https://amazon.com");
	                break;
	            case "whatsapp":
	                openURL("https://whatsapp.com");
	                break;
	            case "netflix":
	                openURL("https://netflix.com");
	                break;
	            case "pinterest":
	                openURL("https://pinterest.com");
	                break;
	            case "quora":
	                openURL("https://quora.com");
	                break;
	            case "duckduckgo":
	                openURL("https://duckduckgo.com");
	                break;
	            case "suggetion for code":
	            case "stackoverflow":
	                openURL("https://stackoverflow.com");
	                break;
	            case "medium":
	                openURL("https://medium.com");
	                break;
	            case "twitch":
	                openURL("https://twitch.tv");
	                break;
	            case "play music":
	            case "spotify":
	                openURL("https://spotify.com");
	                break;
	            case "imdb":
	                openURL("https://imdb.com");
	                break;
	            case "cnn":
	                openURL("https://cnn.com");
	                break;
	            case "bbc":
	                openURL("https://bbc.com");
	                break;
	            case "etsy":
	                openURL("https://etsy.com");
	                break;
	            case "hacker news":
	                openURL("https://news.ycombinator.com");
	                break;
	            case "ted":
	                openURL("https://ted.com");
	                break;
	            case "gmail":
	            case "write the email":
	                openURL("https://mail.google.com");
	                break;
	            case "yahoo":
	                openURL("https://yahoo.com");
	                break;
	            case "stack exchange":
	                openURL("https://stackexchange.com");
	                break;
	            case "adobe":
	                openURL("https://creativecloud.adobe.com");
	                break;
	            case "coursera":
	                openURL("https://coursera.org");
	                break;
	            case "quizlet":
	                openURL("https://quizlet.com");
	                break;
	            case "khan academy":
	                openURL("https://www.khanacademy.org");
	                break;
	            case "chegg":
	                openURL("https://www.chegg.com");
	                break;
	            case "duolingo":
	                openURL("https://www.duolingo.com");
	                break;
	            case "ted-ed":
	                openURL("https://ed.ted.com");
	                break;
	            case "mathway":
	                openURL("https://www.mathway.com");
	                break;
	            case "sparknotes":
	                openURL("https://www.sparknotes.com");
	                break;
	            case "grammarly":
	                openURL("https://www.grammarly.com");
	                break;
	            case "edx":
	                openURL("https://www.edx.org");
	                break;
	            case "memrise":
	                openURL("https://www.memrise.com");
	                break;
	            case "codecademy":
	                openURL("https://www.codecademy.com");
	                break;
	            case "wolfram alpha":
	                openURL("https://www.wolframalpha.com");
	                break;
	            case "quizizz":
	                openURL("https://www.quizizz.com");
	                break;
	            case "scratch":
	                openURL("https://scratch.mit.edu");
	                break;
	            case "notion":
	                openURL("https://www.notion.so");
	                break;
	            case "draw.io":
	                openURL("https://www.draw.io");
	                break;
	            case "researchgate":
	                openURL("https://www.researchgate.net");
	                break;
	            case "onenote":
	                openURL("https://www.onenote.com");
	                break;
	            case "prezi":
	                openURL("https://www.prezi.com");
	                break;
	            default:
	            if (checkWebsiteAvailability(input)) {
	                openURL(input);
	            } else {
	            	displayOutput("Site Opening Failed * * * * * *");
	                displayOutput("The website is not available.\n You anc enter the full address of website to open it. \n eg.https://www."+input+".com");
	            }
	                
	        }
	    }
	
}
