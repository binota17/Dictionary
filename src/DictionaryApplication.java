import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DictionaryApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/view.fxml")));
        Scene scene = new Scene(root);
        // bị lỗi ở đây thì vào packege image copy absolute path của icon.png rồi thay vào cái path ở dưới hộ em với ạ.
        Image icon = new Image("D:\\project1_dictionary-main\\src\\image\\icon.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setTitle("ĐẶT TẠM TÊN GÌ ĐI BẠN?");
        stage.setScene(scene);
        stage.show();
    }
}
