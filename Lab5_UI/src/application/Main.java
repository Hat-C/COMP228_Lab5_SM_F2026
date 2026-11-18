package application;
	
/**
 * @author Shannon Munroe
 * @date November 5th 2026
 */


import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			HBox topBox = new HBox(10);
			VBox leftBox = new VBox(10);
			VBox centerBox = new VBox(10);
			GridPane form = new GridPane();
			form.setVgap(8);
			form.setHgap(8);
			form.setPadding(new Insets(8, 12, 8, 0));
			Button dashboardButton = new Button("Dashboard");
			Button registrationButton = new Button("Registration");
			Button coursesButton = new Button("Courses");
			Button helpButton = new Button("Help");
			leftBox.getChildren().addAll(dashboardButton,registrationButton,coursesButton,helpButton);
			for(Node child:leftBox.getChildren()) {
				((Button) child).setPrefSize(80,20);
			}
			leftBox.prefWidth(80);
			leftBox.setPadding(new Insets(15, 12, 15, 12));
			TextField studentIdField = new TextField();
			studentIdField.setId("field-studentID");
			studentIdField.setPromptText("9 digit student id");
			studentIdField.setOnKeyTyped(new integerOnlyFieldHandler());
			studentIdField.setOnAction(e -> {
				if(studentIdField.getLength()>9) {
					studentIdField.setText(studentIdField.getText().substring(0, 9));
				}
			});
			Label studentIdLabel = new Label("Student ID: ");
			studentIdLabel.labelForProperty();
			studentIdLabel.setLabelFor(studentIdField);
			topBox.setStyle("-fx-background-color: purple;");
			topBox.setMinHeight(80);
			root.setTop(topBox);
			leftBox.setMinWidth(40);
			root.setLeft(leftBox);
			root.setCenter(centerBox);
			TextField studentNameField = new TextField();
			studentNameField.setPromptText("full name");
			Label studentNameLabel = new Label("Full name: ");
			TextField studentEmailField = new TextField();
			studentEmailField.setPromptText("email");
			Label studentEmailLabel = new Label("Email: ");
			TextField programIdField = new TextField();
			TextField programNameField = new TextField();
			programNameField.setPromptText("program name");
			Label programLabel = new Label("program: ");
			programIdField.setPromptText("4 digit id");
			programIdField.setOnKeyTyped(new integerOnlyFieldHandler());
			programIdField.setOnAction(e -> {
				if(programIdField.getLength()>4) {
					programIdField.setText(programIdField.getText().substring(0, 4));
				}
			});
			studentNameLabel.setMinWidth(80);
			form.add(studentIdLabel, 0, 0);
			form.add(studentIdField, 1, 0);
			form.add(studentNameLabel, 0, 1);
			form.add(studentNameField, 1, 1);
			form.add(studentEmailLabel, 0, 2);
			form.add(studentEmailField, 1, 2);
			form.add(programLabel, 0, 3);
			form.add(programNameField, 1, 3);
			form.add(programIdField, 2, 3);
			Button clearButton = new Button("clear");
			Button registerButton = new Button("register");
			TextField[] textFields = {studentIdField, studentNameField, studentEmailField, programNameField, programIdField};
			clearButton.setOnAction(e -> {
				for(TextField input:textFields) {
					input.clear();
				}
			});
			registerButton.setOnAction(e -> {
				TextField[] blankFields = new TextField[textFields.length];
				//String emailPattern = "^[a-z0-9]+([a-z0-9\\+'_-]+\\.)*[a-z0-9\\+'_-]*[a-z0-9]@[a-z0-9]+\\.([a-z0-9]+\\.)*[a-z0-9]+$";
				Pattern emailPattern = Pattern.compile("^[a-z0-9]+([a-z0-9\\+'_-]+\\.)*[a-z0-9\\+'_-]*[a-z0-9]@[a-z0-9]+\\.([a-z0-9]+\\.)*[a-z0-9]+$", Pattern.CASE_INSENSITIVE);
				int indexVar = 0;
				Alert a = new Alert(AlertType.WARNING);
				for(TextField input:textFields) {
					if(input.getText().isBlank()) {
						blankFields[indexVar++] = input;
					}
				}
				if (blankFields[0]!=null) {
					a.setContentText("fill in all blank fields");
					blankFields[0].requestFocus();
				} else if (studentIdField.getLength()!=9) {
					a.setContentText("Student ID must be 9 digits");
					studentIdField.requestFocus();
				} else if (!emailPattern.matcher(studentEmailField.getText()).matches()){
					a.setContentText("Email format incorrect");
					studentEmailField.requestFocus();
				} else if (programIdField.getLength()!=4) {
					a.setContentText("Program ID must be 4 digits");
					programIdField.requestFocus();
				} else {
					a.setAlertType(AlertType.INFORMATION);
					a.setContentText("Form submitted");
				}
				a.show();
			});
			HBox formButtons = new HBox(clearButton,registerButton);
			centerBox.getChildren().addAll(form, formButtons);
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}


class integerOnlyFieldHandler implements EventHandler<KeyEvent> {
	public void handle(KeyEvent e) {
		TextInputControl caller = ((TextInputControl) e.getSource());
		if( (!e.isControlDown()&&caller.getLength()>0&&!elementInArray(e.getCharacter().charAt(0), new char[]{'0','1','2','3','4','5','6','7','8','9',''}))
			||(e.isControlDown()&&!caller.getSelectedText().isEmpty()&&!elementInArray(caller.getSelectedText().charAt(0), new char[]{'0','1','2','3','4','5','6','7','8','9'}))
		) { caller.deletePreviousChar(); }
	}
	
	public static boolean elementInArray(char elem, char[] source) {
		for(char index: source) {
			if(index==elem) { return true; }
		} return false;
	}
}

class FormSubmitHandler implements EventHandler<ActionEvent>{
	public void handle(ActionEvent e) {
		System.out.println("SUBMIT");
	}
}
