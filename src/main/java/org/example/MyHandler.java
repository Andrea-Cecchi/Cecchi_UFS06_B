package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;

public class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();

        URI uri = exchange.getRequestURI();
        System.out.println(uri);

        String method = exchange.getRequestMethod();
        System.out.println(method);

        String s = read(is); // .. read the request body
        System.out.println(s);

        String myAnswer ="";

        if(method.equalsIgnoreCase("POST")){
            myAnswer = response(s);
        }else{
            myAnswer = response(uri.toString());
        }

        if(myAnswer.equals("not_found")){
            exchange.sendResponseHeaders(404,0);
            OutputStream os = exchange.getResponseBody();
            os.write("".getBytes());
            os.close();
        }
        String response = "<!doctype html>\n" +
                "<html lang=en>\n" +
                "<head>\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "  border-collapse: collapse;\n" +
                "}"+
                "</style>" +
                "<meta charset=utf-8>\n" +
                "<title>MyJava Sample</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "</br>The content:" +
                "</br>\n" +
                s +
                "</br>query:" +
                "</br>\n" +
                myAnswer +
                "</body>\n" +
                "</html>\n";

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private String read(InputStream is) {
        BufferedReader br = new BufferedReader( new InputStreamReader(is) );
        System.out.println("\n");
        String received = "";
        while (true) {
            String s = "";
            try {
                if ((s = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
            received += s;
        }
        return received;
    }

    public String response(String uri) {
        String[] res = uri.split("[/?&=]");
        ArrayList<String> ls = new ArrayList<>();
        if (res.length == 0) {
            return "No data";
        }

        for (String r : res) {
            if (!r.equals("")) {
                ls.add(r);
            }
        }

        String s = "";

        if(!ls.get(0).equals("command")){
            return "not_found";
        }

        if (ls.contains("all")) {
            //s = ls.get(3);
            s = "<table>\n" +
                    "<tr>\n" +
                    "<th>" +
                    "id" +
                    "</th>" +
                    "<th>" +
                    "Name" +
                    "</th>" +
                    "<th>" +
                    "Description" +
                    "</th>" +
                    "<th>" +
                    "price" +
                    "</th>" +
                    "<th>" +
                    "suite" +
                    "</th>" +
                    "</tr>\n";
            for (Hotel c:
                    HotelBroker.getInstance().getCars()) {
                s += "<tr>\n" +
                        "<td>" +
                        c.getId() +
                        "</td>\n" +
                        "<td>" +
                        c.getName() +
                        "</td>\n" +
                        "<td>" +
                        c.getDescription() +
                        "</td>\n"+
                        "<td>" +
                        c.getPrice() +
                        "</td>\n"+
                        "<td>" +
                        c.isSuite() +
                        "</td>\n"+
                        "</tr>\n";

            }
            s += "</table>";
            //s = DealerShip.getInstance().toJson();
        }else if(ls.contains("most_expensive")){
            Hotel mexp = HotelBroker.getInstance().getMoreExpensive();
            s = "<table>\n" +
                    "<tr>\n" +
                    "<th>" +
                    "id" +
                    "</th>" +
                    "<th>" +
                    "Nome" +
                    "</th>" +
                    "<th>" +
                    "descrizione" +
                    "</th>" +
                    "<th>" +
                    "price" +
                    "</th>" +
                    "<th>" +
                    "suite" +
                    "</th>" +
                    "</tr>\n"+
                    "<tr>\n" +
                    "<td>" +
                    mexp.getId() +
                    "</td>\n" +
                    "<td>" +
                    mexp.getName() +
                    "</td>\n" +
                    "<td>" +
                    mexp.getDescription() +
                    "</td>\n"+
                    "<td>" +
                    mexp.getPrice() +
                    "</td>\n"+
                    "<td>" +
                    mexp.isSuite() +
                    "</td>\n"+
                    "</tr>\n";


            s += "</table>";
        } else if (ls.contains("all_sorted")){
            s = "<table>\n" +
                    "<tr>\n" +
                    "<th>" +
                    "id" +
                    "</th>" +
                    "<th>" +
                    "Name" +
                    "</th>" +
                    "<th>" +
                    "Description" +
                    "</th>" +
                    "<th>" +
                    "Price" +
                    "</th>" +
                    "<th>" +
                    "Suite" +
                    "</th>" +
                    "</tr>\n";
            for (Hotel c:
                    HotelBroker.getInstance().toJsonSorted()) {
                s += "<tr>\n" +
                        "<td>" +
                        c.getId() +
                        "</td>\n" +
                        "<td>" +
                        c.getName() +
                        "</td>\n" +
                        "<td>" +
                        c.getDescription()+
                        "</td>\n" +
                        "<td>" +
                        c.getPrice()+
                        "</td>\n" +
                        c.isSuite() +
                        "</td>\n"+
                        "</tr>\n";

            }
            s += "</table>";
        } else {
            return "<p> query non trovata! prova con: all, most_expensive, all_sorted </p>";
        }

        return s;
    }
}
