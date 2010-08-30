package com.argmaps.client.thrift;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import com.argmaps.transport.thrift.generated.TransportServer;

public class TransportClient {

    protected final Log LOG = LogFactory.getLog(this.getClass().getName());

    private TTransport transport;
	private TransportServer.Iface transportServer;
    private long longValue = 0L;

    private void open(int port) {

        try {
            transport = new TSocket("localhost", port);
            TProtocol protocol = new TBinaryProtocol(transport);
            transportServer = new TransportServer.Client(protocol);
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    private void close() {

        transport.close();

    }

    public long getLongValue() {

        longValue = 0L;

        try {
            longValue = transportServer.getLongValue();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Long value from server:" + longValue);
            }
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
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
    public TransportClient(TransportServer.Iface transportServer) {

	    this.transportServer = transportServer;
    }
}
