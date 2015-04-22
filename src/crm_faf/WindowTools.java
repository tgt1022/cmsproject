/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm_faf;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class WindowTools extends ToolBar {
    private Stage stage;
    private TransitionScene currentScene;
    private HBox buttonBar = new HBox(); 
    private Button widgetButton = new Button();
    private Button nextButton =new Button();
    private Button previousButton = new Button();
    private Button emailButton =new Button();
    private Button refreshButton = new Button();

  

    public void setWidget(StatusWidget widget) {
        this.widget = widget;
    }
    private StatusWidget widget;
    
    public WindowTools(){
        super();
        setup();
    }

    private void setup() {
        nextButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Forwards.png"))));
        nextButton.setVisible(false);
        previousButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Backwards.png"))));
        previousButton.setVisible(false);
        emailButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("email.png"))));
        refreshButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("refresh.png"))));
        
        refreshButton.setOnAction(this.refresh());
        emailButton.setOnAction(sendMail());
        Image statusOk = new Image(getClass().getResourceAsStream("ok-icon.png"));
        widgetButton.setGraphic(new ImageView(statusOk));
        buttonBar.getChildren().addAll(widgetButton, emailButton, refreshButton);
        widgetButton.setOnAction(activateWidget());
        this.getItems().add(buttonBar);  
    }
    
    private void setWidgetStatusOk() {
        Image statusOk = new Image(getClass().getResourceAsStream("ok-icon.png"));
        widgetButton.setGraphic(new ImageView(statusOk));
    }
    
    private void setWidgetStatusNotOk() {
        Image statusBad = new Image(getClass().getResourceAsStream("Delete-icon.png"));
        widgetButton.setGraphic(new ImageView(statusBad));
    }
    
    
    
    
    public void setPrevious(Scene last) {
        System.out.println("added scene to previous");
        System.out.println(last.toString());
        
    }

    private EventHandler<ActionEvent> activateWidget() {
        return (ActionEvent event) -> {
            Stage popup = new Stage();
            VBox comp = new VBox();
            StatusWidget widget = new StatusWidget();
            comp.getChildren().add(widget.getTable());
            Scene popupScene = new Scene(comp, 300, 300);
            popup.setScene(popupScene);
            popup.setX(300);
            popup.setY(400);
        };
     }
    
    private EventHandler<ActionEvent> sendMail() {
        return (ActionEvent event) -> {
            SendEmail email = new SendEmail();
            Stage popup = new Stage();
            VBox comp = new VBox();
            comp.getChildren().add(new TextField("Automated Email Sent"));
            Scene popupScene = new Scene(comp, 300, 300);
            popup.setScene(popupScene);
            popup.setX(300);
            popup.setY(400);
           
        };
    }
      public Stage getStage() {
            return stage;
        }

        public void setStage(Stage stage) {
            this.stage = stage;
        }

        public TransitionScene getCurrentScene() {
            return currentScene;
        }

        public void setCurrentScene(TransitionScene currentScene) {
            this.currentScene = currentScene;
        }

        public HBox getButtonBar() {
            return buttonBar;
        }

        public void setButtonBar(HBox buttonBar) {
            this.buttonBar = buttonBar;
        }

        public Button getWidgetButton() {
            return widgetButton;
        }

        public void setWidgetButton(Button widgetButton) {
            this.widgetButton = widgetButton;
        }

        public Button getNextButton() {
            return nextButton;
        }

        public void setNextButton(Button nextButton) {
            this.nextButton = nextButton;
        }

        public Button getPreviousButton() {
            return previousButton;
        }

        public void setPreviousButton(Button previousButton) {
            this.previousButton = previousButton;
        }

        public Button getEmailButton() {
            return emailButton;
        }

        public void setEmailButton(Button emailButton) {
            this.emailButton = emailButton;
        }

        public Button getRefreshButton() {
            return refreshButton;
        }

        public void setRefreshButton(Button refreshButton) {
            this.refreshButton = refreshButton;
        }

        public StatusWidget getWidget() {
            return widget;
        }

        private EventHandler<ActionEvent> refresh() {
            return (ActionEvent event) -> {
                stage.setScene(new StatusPage().start(stage, this));
                
            };
         }
}
