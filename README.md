# Campus Event Booking System
## Engg*1420 Group 23


### How To Compile & Run

First, download JavaFX SDK from [here](https://gluonhq.com/products/javafx/)\
Then, in IntelliJ settings, go to:
```
File > Project Structure > Libraries
```
After, click the + in the top left and add the lib folder for JavaFX\
It'll be in the JavaFX folder that was installed.\
After, go to
```
Run > Edit Configuration
```
then 
```
Add New > Application > Modify Options > Add VM Options
```
and in VM Options, add
```--module-path "LIBPATH" --add-modules javafx.controls,javafx.fxml``` (Replace LIBPATH with the path you used in libraries)\
Then set main class as com.group23.EventGUI