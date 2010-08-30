package com.argmaps.transport.thrift;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.TException;

import com.argmaps.transport.thrift.generated.TransportServer;

public class ThriftServer {

    public static class TransportHandler implements TransportServer.Iface {

        protected final Log LOG = LogFactory.getLog(this.getClass().getName());

        @Override
        public long getLongValue() throws TException {
            long longValue = 123456789L;
            if (LOG.isDebugEnabled()) {
                    LOG.debug("longValue() called: " + longValue);
            }
            return longValue;
        }
    }

    private static void printUsageAndExit() {

        printUsageAndExit(null);
    }

    private static void printUsageAndExit(final String message) {

        if (message != null) {
            System.err.println(message);
        }
        System.out.println("Usage: java com.argmaps.protocol.thrift.ThriftServer " + "--help | [--port=PORT] start");
        System.out.println("Arguments:");
        System.out.println(" start Start thrift server");
        System.out.println(" stop  Stop thrift server");
        System.out.println("Options:");
        System.out.println(" port  Port to listen on. Default: 9090");
        System.out.println(" help  Print this message and exit");
        System.exit(0);
    }

    protected static void doMain(final String[] args) throws Exception {

        if (args.length < 1) {
            printUsageAndExit();
        }

        int port = 9090;

        final String portArgKey = "--port=";
        for (String cmd : args) {
            if (cmd.startsWith(portArgKey)) {
                port = Integer.parseInt(cmd.substring(portArgKey.length()));
                continue;
            } else if (cmd.equals("--help") || cmd.equals("-h")) {
                printUsageAndExit();
            } else if (cmd.equals("start")) {
                continue;
            } else if (cmd.equals("stop")) {
                printUsageAndExit("To shutdown the thrift server run " +
                        "bin/transport-daemon.sh stop thrift or send a kill signal to " +
                        "the thrift server pid");
            }
            printUsageAndExit();
        }
        Log LOG = LogFactory.getLog("ThriftServer");
        LOG.info("starting Thrift server on port " + Integer.toString(port));
        TransportHandler handler = new TransportHandler();
        TransportServer.Processor processor = new TransportServer.Processor(handler);
        TServerTransport serverTransport = new TServerSocket(port);
        TProtocolFactory protFactory = new TBinaryProtocol.Factory(true, true);
        TServer server = new TThreadPoolServer(processor, serverTransport, protFactory);
        server.serve();
    }

    public static void main(String[] args) throws Exception {
        
        doMain(args);
    }
}
