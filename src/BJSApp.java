import controller.BJSController;
import model.BJSModel;
import view.BJSView;

public class BJSApp {

    public static void main(String[] args) {
        BJSView view = new BJSView();
        BJSModel model = new BJSModel();
        BJSController controller = new BJSController();

        controller.setView(view);
        controller.setModel(model);
        controller.startFlow();
    }
}
