package nio.client;

import java.util.Scanner;

public class NioClientRunnerSecond {

    public static void main(String[] args) {

        NioClient ruslanAbdillaev = new NioClient("Ruslan Abdillaev");
        ruslanAbdillaev.connect(
                8090, "localhost", new Scanner(System.in)
        );
    }
}
