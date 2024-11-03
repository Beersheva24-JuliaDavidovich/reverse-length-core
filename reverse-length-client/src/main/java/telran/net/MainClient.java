package telran.net;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandardInputOutput;

public class MainClient {
    static ReverseLengthClient client;

    public static void main(String[] args) {
        Item[] items = {
            Item.of("Start session", MainClient::startSession),
            Item.of("Exit", MainClient::exit, true)
        };
        Menu menu = new Menu("Reverse-Length Application", items);
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname");
        int port = io.readNumberRange("Enter port", "Invalid port", 3000, 50000).intValue();

        if (client != null) {
            client.close();
        }
        client = new ReverseLengthClient(host, port);
        Menu sessionMenu = new Menu("Request Menu",
            Item.of("Reverse String", MainClient::reverseString),
            Item.of("Get String Length", MainClient::getStringLength),
            Item.of("Exit Session", MainClient::exit)
        );
        sessionMenu.perform(io);
    }

    static void reverseString(InputOutput io) {
        String input = io.readString("Enter a string:");
        String response = client.sendRequest("1", input);
        io.writeLine("Reversed String: " + response);
    }
    static void getStringLength(InputOutput io) {
        String input = io.readString("Enter a string:");
        String response = client.sendRequest("2", input);
        io.writeLine("String Length: " + response);
    }

    static void exit(InputOutput io) {
        if (client != null) {
            client.close();
        }
        System.exit(0);
    }
}