package com.Eric;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Eric on 2/11/2016.
 */
public class Server {

    ServerSocket serverSocket;
    Socket[] players;
    ArrayList<Thread> gameThreads;
    public Server() {
        try {
            this.serverSocket = new ServerSocket(24377);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        gameThreads = new ArrayList<>();
        start();
    }

    public void start()
    {
        players = new Socket[2];
        do {
            try {
                //creates 2 sockets from first 2 incoming connections.
                //gives sockets to game object, creates and starts a game thread
                //repeats process
                players[0] = serverSocket.accept();
                players[1] = serverSocket.accept();
                gameThreads.add(new Thread(new Game(players), ("" + (gameThreads.size() - 1))));
                gameThreads.get(gameThreads.size() - 1).start();
                players = new Socket[2];
            }catch(IOException ioe)
            {
                ioe.printStackTrace();
            }


        }while(true);

    }
}
