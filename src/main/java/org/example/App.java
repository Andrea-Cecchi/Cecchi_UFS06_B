package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        HotelBroker.getInstance().addHotel(new Hotel(12,"hotel miramare","hotel a rimini",1200.0,true));
        HotelBroker.getInstance().addHotel(new Hotel(13,"hotel tirreno","hotel a bellaria",900.0,false));
        HotelBroker.getInstance().addHotel(new Hotel(14,"hotel adriatica","hotel a igea marina",1600,true));
        HotelBroker.getInstance().addHotel(new Hotel(15,"hotel milano","hotel a milano",2000.0,true));

        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
