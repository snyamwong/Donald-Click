package application;
	
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class Main extends Application {
	
	private int updateX;
	private int updateY;
	private boolean mediumDifficulty;
	private boolean hardDifficulty;
	private boolean notMute = true;
	
	private final int height = 800;
	private final int width = 1150;
	
	private String bawkOrTrump;
	
	private Timeline timeline;
	
	private Random rand = new Random();
	
	private Image[] movingObjects = {new Image("trump.png"), new Image("WeirdChicken.png")};
	
	private ImageView trump;
	private ImageView muteImage = new ImageView(new Image("Volume.png"));
	
	private BackgroundImage backgroundImage = new BackgroundImage(new Image("background.jpg"),
			null, null, null, null);
	
	private Text timer = new Text(550, 50, "Time Left: 60");
	private Text title = new Text(400, 150, "Stump Trump");
	private Text adsLeft = new Text();
	private Text scoreCount = new Text();
	
	private BorderPane pane = new BorderPane();
	
	private Ads ad = new Ads();
	private Score score = new Score();
	private int timeOut;
	
	private Button back = new Button();
	
	private HashMap<String, AudioClip> someSound = new HashMap<>();
	
	private ArrayList<PriorityQueue<Integer>> highScores = new ArrayList<>();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		startMenu();
		mouseClickForMuteImage();
		
		title.setFont(new Font(FontSizes.titleSize));
		
		muteImage.setFitHeight(30);
		muteImage.setFitWidth(30);
		
		trump = new ImageView(movingObjects[0]);
		trump.setX(rand.nextInt(500) + 225);
		trump.setY(rand.nextInt(400) + 225);
		trump.setFitHeight(150);
		trump.setFitWidth(150);
		
		pane.setBackground(new Background(backgroundImage));
		
		for(int i = 0; i < 3; i++)
		{
			highScores.add(new PriorityQueue<Integer>(Collections.reverseOrder()));
		}
		
		someSound.put("Cheering", new AudioClip(new File("Cheering.mp3").toURI().toString()));
		someSound.put("Booing", new AudioClip(new File("Booing.mp3").toURI().toString()));
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(){
			   
    		public void handle(final ActionEvent e){
    			
    			if(hardDifficulty == true) {
    				timeOut++;
    				timer.setText("Time left: " + (int) Math.ceil((6000 - timeOut)/100.0));
    			}
    			if(timeOut == 6000 && ad.getValue() != 0 && hardDifficulty == true)
                {
    				pane.getChildren().remove(adsLeft);
    				pane.getChildren().remove(timer);
                	lost();
                }
    			
    			trump.setX(trump.getX() + updateX);
    			trump.setY(trump.getY() + updateY);
            	double posX = trump.getX();
        		double posY = trump.getY();
        		
                if(hardDifficulty == false){
	                if(posX >= 1025 || posX <= 175){
	                    updateX *= -1;
	                    if(mediumDifficulty == true)
	                    {
		                    trump.setX(rand.nextInt(750) + 175);
		    				trump.setY(rand.nextInt(400) + 175);
	                    }
	                }
	                
	                
	                if(posY >= 675 || posY <= 175) {
	                    updateY *= -1;
	                    if(mediumDifficulty == true)
	                    {
		                    trump.setX(rand.nextInt(750) + 175);
		    				trump.setY(rand.nextInt(400) + 175);
	                    }
	                }
	                
	                
                }
                else{
                	if(rand.nextInt(50) == 15){
                		updateX *= -1;
                	}
                	if(rand.nextInt(50) == 23){
                		updateY *= -1;
                	}
                	
                	
                	if(posX >= 1025 || posX <= 75) {
	                    if(rand.nextInt(20) > 12){
	                    	updateX *= -1;
	                    }
	                    else{
		                    trump.setX(rand.nextInt(750) + 175);
		    				trump.setY(rand.nextInt(400) + 175);
	                    }
	                }
                	
                	
	                if(posY >= 625 || posY <= 100) {
	                	if(rand.nextInt(20) > 12){
	                    	updateY *= -1;
	                    }
	                    else{
		                    trump.setX(rand.nextInt(750) + 175);
		    				trump.setY(rand.nextInt(400) + 175);
	                    }
	                }
	                
	                
                }
            }
    	};
		
		timeline = new Timeline(new KeyFrame(Duration.millis(10), event));
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		Scene scene = new Scene(pane, width, height);
		
		primaryStage.setTitle("STUMP TRUMP");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void startMenu(){
		VBox toStart = new VBox(10);
		toStart.setAlignment(Pos.CENTER);
		HBox selection = new HBox(10);
		selection.setAlignment(Pos.CENTER);
		HBox muteToTheRight = new HBox(10);
		muteToTheRight.setAlignment(Pos.TOP_RIGHT);
		
		Button pressStart = new Button("New Game");
		Button instructions = new Button("Instructions");
		Button options = new Button("Options");
		
		selection.getChildren().addAll(pressStart, instructions);
		
		if(title.isStrikethrough()){
			toStart.getChildren().add(new Text("Bawk bawk!"));
		}
		else{
			toStart.getChildren().add(new Text("Don't let Trump become president!"));
		}
		
		toStart.getChildren().addAll(selection, options);
		
		muteToTheRight.getChildren().add(muteImage);
		
		pane.setTop(muteToTheRight);
		pane.getChildren().add(title);
		pane.setCenter(toStart);

		pressStart.setOnAction(e -> {
			pane.getChildren().remove(title);
			pane.getChildren().remove(toStart);
			pane.getChildren().remove(muteToTheRight);
			difficultySetting();
		});
		
		instructions.setOnAction(e -> {
			pane.getChildren().remove(title);
			pane.getChildren().remove(toStart);
			pane.getChildren().remove(muteToTheRight);
			instructionsMenu();
		});
		
		options.setOnAction(e -> {
			pane.getChildren().remove(title);
			pane.getChildren().remove(toStart);
			pane.getChildren().remove(muteToTheRight);
			optionsMenu();
		});
	}
	
	public void removePaneEvent(){
		pane.setOnMouseClicked(e -> {
			
		});
	}
	
	public void mouseClickForMuteImage(){
		muteImage.setOnMouseClicked(e -> {
			if(notMute){
				notMute = false;
				muteImage.setImage((new Image("Mute.png")));
			}
			else{
				notMute = true;
				muteImage.setImage((new Image("Volume.png")));
			}
		});
	}
	
	public void mouseClickToPlay(){
		if(title.isStrikethrough()){
			bawkOrTrump = "Bawks";
		}
		else{
			bawkOrTrump = "Ads";
		}
		
		score.clearValue();
		
		adsLeft.setFont(Font.font("Times New Roman",FontWeight.BOLD, 40));
		scoreCount.setFont(Font.font("Times New Roman",FontWeight.BOLD, 40));
		
		scoreCount.setText("Score: " + score.getValue());
		adsLeft.setText("# of " + bawkOrTrump + " Remaining: " + ad.getValue());
		
		if(hardDifficulty == true)
		{
			pane.getChildren().add(timer);
		}
		pane.setTop(scoreCount);
		pane.setBottom(adsLeft);
		pane.getChildren().add(trump);
		
		pane.setOnMouseClicked(e -> {
			ad.clicked();
			
			for(String s: someSound.keySet())
			{	
				if(someSound.get(s).isPlaying())
				{
					someSound.get(s).stop();
				}
			}
						
			if(e.getX() >= trump.getX() && e.getX() <= trump.getX() + 150 
					&& e.getY() >= trump.getY() && e.getY() <= trump.getY() + 150)
			{
				score.clicked();
				scoreCount.setText("Score: " + score.getValue());
				trump.setX(rand.nextInt(800) + 140);
				trump.setY(rand.nextInt(450) + 140);
				if(notMute)
					someSound.get("Booing").play();
			}
			else
			{
				if(notMute)
					someSound.get("Cheering").play();
				trump.setX(rand.nextInt(800) + 140);
				trump.setY(rand.nextInt(450) + 140);
			}
			
			if(ad.getValue() == 0)
			{
				pane.getChildren().remove(adsLeft);
				pane.getChildren().remove(timer);
				lost();
			}
			
			adsLeft.setText("# of " + bawkOrTrump + " Remaining: " + ad.getValue());
		});
	}
	
	public void lost() {
		if(title.isStrikethrough()){
			bawkOrTrump = "Bawks";
		}
		else{
			bawkOrTrump = "Ads";
		}
		
		for(String s: someSound.keySet())
		{	
			if(someSound.get(s).isPlaying())
			{
				someSound.get(s).stop();
			}
		}
		
		pane.getChildren().remove(trump);
		
		Text[] highScoreList = {new Text(), new Text(), new Text()};
		highScoreList[0].setFont(Font.font(FontSizes.subLabels, FontSizes.subLazelSize));
		highScoreList[1].setFont(Font.font(FontSizes.subLabels, FontSizes.subLazelSize));
		highScoreList[2].setFont(Font.font(FontSizes.subLabels, FontSizes.subLazelSize));
		
		Text gameOver = new Text("Game Over!\n");
		gameOver.setFont(Font.font(FontSizes.labels, FontSizes.labelSize));
		
		Text highScoreTitle = new Text();
		highScoreTitle.setFont(Font.font(FontSizes.labels, FontSizes.labelSize));
		
		if(mediumDifficulty == true)
		{
			highScores.get(1).offer(score.getValue());
			createListOfHighScores(highScoreList, highScores.get(1));
			highScoreTitle.setText("Medium Difficulty High Score\n");
		}
		else if(hardDifficulty == true)
		{
			highScores.get(2).offer(score.getValue());
			createListOfHighScores(highScoreList, highScores.get(2));
			highScoreTitle.setText("Hard Difficulty High Score\n");
		}
		else 
		{
			highScores.get(0).offer(score.getValue());
			createListOfHighScores(highScoreList, highScores.get(0));
			highScoreTitle.setText("Easy Difficulty High Score\n");
		}
		
		TextFlow gameOverTitles = new TextFlow();
		gameOverTitles.setTextAlignment(TextAlignment.CENTER);
		gameOverTitles.getChildren().addAll(gameOver, highScoreTitle, 
				highScoreList[0], highScoreList[1], highScoreList[2]);
		
		VBox messagesAfterLosing = new VBox(10);
		messagesAfterLosing.setAlignment(Pos.CENTER);
		
		Text effectiveness = new Text("% of Effective " + bawkOrTrump + ": " + EffectiveAds.percent(score.getValue()) + "%");
		effectiveness.setFont(Font.font(FontSizes.subLabels, FontSizes.subLazelSize));
		
		messagesAfterLosing.getChildren().add(effectiveness);
		
		VBox withHighScore = new VBox(150);
		withHighScore.setAlignment(Pos.CENTER);
		
		if(score.getValue() == 0)
			messagesAfterLosing.getChildren().add(new Text("Git gud...."));
		
		back.setText("Start Menu");
		messagesAfterLosing.getChildren().add(back);
		
		if(score.getValue() == 20){
			ImageView maxPoints = new ImageView(new Image("20points.png"));
			maxPoints.setFitWidth(200);
			maxPoints.setFitHeight(200);
			
			withHighScore.setSpacing(50);
			withHighScore.getChildren().addAll(gameOverTitles, maxPoints, messagesAfterLosing);
		}
		else {
			withHighScore.setSpacing(150);
			withHighScore.getChildren().addAll(gameOverTitles, messagesAfterLosing);
		}
		pane.setCenter(withHighScore);
		
		back.setOnAction(e -> {
			pane.getChildren().remove(messagesAfterLosing);
			pane.getChildren().remove(scoreCount);
			startMenu();
		});
		
		removePaneEvent();
	}
	
	public void difficultySetting(){
		mediumDifficulty = false;
		hardDifficulty = false;
		
		VBox textAndDifficultySettings = new VBox(20);
		textAndDifficultySettings.setAlignment(Pos.CENTER);
		HBox forDifficulty = new HBox(20);
		forDifficulty.setAlignment(Pos.CENTER);
		
		Button easy = new Button("Easy");
		Button medium = new Button("Medium");
		Button hard = new Button("Hard");
		
		forDifficulty.getChildren().addAll(easy, medium, hard);
		textAndDifficultySettings.getChildren().addAll(new Text("For information about difficulty settings, "
				+ "please go back to the instructions menu."),
				forDifficulty);
		
		back.setText("<--- Return to Start Menu");
		pane.setTop(back);
		pane.setCenter(textAndDifficultySettings);
		
		easy.setOnAction(e -> {
			updateX = 1;
			updateY = 1;
			ad.moreAds();
			
			pane.getChildren().remove(textAndDifficultySettings);
			pane.getChildren().remove(back);
			
			timeline.play();
			
			mouseClickToPlay();
		});
		medium.setOnAction(e -> {
			updateX = 3;
			updateY = 3;
			ad.moreAds();
			
			mediumDifficulty = true;
			
			pane.getChildren().remove(textAndDifficultySettings);
			pane.getChildren().remove(back);
			
			timeline.play();
			
			mouseClickToPlay();
		});
		hard.setOnAction(e -> {
			updateX = 5;
			updateY = 5;
			ad.moreAds();
			timeOut = 0;
			
			hardDifficulty = true;
			
			pane.getChildren().remove(textAndDifficultySettings);
			pane.getChildren().remove(back);
			
			timeline.play();
						
			mouseClickToPlay();
		});
		
		back.setOnAction(e -> {
			pane.getChildren().remove(textAndDifficultySettings);
			pane.getChildren().remove(back);
			startMenu();
		});
	}
	
	public void instructionsMenu(){
		if(title.isStrikethrough()){
			bawkOrTrump = "the chicken with bawks meant"
					+ "\nto reduce her number of bawks or to stop her from bawking.\n\n";
		}
		else{
			bawkOrTrump = "Trump with ads meant to "
					+ "\nreduce his number of or stop him from gaining more supporters.\n\n";
		}
		
		Text objective = new Text("Objective: ");
		objective.setFont(Font.font(FontSizes.instructions, FontWeight.BOLD, 20));
		Text forObjective = new Text("The objective of this game is to hit " + bawkOrTrump);
		forObjective.setFont(Font.font(FontSizes.instructions, 18));
		
		
		Text modeExplaination = new Text("Mode Explaination: \n\n");
		modeExplaination.setFont(Font.font(FontSizes.instructions, FontWeight.BOLD, 20));
		Text allModes = new Text("Each mode has their own speeds to move at.\n"
				+ "All modes include: Changing direction after hitting a wall.\n"
				+ "Moving to a different location after clicking, regardless if "
				+ "clicked or not.\n"
				+ "Harder difficulties have different attributes to them.\n"
				+ "All of them includes faster moving speeds at higher difficulties.\n\n");
		allModes.setFont(Font.font(FontSizes.instructions, 18));
		
		
		Text mediumMode = new Text("Medium Difficulty Changes: \n\n");
		mediumMode.setFont(Font.font(FontSizes.instructions, FontWeight.BOLD, 20));
		
		
		Text mediumModeText = new Text("Moves to a random spot after hitting a wall.\n\n");
		mediumModeText.setFont(Font.font(FontSizes.instructions, 18));
		
		
		Text hardMode = new Text("Hard Difficulty Changes: \n\n");
		hardMode.setFont(Font.font(FontSizes.instructions, FontWeight.BOLD, 20));
		Text hardModeText = new Text("Moves to a random spot after hitting a wall.\n"
				+ "Randomly changes directions, sometimes after hitting a wall, or when"
				+ "sometimes when traveling.\n"
				+ "Includes a 60 second timer.\n");
		hardModeText.setFont(Font.font(FontSizes.instructions, 18));
		
		
		TextFlow allText = new TextFlow();
		allText.setLayoutX(300);
		allText.setLayoutY(175);
		allText.getChildren().addAll(objective, forObjective, modeExplaination,
				allModes, mediumMode, mediumModeText, hardMode, hardModeText);
		
		
		back.setText("<--- Return to Start Menu");
		
		pane.setTop(back);
		pane.getChildren().add(allText);
		
		back.setOnAction(e -> {
			pane.getChildren().remove(back);
			pane.getChildren().remove(allText);
			startMenu();
		});
	}
	
	public void optionsMenu(){
		VBox allOptions = new VBox(10);
		allOptions.setAlignment(Pos.CENTER);
		
		Button muteButton = new Button();
		if(notMute){
			muteButton.setText("Mute");
		}
		else{
			muteButton.setText("Unmute");
		}
		
		Button removeBackground = new Button();
		if(pane.getBackground() == null)
		{
			removeBackground.setText("Re-add Background");
		}
		else
		{
			removeBackground.setText("Remove Background");
		}

		Button replaceTrumpHead = new Button();
		if(trump.getImage() == movingObjects[0])
		{
			replaceTrumpHead.setText("Change Trump Head");
		}
		else
		{
			replaceTrumpHead.setText("Change Back To Trump Head");
		}
		
		allOptions.getChildren().addAll(muteButton, removeBackground, replaceTrumpHead);
		
		pane.setCenter(allOptions);
		pane.setTop(back);
		
		muteButton.setOnAction(e -> {
			if(notMute){
				notMute = false;
				muteImage.setImage((new Image("Mute.png")));
				muteButton.setText("Unmute");
			}
			else{
				notMute = true;
				muteImage.setImage((new Image("Volume.png")));
				muteButton.setText("Mute");
			}
		});
		
		removeBackground.setOnAction(e -> {
			if(pane.getBackground() == null)
			{
				removeBackground.setText("Remove Background");
				pane.setBackground(new Background(backgroundImage));
			}
			else
			{
				removeBackground.setText("Re-add Background");
				pane.setBackground(null);
			}
		});

		replaceTrumpHead.setOnAction(e -> {
			if(trump.getImage() == movingObjects[0])
			{
				replaceTrumpHead.setText("Change Back To Trump Head");
				trump.setImage(movingObjects[1]);
				title.setStrikethrough(true);
			}
			else
			{
				replaceTrumpHead.setText("Change Trump Head");
				trump.setImage(movingObjects[0]);
				title.setStrikethrough(false);
			}
		});
		back.setText("<--- Return to Start Menu");
		
		back.setOnAction(e -> {
			pane.getChildren().remove(back);
			pane.getChildren().remove(allOptions);
			startMenu();
		});
	}
	
	public void createListOfHighScores(Text[] text, PriorityQueue<Integer> givenScores){
		int threeHighScores;
		ArrayList<Integer> dummy = new ArrayList<>();
		for(threeHighScores = 0; threeHighScores < 3;  threeHighScores++)
		{ 
			if(givenScores.size() == 0)
			{
				break;
			}
			dummy.add(givenScores.remove());
			text[threeHighScores].setText((threeHighScores + 1) + ". " + dummy.get(threeHighScores) + "\n");
		}
		for(int i = threeHighScores; i < 3; i++)
		{
			text[i].setText((i + 1) + ". N/A\n");
		}
		for(int i = 0; i < dummy.size(); i++)
		{
			givenScores.add(dummy.get(i));
		}
	}
}
