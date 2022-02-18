import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import java.util.*;
import java.io.*; 

//**************************************************************************************************************************************************
//**************************************************************************************************************************************************
//  CSE1142 - COMPUTER PROGRAMMING 2 TERM PROJECT
//
//	170517033     Yasin Alper BÝNGÜL
//	150119565  	  Osman Buðra GÖKTAÞ
//
//**************************************************************************************************************************************************
//**************************************************************************************************************************************************


public class TheGame extends Application {
		//Data Fields
		//Creating Boxes
		private Boxes[][] box =  new Boxes[10][10];
		//Level - Point definitions
		int generalPoint = 0;
		static int level = 1; 
		int[] highScores = new int[5];
		int[] clickedCoordinate = new int[3];
		
		//Application pane definitions
		BorderPane borderPane = new BorderPane();
		FlowPane pane = new FlowPane();
		
		//Text definitions
		private Text scoreText = new Text(" ");
		private String bottomText;
		private Label lblBottom = new Label("--Text--");
		
		//Button definitions
		Button btNext = new Button("Next Level");
		Button playAgain = new Button("The Game is Over -- Play Again --");
	  
	@Override
	public void start(Stage primaryStage) throws Exception {
			pane.setHgap(0);
			pane.setVgap(0);
			
			//Firstly Draw Map
			drawMap();
			scoreText.setText("Level #" + level + "                                            " + generalPoint + "                                     High Score: " + highScores[level-1]);
			
			//If the user clicked the "Next" Button
			btNext.setOnMouseClicked(e -> {
				try {
					buttonClicked();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			//If the user clicked the "Play Again" Button
			playAgain.setOnMouseClicked(e -> {
				try {
					buttonClicked2();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
		    //Set the borderPane
			borderPane.setTop(scoreText);
			borderPane.setCenter(pane);
		    borderPane.setBottom(lblBottom);
			//Set the scene
		    
			Scene scene = new Scene(borderPane,400,444);
			primaryStage.setScene(scene);
		    primaryStage.setResizable(false);
		    primaryStage.setTitle("The Game");
			primaryStage.show();
		}
	public static void main(String[] args) throws Exception {
		
		launch(args);
	}
	
	//Take file string and split it
	public static ArrayList<String> file() throws FileNotFoundException {
		//Create File object
		File file = null;
		
		//Choose the level according to "level" data field
		switch(level) {
		case 1:
			file = new File("level"+ 1 + ".txt");
			break;
		case 2:
			file = new File("level"+ 2 + ".txt");
			break;
		case 3:
			file = new File("level"+ 3 + ".txt");
			break;
		case 4:
			file = new File("level"+ 4 + ".txt");
			break;
		case 5:
			file = new File("level"+ 5 + ".txt");
			break;
		default:
			System.out.println("This " + level + " level is not existed.");
			break;
		}
		
		//Create ArrayList for splitting the file content
		ArrayList<String> f = new ArrayList<>();
		
		try (
			Scanner input = new Scanner(file);
		){
			while (input.hasNext()) {
				 String s1 = input.nextLine();
				 String[] splitted = s1.split(",");
				 
				 for(int i=0; i<splitted.length; i++) {
					 f.add(splitted[i]);
				 }
			}
		}
	
		//Return the splitted file content
		return f;
	}
	
	//This module is made for record the high scores with using level and score data fields
	private void highScores(int level, int score) {
		switch(level) {
		case 1:
			if(highScores[0] < score || score < 0) {
				System.out.println("highScore1 " + score);
				highScores[0] = score;
			}
			break;
		case 2:
			if(highScores[1] < score || score < 0) {
				System.out.println("highScore2 " + score);
				highScores[1] = score;
			}
			break;
		case 3:
			if(highScores[2] < score || score < 0) {
				System.out.println("highScore3 " + score);
				highScores[2] = score;
			}
			break;
		case 4:
			if(highScores[3] < score || score < 0) {
				System.out.println("highScore4 " + score);
				highScores[3] = score;
			}
			break;
		case 5:
			if(highScores[4] < score || score < 0) {
				System.out.println("highScore5 " + score);
				highScores[4] = score;
			}
			break;
		default:
			System.out.println("This is not valid level");
			break;	
		}
	}
	
	//Checks map status for finish the level
	public boolean checkBoxes(Boxes[][] box) {
		boolean indicator = false;
		
		//Check the map
		for(int i=0; i<box.length; i++) {
			for(int j=0; j<box.length; j++) {
				if(box[i][j].name.equals("Wood") || box[i][j].name.equals("Mirror")) {
					indicator = true;
				}
			}
		}
		
		return indicator;	
	}
	
	//This method is made for create game board
	public void drawMap() throws Exception {
		int size = 0; //counts created boxes
		int count = 0; //counts index of string array
		
		//If there is any box in box[][], then remove from pane it for create and add new boxes.
		for(int i=0; i<box.length; i++) {
			for(int j=0; j<box[i].length; j++) {
				if( box[i][j] != null)
				{
					pane.getChildren().remove(box[i][j].getImage());
					box[i][j] = null;
				}		
			}
		}
		
		//Create boxes with the datas from file input
			for (int i = 0; i < 10; i++) {
		    	for (int j = 0; j < 10; j++) {
		    		if(count != file().size()) {
		    			if(file().get(count).equals("Mirror")) {
			    			box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))] = new MirrorBox();
			    			count += 3;
			    			size++;
			    		}
			    		else if( file().get(count).equals("Empty")) {
			    			box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))] = new EmptyBox();
			    			count += 3;
			    			size++;
			    		}
			    		else if(file().get(count).equals("Wood")) {
			    			System.out.println(file().get(count+1) + ", " + Integer.parseInt(file().get(count+2)));
			    			box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))] = new WoodBox();
			    			count += 3;
			    			size++;
			    		}
		    		}
		      }
		   }
			
			//Fill the blanks with wall type box
			for(int i=0; i<box.length; i++) {
				for(int j=0; j<box[i].length; j++) {
					if(box[i][j] == null) {
			    		box[i][j] = new WallBox();
			    		size++;
		    		}
				}
			}
			
		//Add the created new boxes to pane
		for(int i=0; i<box.length; i++) {
			for(int j=0; j<box[i].length; j++) {
				pane.getChildren().add(box[i][j].getImage());	
			}
		}
		System.out.println("Size:"+size);	
	}
	
	//This method is made for update the game board
	public void updateMap() throws Exception {
		int size = 0; //counts created boxes
		int count = 0; //counts index of string array
		
		//If there is any box in box[][], then remove from pane it for create and add new boxes.
		for(int i=0; i<box.length; i++) {
			for(int j=0; j<box[i].length; j++) {
				pane.getChildren().remove(box[i][j].getImage());	
			}
		}
		
		//Getting data from file
		while(count != file().size()) {
		   //If file has Mirror type box and durability is still same, then create Mirror Box again.
		   if(file().get(count).equals("Mirror") && box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))].getDurability() == 5) {
			    box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))] = new MirrorBox();    			
			    count += 3;
			    size++;		
		   }
		   //If clicked the Mirror box and the durability is decreased, then create Empty Box.
		   else if(file().get(count).equals("Mirror") && box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))].getDurability() == 4) {
			    System.out.println(file().get(count+1) + ", " + Integer.parseInt(file().get(count+2)));
			    box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))] = new EmptyBox();
			    			
			    if(box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))].getDurability() == -1) {
			    	clickedCoordinate[0] = Integer.parseInt(file().get(count+1));
				   	clickedCoordinate[1] = Integer.parseInt(file().get(count+2));
				   	clickedCoordinate[2] = 0; // clicked on mirror
			    }		
			    count += 3;
			    size++;
			   } 
		   //If file has Empty type box and durability is still same, then create Empty Box again.
		   else if( file().get(count).equals("Empty")) {
			    box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))] = new EmptyBox();
			    
			    count += 3;
			    size++;
				}
		   //If file has Wood type box and durability is still same, then create Wood Box again.
		   else if(file().get(count).equals("Wood") && box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))].getDurability() == 2) {
			    System.out.println(file().get(count+1) + ", " + Integer.parseInt(file().get(count+2)));
			    box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))] = new WoodBox();
			    			
			    count += 3;
			    size++;
				}
		   //If clicked the Wood box and the durability is decreased, then create Mirror Box.
			else if(file().get(count).equals("Wood") && box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))].getDurability() == 1) {
			    box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))] = new MirrorBox();
			    	
			    if(box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))].getDurability() == 5) {
			    	clickedCoordinate[0] = Integer.parseInt(file().get(count+1));
				    clickedCoordinate[1] = Integer.parseInt(file().get(count+2));
				    clickedCoordinate[2] = 1; // clicked on wood - first time
			    }		
			    count += 3;
			    size++;
			    			
			  	}

		    //If double clicked the Wood box and the durability is decreased again, then create Empty Box.
			else if(file().get(count).equals("Wood") && box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))].getDurability() == 4) {
			    box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))] = new EmptyBox();
			    			
			    if(box[Integer.parseInt(file().get(count+1))][Integer.parseInt(file().get(count+2))].getDurability() == -1) {
			    	clickedCoordinate[0] = Integer.parseInt(file().get(count+1));
				    clickedCoordinate[1] = Integer.parseInt(file().get(count+2));
				 	clickedCoordinate[2] = -1; // clicked on wood - double
			    }
			    count += 3;
			    size++;
			   }
			else {
			   count += 3;
			   size++;
			}
		
		  }
			
		//Fill the blanks with wall type box
			for(int i=0; i<box.length; i++) {
				for(int j=0; j<box[i].length; j++) {
					if(box[i][j].getDurability() == 6) {
		    			box[i][j] = new WallBox();
		    			size++;
	    			}
				}
			}
			
			//Add the created new boxes to pane
			for(int i=0; i<box.length; i++) {
				for(int j=0; j<box[i].length; j++) {
					pane.getChildren().add(box[i][j].getImage());	
				}
			}
			
		//Test Displays
		System.out.println("Updated Size:" + size);	
		System.out.println("Clicked Coordinate : " + clickedCoordinate[0] + "," + clickedCoordinate[1] + "  Type: " + ((clickedCoordinate[2] == 1)?"Wood":"Mirror"));
	
	}
	
	//This method updates the neighbour boxes according to mouse click. Then calculate the earned score.
	public int updateNeighbours(int[] coordinates) {
		int point = 0;
		int resultPoint = 0;
		
		int x = coordinates[0];
		int y = coordinates[1];
		//Test Display
		System.out.println(box[x][y].getName());
		System.out.println(box[x][y-1].getName());
		System.out.println(box[x][y+1].getName());
		System.out.println(box[x-1][y].getName());
		System.out.println(box[x+1][y].getName());
		
		//Remove the boxes from pane
		for(int i=0; i<box.length; i++) {
			for(int j=0; j<box[i].length; j++) {
				pane.getChildren().remove(box[i][j].getImage());	
			}
		}
		//Update the bottom text
		bottomText = "Box: " + coordinates[0] + "," + coordinates[1];
		//Put the correct box with using coordinates and display informations about click event.
		if(clickedCoordinate[2] == 1) {
			if(box[x][y-1].getName() == "Mirror") {
				box[x][y-1] = new EmptyBox();
				point++;
				bottomText += " - " + "Hit : " + x + "," + (y-1);
			}
			if(box[x][y+1].getName() == "Mirror") {
				box[x][y+1] = new EmptyBox();
				point++;
				bottomText += " - " +"Hit : " + x + "," + (y+1);
			}
			if(box[x-1][y].getName().equals("Mirror")) {
				box[x-1][y] = new EmptyBox();
				point++;
				bottomText += " - "  + "Hit : " + (x-1) + "," + y;
			}
			if(box[x+1][y].getName() == "Mirror") {
				box[x+1][y] = new EmptyBox();
				point++;
				bottomText += " - "  + "Hit : " + (x+1) + "," + y;
			}

			if(box[x][y-1].getName() == "Wood") {
				box[x][y-1] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + x + "," + (y-1);
			}
			if(box[x][y+1].getName() == "Wood") {
				box[x][y+1] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + x + "," + (y+1);
			}
			if(box[x-1][y].getName().equals("Wood")) {
				box[x-1][y] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + (x-1) + "," + y;
			}
			if(box[x+1][y].getName() == "Wood") {
				box[x+1][y] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + (x+1) + "," + y;
			}
		}
		else if( clickedCoordinate[2] == -1) {
			if(box[x][y-1].getName() == "Mirror") {
				box[x][y-1] = new EmptyBox();
				point++;
				bottomText +=" - " + "Hit : " + x + "," + (y-1);
			}
			if(box[x][y+1].getName() == "Mirror") {
				box[x][y+1] = new EmptyBox();
				point++;
				bottomText += " - " + "Hit : " + x + "," + (y+1);
			}
			if(box[x-1][y].getName().equals("Mirror")) {
				box[x-1][y] = new EmptyBox();
				point++;
				bottomText +=" - " + "Hit : " + (x-1) + "," + y;
			}
			if(box[x+1][y].getName() == "Mirror") {
				box[x+1][y] = new EmptyBox();
				point++;
				bottomText +=" - " + "Hit : " + (x+1) + "," + y;
			}
			if(box[x][y-1].getName() == "Wood") {
				System.out.println("GÝRDÝ21");
				box[x][y-1] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + x + "," + (y-1);
			}
			if(box[x][y+1].getName() == "Wood") {
				box[x][y+1] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + x + "," + (y+1);
			}
			if(box[x-1][y].getName() == "Wood") {
				box[x-1][y] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + (x-1) + "," + y;
			}
			if(box[x+1][y].getName() == "Wood") {
				box[x+1][y] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + (x+1) + "," + y;
			}
		}
		else {
			System.out.println("GÝRDÝ2");
			if(box[x][y-1].getName() == "Mirror") {
				box[x][y-1] = new EmptyBox();
				point++;
				bottomText += " - " + "Hit : " + x + "," + (y-1);
			}
			if(box[x][y+1].getName() == "Mirror") {
				box[x][y+1] = new EmptyBox();
				point++;
				bottomText += " - " + "Hit : " + x + "," + (y+1);
			}
			if(box[x-1][y].getName() == "Mirror") {
				box[x-1][y] = new EmptyBox();
				point++;
				bottomText += " - " + "Hit : " + (x-1) + "," + y;
			}
			if(box[x+1][y].getName() == "Mirror") {
				box[x+1][y] = new EmptyBox();
				point++;
				bottomText += " - " + "Hit : " + (x+1) + "," + y;
			}
			if(box[x][y-1].getName() == "Wood") {
				box[x][y-1] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + x + "," + (y-1);
			}
			if(box[x][y+1].getName() == "Wood") {
				box[x][y+1] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + x + "," + (y+1);
			}
			if(box[x-1][y].getName() == "Wood") {
				box[x-1][y] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + (x-1) + "," + y;
			}
			if(box[x+1][y].getName() == "Wood") {
				box[x+1][y] = new MirrorBox();
				point++;
				bottomText += " - " + "Hit : " + (x+1) + "," + y;
			}
		}
		
		//Add the new created boxes to pane
		for(int i=0; i<box.length; i++) {
			for(int j=0; j<box[i].length; j++) {
				pane.getChildren().add(box[i][j].getImage());	
			}
		}
		
		//Find the generalPoint
		switch(point) {
		case 0:
			resultPoint = -3;
			break;
		case 1:
			resultPoint = -1;
			break;
		case 2:
			resultPoint = 1;
			break;
		case 3:
			resultPoint = 2;
			break;
		case 4:
			resultPoint = 4;
			break;
		default:
			System.out.println("The resultPoint not ended");
			break;
		}
		
		//return the result point
		bottomText += (resultPoint > 0)?" (+" + resultPoint + " points)":" (" + resultPoint + " points)";
		lblBottom.setText(bottomText);
		return resultPoint;
	}
	
	//The method that sets top label
	public void updateTopLabel() {
		scoreText.setText("Level #" + level + "                                            " + generalPoint + "                                   High Score: " +  highScores[level-1]);
	}  
	
	
	public abstract class Boxes extends Pane{
		//Each box has own name,durability and image
		String name;
		private int durability;
		protected ImageView image;
		
		//Getter and setter methods
		public ImageView getImage() {
			return image;
		}

		public void setImage(ImageView image) {
			image.setFitHeight(40);
			image.setFitWidth(40);
			this.image = image;
		}

		public int getDurability() {
			return durability;
		}

		public void setDurability(int durability) {
			this.durability = durability;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		//Abstract method
		public abstract void isClicked() throws Exception;
	}
	
	public class MirrorBox extends Boxes{
		//Constructor
		public MirrorBox() {
			//Set name, image and durability
			setName("Mirror");
			setImage(new ImageView("/MirrorBox.PNG"));
			setDurability(5);
			
			//If clicked the image, then run isClicked() method
			image.setOnMouseClicked(e -> {
				try {
					isClicked();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		}

		@Override
		public void isClicked() throws Exception {
			//If durability is not 0, then decrease it.
			setDurability((getDurability() != 0)?getDurability()-1:0);
			//Update map according to new durability values
			updateMap();
			//Calculate general point with using method below
			generalPoint += updateNeighbours(clickedCoordinate);
			//Update top label
			updateTopLabel();
			
			//If there isn't any Mirror or Wood boxes, then put the bottom screen a "Next Level" button
			if(!checkBoxes(box)) {
				//Update bottom pane
				borderPane.setBottom(btNext);
				BorderPane.setAlignment(btNext, Pos.BOTTOM_CENTER);
				//Update High Score
				highScores(level,generalPoint);
				updateTopLabel();
				
			}
			
		}
		
	}
	
	public class EmptyBox extends Boxes{
		public EmptyBox() {
			setName("Empty");
			setImage(new ImageView("EmptyBox.PNG"));
			setDurability(-1);
			//If clicked the image, then run isClicked() method
			image.setOnMouseClicked(e -> {
				try {
					isClicked();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		    
		}

		@Override
		public void isClicked() throws Exception {
			//Update map according to new durability values
			updateMap();
			
			//If there isn't any Mirror or Wood boxes, then put the bottom screen a "Next Level" button
			if(!checkBoxes(box)) {
				//Update bottom pane
				borderPane.setBottom(btNext);
				 BorderPane.setAlignment(btNext, Pos.BOTTOM_CENTER);
				//Update High Score
				highScores(level,generalPoint);
				updateTopLabel();
			}
		}
	}
	
	public class WallBox extends Boxes{
		public WallBox() {
			setName("Wall");
			setImage(new ImageView("WallBox.PNG"));
			setDurability(6);
			//If clicked the image, then run isClicked() method
			image.setOnMouseClicked(e -> {
				try {
					isClicked();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}

		@Override
		public void isClicked() throws Exception {
			//Update map according to new durability values
			updateMap();
			
			//If there isn't any Mirror or Wood boxes, then put the bottom screen a "Next Level" button
			if(!checkBoxes(box)) {
				//Update bottom pane
				borderPane.setBottom(btNext);
				 BorderPane.setAlignment(btNext, Pos.BOTTOM_CENTER);
				//Update High Score
				highScores(level,generalPoint);
				updateTopLabel();
			}

		}
	    
	}
	
	public class WoodBox extends Boxes{
		//Constructor
		public WoodBox() {
			//Set name, image and durability
			setName("Wood");
			setImage(new ImageView("WoodBox.PNG"));
			setDurability(2);
			
			//If clicked the image, then run isClicked() method
			image.setOnMouseClicked(e -> {
				try {
					isClicked();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		}
		

		@Override
		public void isClicked() throws Exception {
			//If durability is not 0, then decrease it.
			setDurability((getDurability() != 0)?getDurability()-1:0);
			//Update map according to new durability values
			updateMap();
			generalPoint += updateNeighbours(clickedCoordinate);
			updateTopLabel();
			
			//If there isn't any Mirror or Wood boxes, then put the bottom screen a "Next Level" button
			if(!checkBoxes(box)) {
				//Update bottom pane
				borderPane.setBottom(btNext);
				 BorderPane.setAlignment(btNext, Pos.BOTTOM_CENTER);
				//Update High Score
				highScores(level,generalPoint);
				updateTopLabel();
				
			}
		}
	}
	
	//If user click the next level button, increase the level data field and update the map
	private void buttonClicked() throws Exception {
		level++;
		//There is 6 levels in game
		if(level < 6) {
			System.out.println(level);
			drawMap();
			System.out.println("Next Button Clicked");
			lblBottom = new Label("--Text--");
			borderPane.setBottom(lblBottom);
			generalPoint = 0;
			updateTopLabel();
		}
		else {
			borderPane.setBottom(playAgain);
			BorderPane.setAlignment(playAgain, Pos.BOTTOM_CENTER);
		}
	}
	
	//If user click Play Again button, then start the game again with recorded high scores.
	private void buttonClicked2() throws Exception {
		level=1;
		drawMap();
		System.out.println("Next Button Clicked");
		lblBottom = new Label("--Text--");
		borderPane.setBottom(lblBottom);
		generalPoint = 0;
		System.out.println("High Socre: " + highScores[level-1]);
		updateTopLabel();
	}

	

}
