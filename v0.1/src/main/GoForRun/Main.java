import processes.ProcessInstance;
import processes.menus.WelcomeMenu;

class Main{
    public static void main(String[] args) {
        // start CLI
        ProcessInstance processInstance = new ProcessInstance(new WelcomeMenu());
    }
}