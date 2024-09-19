package io.server;

import java.io.IOException;

public class IoServerRunner {

    public static void main(String[] args) throws IOException {

        IoServer ioServer = new IoServer();
        ioServer.start(8090);
    }
}
