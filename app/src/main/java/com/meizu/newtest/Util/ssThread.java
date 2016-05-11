package com.meizu.newtest.Util;

import android.util.Log;

import com.meizu.newtest.Activity.wifiAdb;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by libinhui on 2016/2/15.
 */
public class ssThread extends Thread {

    private static final String LOG_TAG = "MyServerThread";

    private wifiAdb m_activityMain;

    public ssThread(wifiAdb activityMain)
    {
        super();

        // Save the activity
        m_activityMain = activityMain;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                // Wait for new client connection
                Log.i(LOG_TAG, "Waiting for client connection...");
                Socket socketClient = m_activityMain.m_serverSocket.accept();

                Log.i(LOG_TAG, "Accepted connection from " + socketClient.getInetAddress().getHostAddress());

                // Read input from client socket
                InputStream is = socketClient.getInputStream();
                OutputStream os = socketClient.getOutputStream();
                DataInputStream dis = new DataInputStream(is);
                while (!socketClient.isClosed())
                {
                    // Read a line
                    String sLine = dis.readLine();
                    Log.i(LOG_TAG, "Read client socket=[" + sLine + "]");
                    if (sLine == null)
                    {
                        break;
                    }
                }

                // Close streams
                dis.close();
                os.close();
                is.close();

                // Close client socket
                Log.i(LOG_TAG, "Read data from client ok. Close connection from " + socketClient.getInetAddress().getHostAddress());
                socketClient.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            // Stop loop when server socket is closed
            if (m_activityMain.m_serverSocket.isClosed())
            {
                break;
            }
        }
    }

}
