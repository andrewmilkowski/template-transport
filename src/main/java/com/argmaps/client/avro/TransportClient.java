package com.argmaps.client.avro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.avro.ipc.SocketTransceiver;
import org.apache.avro.specific.SpecificRequestor;
import org.apache.avro.ipc.AvroRemoteException;

import com.argmaps.transport.avro.generated.TransportServer;

import java.net.InetSocketAddress;

public class TransportClient {

    protected final Log LOG = LogFactory.getLog(this.getClass().getName());

    private SocketTransceiver client;
    private TransportServer transportServer;
    private long longValue = 0L;

    private void open(int port) {

        try {
            client = new SocketTransceiver(new InetSocketAddress(port));
            transportServer = (TransportServer) SpecificRequestor.getClient(TransportServer.class, client);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getLongValue() {

        longValue = 0L;

        try {
            longValue = transportServer.getLongValue();
        } catch(AvroRemoteException e) {
            e.printStackTrace();
        }

        return longValue;
    }

    public static void main(String[] args) {

        int port = 9090;
        final String portArgKey = "--port=";
        for (String cmd : args) {
            if (cmd.startsWith(portArgKey)) {
                port = Integer.parseInt(cmd.substring(portArgKey.length()));
                break;
            }
        }

        TransportClient c = new TransportClient();
        c.open(port);
        c.getLongValue();
        c.close();
    }

    public TransportClient() {
        
    }

    // JMock constructor
    public TransportClient(TransportServer transportServer) {

	    this.transportServer = transportServer;
    }
}
