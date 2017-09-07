package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import message.MessageModel;

public class Server implements Runnable {

    private static final int BUFFER_SIZE = 1024;

    private InetSocketAddress address;

    private ServerSocketChannel serverChannel;

    private Selector selector;

    public Server(String host, int port) throws IOException {
        this.address = new InetSocketAddress(host, port);	//Creates a socket address from a hostname and a port number
        this.selector = initSelector();
    }

    public void start() {
        try {
            logger(String.format("Listening for connections on \n---- HOST: %s  ---- PORT: %d...", address.getHostName(), address.getPort()));

            while (true) {						//Returns:The number of keys, possibly zero, whose ready-operation sets were updated
                selector.select();			//Selects a set of keys whose corresponding channels are ready for I/O operations.
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove(); //remove the key so that we don't process this OPERATION again.
                 // key could be invalid if for example, the client closed the connection.
                    if (!key.isValid()) {
                        continue;
                        /**
                         * In the server, we start by listening to the OP_ACCEPT when we register with the Selector.
                         * If the key from the keyset is Acceptable, then we must get ready to accept the client
                         * connection and do something with it. Go read the comments in the accept method.
                         */
                    } else if (key.isAcceptable()) {
                        accept(key);
                        
                        /**
                         * If you already read the comments in the write method then you understand that we registered
                         * the OPERATION OP_READ. That means that on the next Selector.select(), there is probably a key
                         * that is ready to read (key.isReadable()). The read() method will explain further. 
                         */
                    } else if (key.isReadable()) {
                        read(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger("Error occured during server start.");
            System.exit(0);
        }
    }

    public void stop() {
        try {
            serverChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger("Error occured during server stop.");
        }
    }

    @Override
    public void run() {
        while (true) {
            start();
        }
    }

    private Selector initSelector() throws IOException {
        Selector socketSelector = SelectorProvider.provider().openSelector();

        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(address); //Binds the ServerSocket to a specific address (IP address and port number). 
        serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);

        //Here you are registering the serverSocketChannel to accept connection, thus the OP_ACCEPT.
        //* This means that you just told your selector that this channel will be used to accept connections.
        
        return socketSelector;
    }

    /**
     * Since we are accepting, we must instantiate a serverSocketChannel by calling key.channel().
     * We use this in order to get a socketChannel (which is like a socket in I/O) by calling
     *  serverSocketChannel.accept() and we register that channel to the selector to listen 
     *  to a WRITE OPERATION. I do this because my server sends a hello message to each
     *  client that connects to it. This doesn't mean that I will write right NOW. It means that I
     *  told the selector that I am ready to write and that next time Selector.select() gets called
     *  it should give me a key with isWritable(). More on this in the write() method.
     */
    private void accept(SelectionKey key) throws IOException {
        logger("Accepting connection");

        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * We read data from the channel. In this case, my server works as an echo, so it calls the echo() method.
     * The echo() method, sets the server in the WRITE OPERATION. When the while loop in run() happens again,
     * one of those keys from Selector.select() will be key.isWritable() and this is where the actual
     * write will happen by calling the write() method.
     */
    
    private void read(SelectionKey key) throws IOException {
        logger("Reading data from client");

        SocketChannel clientChannel = (SocketChannel) key.channel();

        MessageModel message = new MessageModel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.clear();
        int read = 0;

        try {
            read = clientChannel.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            key.cancel();
            clientChannel.close();
            return;
        }

        if (read == -1) {
            key.channel().close();
            key.cancel();
            return;
        }

        if (read > 0) {

            InputStream bais;
            buffer.flip();
            try (ObjectInputStream ois = new ObjectInputStream(bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit()))) {
                message = (MessageModel) ois.readObject();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                key.cancel();
                key.channel().close();
                clientChannel.close();
                return;
            }
        } else {
            logger("Nothing has been read. \n");
        }

        if (message.getMessage().length() > 0 && message.getUserName().length() > 0 && message.getType() != 0) {
            logger(String.format("%s\n", message.toString()));
            broadcast(message, key);
        } else {
            logger("Fields: message, userName and type are empty.\n");
        }
    }

    private void broadcast(MessageModel msg, SelectionKey k) throws IOException {
        logger("Broadcasting message to all clients: " + msg.getMessage());

        ByteArrayOutputStream baos;

        for (SelectionKey key : selector.keys()) {
            if (key.isValid() && key.channel() instanceof SocketChannel && key != k) {
                SocketChannel sch = (SocketChannel) key.channel();
                sch.register(selector, SelectionKey.OP_WRITE);
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                buffer.flip();

                try (ObjectOutputStream outputStream = new ObjectOutputStream(baos = new ByteArrayOutputStream())) {
                    outputStream.reset();
                    outputStream.writeObject(msg);
                    outputStream.flush();
                    buffer = ByteBuffer.wrap(baos.toByteArray());
                    sch.write(buffer);
                    outputStream.flush();
                    baos.flush();
                    sch.register(selector, SelectionKey.OP_READ);

                } catch (IOException e) {
                    e.printStackTrace();
                    logger("Error while sending the message: " + msg.getMessage());
                    return;
                }
            }
        }
    }

    public static void logger(String msg) {
        System.out.println(msg);
    }
}
