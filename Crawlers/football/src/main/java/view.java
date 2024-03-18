import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @time: 2024/3/9 8:02
 * @description:
 */

public class view extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建一个包含Person对象的ObservableList作为表格的数据源
        ObservableList<Person> data = FXCollections.observableArrayList();
        data.add(new Person("John Doe", "30"));
        data.add(new Person("Jane Smith", "25"));

        // 创建TableView并设置列
        TableView<Person> tableView = new TableView<>(data);

        TableColumn<Person, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Person, String> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(cellData -> cellData.getValue().ageProperty());

        tableView.getColumns().addAll(nameCol, ageCol);

        StackPane root = new StackPane(tableView);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("JavaFX TableView Example with Data Binding");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public class Person {
        // 创建一个包含Person对象的ObservableList作为表格的数据源
        private final SimpleStringProperty name = new SimpleStringProperty();
        private final SimpleStringProperty age = new SimpleStringProperty();

        public Person(String name, String age) {
            this.name.set(name);
            this.age.set(age);
        }

        public String getName() {
            return name.get();
        }

        public String getAge() {
            return age.get();
        }

        // 提供属性以供TableView读取
        public SimpleStringProperty nameProperty() {
            return name;
        }

        public SimpleStringProperty ageProperty() {
            return age;
        }
    }

}
