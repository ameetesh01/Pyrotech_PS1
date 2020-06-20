package com.example.pyrotechapp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import static android.content.ContentValues.TAG;
public class connectThread extends Thread{
    private final BluetoothSocket btS;
    private final BluetoothDevice btD;
    public connectThread(BluetoothDevice device, UUID myuuid){
        BluetoothSocket tmp = null;
        btD = device;
        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createRfcommSocketToServiceRecord(myuuid);
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        btS = tmp;
    }
    public void run(BluetoothAdapter btadapt) {
        // Cancel discovery because it otherwise slows down the connection.
        //BluetoothAdapter btadapt = BluetoothAdapter.getDefaultAdapter();
        btadapt.cancelDiscovery();

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            btS.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                btS.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        //manageMyConnectedSocket(btS);
    }
    public void cancel() {
        try {
            btS.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the client socket", e);
        }
    }
}
