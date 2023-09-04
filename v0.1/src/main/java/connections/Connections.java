package connections;

import java.util.Scanner;

// 方便后续改成玩网络连接

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


    public void sendData(String []data){
        for (var val : data){
            System.out.println(val);
        }
    }

    public String getData(){
        return scanner.next();
    }
}
