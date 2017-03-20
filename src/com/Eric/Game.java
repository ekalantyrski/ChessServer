package com.Eric;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import static com.Eric.PieceColor.*;
import static com.Eric.ActionType.*;

public class Game implements Runnable {

    private Player[] players;
    private Queue<Action> actionQueue;
    private boolean running;
    private Action currentAction;


    //creates a usable connection to both players
    //a queue is given where all actions are stored
    public Game(Socket[] playerSockets)
    {
        players = new Player[2];
        actionQueue = new LinkedList<>();
        players[0] = new Player(playerSockets[0], actionQueue);
        players[1] = new Player(playerSockets[1], actionQueue);


    }

    public synchronized void run()
    {
        startGame();
    }


    private void startGame()
    {
        //first connection will be white
        //second connection will be black
        players[0].send(new Action(WHITE, CHAT, "Playing as White"));
        players[1].send(new Action(BLACK, CHAT, "Playing as Black"));
        running = true;
        //gameloop
        while(running)
        {
            currentAction = actionQueue.poll();
            if(currentAction != null)
            {
                if(currentAction.getActionType() == EXIT) // code to send exit to player who did not exit
                {
                    System.out.println(currentAction.toString());
                    if(players[0].isClosed())
                    {
                        players[1].send(currentAction);
                        break;
                    }
                    else
                    {
                        players[0].send(currentAction);
                        break;
                    }
                }
                else {
                    switch (currentAction.getPieceColor()) { // code that sends what action happened
                        case BLACK:
                            players[0].send(currentAction);
                            break;
                        case WHITE:
                            players[1].send(currentAction);
                            break;
                    }
                }
            }



            try
            {
                Thread.sleep(5);
            }
            catch(InterruptedException ie)
            {
                running = false;

            }
        }


        System.out.println("Game over");
    }


}
