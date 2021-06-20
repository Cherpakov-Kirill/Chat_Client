import nsu.oop.client.presenter.Presenter;
import nsu.oop.client.view.View;
import nsu.oop.client.view.windows.MainWindow;

public class Main {

    public static String ipAddr = "127.0.1.1";
    public static int port = 8080;

    public static void main(String[] args) {
        View view = new MainWindow();
        Presenter presenter = new Presenter(view, ipAddr, port);
        presenter.launchTheChat();
    }
}