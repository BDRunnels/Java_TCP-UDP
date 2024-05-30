package dev.bdr;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class SimpleServerChannel {
    public static void main(String[] args) {
        
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) { // .open opens a new server socket channel.

            serverChannel.socket().bind(new InetSocketAddress(5001));
            serverChannel.configureBlocking(false);
            System.out.println("Server is listening on port: " + serverChannel.socket().getLocalPort());

            
            List<SocketChannel> clientChannels = new ArrayList<>();

            while (true) {
                // System.out.println("Waiting to connect to another client");
                SocketChannel clientChannel = serverChannel.accept();
                if (clientChannel != null) {
                    clientChannel.configureBlocking(false);
                    clientChannels.add(clientChannel);

                    System.out.printf("Client %s connected%n", clientChannel.socket().getRemoteSocketAddress());
                }
                
                ByteBuffer buffer = ByteBuffer.allocate(1024); // 2^10 -> standard buffer size

                for (int i = 0; i < clientChannels.size(); i++) {
                    SocketChannel channel = clientChannels.get(i);
                    // System.out.println("Waiting on client request data.");
                    int readBytes = channel.read(buffer);

                    if (readBytes > 0) {
                        buffer.flip(); // flips from readable to writable
                        channel.write(ByteBuffer.wrap("Echo from server: ".getBytes()));
                        while (buffer.hasRemaining()) {
                            channel.write(buffer);
                        }
                        buffer.clear();
                    } else if (readBytes == -1) {
                        System.out.printf("Connected to %s lost%n", channel.socket().getRemoteSocketAddress());
                        channel.close();
                        clientChannels.remove(i);
                    }
                }
                
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
}
