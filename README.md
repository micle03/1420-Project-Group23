# Campus Event Booking System
## Engg*1420 Group 23


### How To Compile & Run

1. Download the JavaFX SDK matching your JDK version from [gluonhq.com/products/javafx](https://gluonhq.com/products/javafx)

2. In IntelliJ, go to **File → Project Structure → Modules**, click `+`, select **Import Module**, and point it to the `.iml` file in the project root.

3. Still in Project Structure, go to **Libraries**, click `+`, select Java, and point it to the `lib` folder inside your JavaFX SDK. Name the library `lib`.

4. Go to **Run → Edit Configurations → Add New → Application** and set:
    - **Module**: `1420-Project-Group23`
    - **Main class**: `com.group23.EventGUI`
    - Click **Modify Options → Add VM Options** and add:
```
     --module-path "LIBPATH" --add-modules javafx.controls,javafx.fxml
```
     Replace `LIBPATH` with the path to your JavaFX SDK's `lib` folder.

### How To Execute The Text Suite

1. Right-click the 'test' folder, and select the option saying **Run 'All Tests'**

2. Rollback .csv files after tests conclude