import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SimpleWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a StackPane layout
        StackPane root = new StackPane();

        // Create a Scene with the StackPane as its root
        Scene scene = new Scene(root, 400, 300);

        // Set the scene on the primary stage
        primaryStage.setScene(scene);

        // Set the title of the primary stage
        primaryStage.setTitle("Simple Window11");

        // Show the primary stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
