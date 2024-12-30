package myXO;


import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class xogame extends Application {
boolean flag =true;
boolean end =false;
int spaces =0;
int W =0;
int L =0;
String[][] board = new String[3][3];
Button btn = new Button("replay");

Text rslt2= new Text(new String(W +" - "+ L));
Pane lowerp = new Pane(btn,rslt2);
Text rslt;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage ps) throws Exception {
		ps.setTitle("404 XO");
		double size = 200;
		GridPane gp = new GridPane();
		btn.setLayoutX(500);
		btn.setLayoutY(12.5);
		btn.setPrefWidth(75);
		btn.setDisable(true);
		rslt2.setLayoutX(290);
		rslt2.setLayoutY(30);
		
		VBox vb = new VBox(gp, lowerp);
		Pane cell[][] = new Pane[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				cell[i][j] = new Pane();
				cell[i][j].setPrefSize(size, size);
				cell[i][j].setStyle("-fx-border-color: black; -fx-background-color: white;");
                int r = i;
                int c = j;
                cell[i][j].setOnMouseClicked(event -> handleMouseClick(event, cell, r, c));
                gp.add(cell[i][j], i, j);
			}
		
		btn.setOnAction(event -> {
			flag = true;
			end = false;
			spaces = 0;
			if(lowerp.getChildren().contains(rslt)) lowerp.getChildren().remove(rslt);
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					board[i][j] =null;
					if(!cell[i][j].getChildren().isEmpty())
					cell[i][j].getChildren().clear();
				}
				}
			btn.setDisable(true);
			
		});
				
        gp.setStyle("-fx-background-color: lightgray;");
        Scene scene = new Scene(vb, size*3, size*3+50);
        ps.setScene(scene);
        ps.show();
		
	}
	
	
	boolean checkrow(String sy)
	{
	for(int cnt=0,i=0;i<3;i++) {
		for(int j=0;j<3;j++) {
			if(board[i][j]==sy) cnt++;
		}
	if(cnt==3) return true;
	else cnt =0;
		}
	return false;
	}
	
	
	boolean checkcol(String sy)
	{
	for(int cnt=0,i=0;i<3;i++) {
		for(int j=0;j<3;j++) {
			if(board[j][i]==sy) cnt++;
		}
	if(cnt==3) return true;
	else cnt = 0;
		}
	return false;
	}
	
	
	boolean checkdiag(String sy)
	{
		int cnt =0;
	for(int i=0;i<3;i++) {
			if(board[i][i]==sy) cnt++;
		}
	if(cnt==3) return true;
	else cnt = 0;
	return false;
	}
	
	boolean checkdiag2(String sy)
	{
		int cnt =0;
	if(board[0][2]==sy) cnt++;
	if(board[1][1]==sy) cnt++;
	if(board[2][0]==sy) cnt++;
	if(cnt==3) return true;
	return false;
	}
	
	
	void checkwin() {
		if (checkrow("X") || checkcol("X") || checkdiag("X") || checkdiag2("X")) {
			 rslt = new Text("Winner!!");
			 rslt.setFill(Color.GREEN);
			 rslt.setLayoutX(20);
			 rslt.setLayoutY(30);
			 lowerp.getChildren().add(rslt);
			 btn.setDisable(false);
			 end = true;
			 W+=1;
			 rslt2.setText(new String(W+" - "+L));
			 
		}
	}
	
	
	void checkloose(){
		if ((checkrow("O") || checkcol("O") || checkdiag("O") || checkdiag2("O")) && !(checkrow("X") || checkcol("X") || checkdiag("X") || checkdiag2("X")))
		{
			rslt = new Text("Looser!!");
			rslt.setFill(Color.RED);
			rslt.setLayoutX(20);
			rslt.setLayoutY(30);
			lowerp.getChildren().add(rslt);
			btn.setDisable(false);
			end = true;
			L+=1;
			rslt2.setText(new String(W+" - "+L));

		}
	}
	
	
	void checkDraw()
	{
		if(spaces==9) {
			if(!(checkrow("X") || checkcol("X") || checkdiag("X") || checkdiag2("X")) && !(checkrow("O") || checkcol("O") || checkdiag("O") || checkdiag2("O"))) {
			rslt = new Text("DRAW!!");
			rslt.setFill(Color.YELLOW);
			rslt.setStrokeWidth(0.5);
			rslt.setStroke(Color.BLACK);
			rslt.setLayoutX(20);
			rslt.setLayoutY(30);
			lowerp.getChildren().add(rslt);
			btn.setDisable(false);
			end = true;

			}
		}
	}
	
	void botTurn(Pane cell[][]) 
	{
			Random rand = new Random();
			
		while(spaces<8) {
			int i = rand.nextInt(3);
			int j = rand.nextInt(3);
			if(board[i][j]==null) {
			board[i][j]="O";
			spaces++;
			Text T = new Text("O");
			T.setFill(Color.BLUE);
			T.setFont(Font.font(80));
			T.setLayoutX(cell[i][j].getWidth()/2-20);
			T.setLayoutY(cell[i][j].getHeight()/2+20);
			cell[i][j].getChildren().add(T);
			flag=!flag;
			checkloose();
			checkDraw();
			return;
			}
			}
	}
	
	
	void handleMouseClick(MouseEvent event, Pane cell[][], int r, int c)
	{
	if(board[r][c]!=null) return;
	if(!end)
	if(flag)
	{
		board[r][c]="X";
		spaces++;
		Text T = new Text("X");
		T.setFill(Color.RED);
		T.setFont(Font.font(80));
		T.setLayoutX(cell[r][c].getWidth()/2-20);
		T.setLayoutY(cell[r][c].getHeight()/2+20);
		cell[r][c].getChildren().add(T);
		flag= !flag;
		checkwin();
		checkDraw();
		botTurn(cell);
		
	}
	}

}
