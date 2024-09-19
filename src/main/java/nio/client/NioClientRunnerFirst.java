package nio.client;

import java.util.Scanner;

public class NioClientRunnerFirst {

    public static void main(String[] args) {

        NioClient slavaBurgas = new NioClient("Slava Burgas");
        slavaBurgas.connect(
                8090, "localhost", new Scanner(System.in)
        );
    }
}
