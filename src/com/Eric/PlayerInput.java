package com.Eric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Queue;

import static com.Eric.PieceColor.*;

/**
 * Created by Eric on 3/19/2016.
 */
public class PlayerInput implements Runnable{

    BufferedReader in;
    Queue<Action> actionQueue;

    public void run()
    {
        loop();

    }

    public PlayerInput(InputStream inputStream, Queue<Action> actionQueue)
    {
        in = new BufferedReader(new InputStreamReader(inputStream));
        this.actionQueue = actionQueue;
    }

    private void loop()
    {
        String input;
        boolean running = true;
        while(running)
        {
            try
            {
                input = in.readLine(); //sanitize input
                actionQueue.add(new Action(input));
                if(input == null)
                {
                    in.close();
                    running = false;
                }
            }catch(IOException ioe)
            {
                //ioe.printStackTrace(); //handle better
            }

        }

    }


}
