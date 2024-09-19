package nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashSet;
import java.util.Set;

public class NioServer {

    private final Set<SocketChannel> clients = new HashSet<>();

    public void start(int port) {

        try (
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                Selector selector = Selector.open()
        ){
            serverSocketChannel.bind(
                    new InetSocketAddress(port)
            );
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (serverSocketChannel.isOpen()) {

                if (selector.select() == 0) {
                    continue;
                }

                for (SelectionKey key : selector.selectedKeys()) {

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    if (key.isAcceptable()) {

                        if (key.channel() instanceof ServerSocketChannel channel) {
                            SocketChannel client = channel.accept();
                            Socket socket = client.socket();

                            String clientInfo = "Client: " + client.getLocalAddress() + " :: "
                                                + socket.getPort() + " is CONNECTED";

                            System.out.println(clientInfo);

                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                            clients.add(client);

                        } else {
                            throw new RuntimeException("Unknown channel");
                        }

                    } else if (key.isReadable()) {

                        if (key.channel() instanceof SocketChannel client) {

                            int read = client.read(byteBuffer);
                            if (read == -1) {
                                byteBuffer.flip();
                                Socket socket = client.socket();

                                String clientInfo = "Client: " + client.getLocalAddress() + " :: "
                                                    + socket.getPort() + " is DISCONNECTED";

                                System.out.println(clientInfo);
                                client.close();
                                clients.remove(client);
                                continue;
                            }
                            byteBuffer.flip();

                            System.out.print(
                                    new String(byteBuffer.array(), byteBuffer.position(), read)
                            );

                        } else {
                            throw new RuntimeException("Unknown channel");
                        }

                    }
                }

                selector.selectedKeys().clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    public Set<SocketChannel> getClients() {
        return clients;
    }
}
