package connections;

import java.util.Scanner;

public class Connections {
    private static Scanner scanner;

    private static Connections instance;

    private Connections() {
        scanner = new Scanner(System.in);
    }

    public static Connections getInstance(){
        if (instance == null){
            instance = new Connections();
        }
        return instance;
    }

    public void sendData(String data){
        System.out.println(data);
    }

    public String getData(){
        return scanner.next();
    }
}
