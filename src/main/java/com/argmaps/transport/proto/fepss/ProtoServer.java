package com.argmaps.transport.proto.fepss;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandler;

import com.fepss.rpc.server.impl.RpcIoHandler;
import com.fepss.rpc.server.impl.RpcServerImpl;

import com.google.protobuf.RpcController;
import com.google.protobuf.Service;
import com.google.protobuf.RpcCallback;

import com.argmaps.transport.proto.generated.TransportServer.ProtoService;
import com.argmaps.transport.proto.generated.TransportServer.DefaultLongValue;
import com.argmaps.transport.proto.generated.TransportServer.LongValue;
import com.argmaps.transport.proto.generated.TransportServer.LongValue.Builder;


public class ProtoServer {

    public static class TransportHandler extends ProtoService {

        protected final Log LOG = LogFactory.getLog(this.getClass().getName());

        @Override
        public void getLongValue(RpcController controller, DefaultLongValue request, RpcCallback<LongValue> done) {

            long longValue = 123456789L;

            Builder builder = LongValue.newBuilder();
            builder.setLongValue(longValue);
            builder.setSuccess(true);
            done.run(builder.build());
            if (LOG.isDebugEnabled()) {
                LOG.debug("getLongValue() called: " + longValue);
            }
        }
    }

    private static void printUsageAndExit() {

        printUsageAndExit(null);
    }

    private static void printUsageAndExit(final String message) {

        if (message != null) {
            System.err.println(message);
        }
        System.out.println("Usage: java com.argmaps.protocol.proto.ProtoServer " + "--help | [--port=PORT] start");
        System.out.println("Arguments:");
        System.out.println(" start Start proto server");
        System.out.println(" stop  Stop proto server");
        System.out.println("Options:");
        System.out.println(" port  Port to listen on. Default: 9090");
        System.out.println(" help  Print this message and exit");
        System.exit(0);
    }

    protected static void doMain(final String[] args) throws Exception {

        if (args.length < 1) {
            printUsageAndExit();
        }

        String host = "localhost";
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

        Log LOG = LogFactory.getLog("ProtoServer");
        LOG.info("starting Proto server on port " + Integer.toString(port));

        Map<String, Service> services = new HashMap<String, Service>();
        services.put(ProtoService.getDescriptor().getFullName(), new TransportHandler());
        IoHandler handler = new RpcIoHandler(services);

        // start rpc server
        RpcServerImpl server = new RpcServerImpl(host, port, handler);
        server.start();

    }

    public static void main(String[] args) throws Exception {

        doMain(args);
    }
}
