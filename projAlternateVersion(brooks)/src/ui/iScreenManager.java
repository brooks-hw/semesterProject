package ui;

//Interface to MainFrame that allows panels to handle switching
// without having to know about the MainFrame class

public interface iScreenManager {
    void switchTo(String screenName);
}
