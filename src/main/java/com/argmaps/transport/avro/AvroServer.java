package com.argmaps.transport.avro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;

import org.apache.avro.ipc.SocketServer;
import org.apache.avro.specific.SpecificResponder;

import com.argmaps.transport.avro.generated.TransportServer;

public class AvroServer {

    public static class TransportServerImpl implements TransportServer {

        protected final Log LOG = LogFactory.getLog(this.getClass().getName());

        public long getLongValue()  {

            long longValue = 123456789L;
            if (LOG.isDebugEnabled()) {
                LOG.debug("getLongValue() called: " + longValue);
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
        System.out.println("Usage: java com.argmaps.protocol.avro.AvroServer " + "--help | [--port=PORT] start");
        System.out.println("Arguments:");
        System.out.println(" start Start avro server");
        System.out.println(" stop  Stop avro server");
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
        Log LOG = LogFactory.getLog("AvroServer");
        LOG.info("starting Avro server on port " + Integer.toString(port));

        SocketServer server = new SocketServer(new SpecificResponder(TransportServer.class, new TransportServerImpl()), new InetSocketAddress("0.0.0.0", port));

        while (true) {
            LOG.info("Avro server is waiting for incoming connections");
            Thread.sleep(Long.MAX_VALUE);
        }
    }

    public static void main(String[] args) throws Exception {

        doMain(args);
    }
}
