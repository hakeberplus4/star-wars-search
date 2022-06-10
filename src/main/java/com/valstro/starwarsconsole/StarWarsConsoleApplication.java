package com.valstro.starwarsconsole;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import static java.lang.Thread.sleep;
import static java.util.Collections.singletonMap;

public class StarWarsConsoleApplication {

    static boolean readyForInput = true;
    static List<SearchResult> results = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Socket searchSocket = newSocket();
        searchSocket.on("search", peopleSearchListener());
        searchSocket.connect();

        while (true) {
            String userInput = readOneLine(scanner);
            if (userInput.startsWith("quit")) {
                System.exit(0);
            } else {
                readyForInput = false;
                searchSocket.emit("search", new JSONObject(singletonMap("query", userInput)));
            }
        }
    }

    private static Emitter.Listener peopleSearchListener() {
        return searchResults -> {
            try {
                String stringResult = searchResults[0].toString();
                SearchResult searchResult = new ObjectMapper().readValue(stringResult, SearchResult.class);
                results.add(searchResult);
                if (searchResult.page == searchResult.resultCount) {
                    // Sort results
                    Collections.sort(results);
                    results.forEach(result -> {
                      System.out.println(result.name);
                    });
                    readyForInput = true;
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private static Socket newSocket() {
        URI uri = URI.create("http://localhost:3000");
        IO.Options options = IO.Options.builder()
                .setForceNew(false)
                .setTransports(new String[]{WebSocket.NAME})
                .build();
        return IO.socket(uri, options);
    }

    private static String readOneLine(Scanner scanner) {
        while (!readyForInput) {
            try {
                sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\nEnter Star Wars character name:");
        return scanner.nextLine();
    }
}
