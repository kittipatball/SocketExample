package com.kittipat.socket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Socket client;
    private PrintWriter printWriter;
    private EditText edtMessage, edtIPAddress, edtPort;
    private Button btnSend;
    private String message;
    int port = 0;
    int timeout = 10000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtIPAddress = (EditText) findViewById(R.id.edtIPAddress);
        edtMessage = (EditText) findViewById(R.id.edtMessage);
        edtPort = (EditText) findViewById(R.id.edtPort);
        btnSend = (Button) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = edtMessage.getText().toString();
                port = Integer.parseInt(edtPort.getText().toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            client = new Socket();
                            client.connect(new InetSocketAddress(edtIPAddress.getText().toString(), port),timeout);
                            printWriter = new PrintWriter(client.getOutputStream());
                            printWriter.write(message);
                            if (printWriter.checkError()) {
                                Toast.makeText(getApplicationContext(),"Write Error",Toast.LENGTH_SHORT).show();
                            }

                            printWriter.flush();
                            if (!printWriter.checkError()) {
                                String str = message;
                            }
                            printWriter.close();
                            client.close();

                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("Socket Error", e.getMessage());
                        }
                    }
                }).start();
            }
        });
    }

}
