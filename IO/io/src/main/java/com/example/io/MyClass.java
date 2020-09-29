package com.example.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class MyClass {
    public static void main(String[] args) {
        //io1();
        //io2();
        //io3();
        //io4();
        //io5();
        //io6();
        //io7();
        //io8();
        //io9();
        io10();
    }

    public static void io1() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("./io/text1.text");
            outputStream.write('q');
            outputStream.write('w');
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(OutputStream outputStream1 = new FileOutputStream("./io/text1.text")) {
            outputStream1.write('q');
            outputStream1.write('w');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void io2() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("./io/text1.text");
            System.out.println((char)inputStream.read());
            System.out.println((char)inputStream.read());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void io3() {
        try(InputStream inputStream = new FileInputStream("./io/text1.text");
            Reader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader)
        ) {
            System.out.println(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Reader reader2 = null;
        BufferedReader bufferedReader2 = null;
        try {
            reader2 = new FileReader("./io/text1.text");
            bufferedReader2 = new BufferedReader(reader2);
            System.out.println(bufferedReader2.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (reader2 != null) {
                try {
                    reader2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    public static void io4() {
        try(OutputStream outputStream = new FileOutputStream("./io/text1.text")) {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write('q');
            bufferedOutputStream.write('a');
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void io5() {
        /*try (InputStream inputStream = new FileInputStream("./io/text1.text");
             OutputStream outputStream = new FileOutputStream("./io/text2.text"))
        {
            byte[] data = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream("./io/text1.text"));
             OutputStream outputStream = new BufferedOutputStream(new FileOutputStream("./io/text2.text")))
        {
            byte[] data = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void io6() {
        try (Socket socket = new Socket("hencoder.com", 80);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            //socket.getOutputStream();
            //socket.getInputStream();
            writer.write("GET / HTTP/1.1\n" +
                    "Host: www.example.com\n\n");
            writer.flush();
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void io7() {
        try (ServerSocket serverSocket = new ServerSocket(80);
             Socket socket = serverSocket.accept();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            writer.write("HTTP/1.1 200 OK\n" +
                    "Date: Mon, 23 May 2005 22:38:34 GMT\n" +
                    "Content-Type: text/html; charset=UTF-8\n" +
                    "Content-Length: 138\n" +
                    "Last-Modified: Wed, 08 Jan 2003 23:11:55 GMT\n" +
                    "Server: Apache/1.3.3.7 (Unix) (Red-Hat/Linux)\n" +
                    "ETag: \"3f80f-1b6-3e1cb03b\"\n" +
                    "Accept-Ranges: bytes\n" +
                    "Connection: close\n" +
                    "\n" +
                    "<html>\n" +
                    "  <head>\n" +
                    "    <title>An Example Page</title>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <p>Hello World, this is a very simple HTML document.</p>\n" +
                    "  </body>\n" +
                    "</html>\n\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void io8() {
        try {
            RandomAccessFile file = new RandomAccessFile("./io/text1.text", "r");
            FileChannel channel = file.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer);

            //byteBuffer.limit(byteBuffer.position());
            //byteBuffer.position(0);
            byteBuffer.flip();

            System.out.println(Charset.defaultCharset().decode(byteBuffer));
            byteBuffer.clear(); // reset
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void io9() {
        // 阻塞
        /*try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(80));
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (socketChannel.read(byteBuffer) != -1){
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // 非阻塞式 NIO
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(80));

            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select(); // 阻塞
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        while (socketChannel.read(byteBuffer) != -1) {
                            byteBuffer.flip();
                            socketChannel.write(byteBuffer);
                            byteBuffer.clear();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void io10() {
        /*try {
            Source source = Okio.source(new File("./io/text1.text"));
            Buffer buffer = new Buffer();
            source.read(buffer, 1024);
            System.out.println(buffer.readUtf8Line());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try(BufferedSource source = Okio.buffer(Okio.source(new File("./io/text1.text")))) {
            System.out.println(source.readUtf8Line());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
