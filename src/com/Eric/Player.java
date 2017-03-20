package com.Eric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;

public class Player{
    private PrintWriter out;
    private Queue<Action> actionQueue;
    private Socket socket;
    private Thread playerInputThread;

    public Player(Socket socket, Queue<Action> actionQueue)
    {
        this.actionQueue = actionQueue;
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            playerInputThread = new Thread(new PlayerInput(socket.getInputStream(), actionQueue), Thread.currentThread().getName() + " Input");
            playerInputThread.start();
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

    }



    public void send(Action action)
    {
        out.println(action.toString());
    }

    public boolean isClosed()
    {
        return socket.isClosed();
    }






}
