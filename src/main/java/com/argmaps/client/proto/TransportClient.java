package com.argmaps.client.proto;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fepss.rpc.server.RpcApplication;
import com.fepss.rpc.client.RpcChannelImpl;

import org.apache.tapestry5.ioc.MappedConfiguration;

import com.google.protobuf.RpcController;
import com.google.protobuf.RpcCallback;

import com.argmaps.transport.proto.generated.TransportServer.ProtoService;
import com.argmaps.transport.proto.generated.TransportServer.ProtoService.Stub;
import com.argmaps.transport.proto.generated.TransportServer.DefaultLongValue;
import com.argmaps.transport.proto.generated.TransportServer.LongValue;
import com.argmaps.transport.proto.fepss.ProtoServer.TransportHandler;

public class TransportClient {

    protected final Log LOG = LogFactory.getLog(this.getClass().getName());

    private RpcController controller;

    private TransportHandler transportHandler;
    private ProtoService.Interface service;

    private void open(String host, int port) {

        RpcChannelImpl channel = new RpcChannelImpl(host, port);
	    controller = channel.newRpcController();
	    service = ProtoService.newStub(channel);

    }

    protected static class LongValueRpcCallback implements RpcCallback<LongValue> {

        private long longValue = 0L;

        @Override
	    public void run(LongValue result) {
            longValue = result.getLongValue();
		}

        private long getLongValue() {

            return longValue;
        }
    }

    private void close() {

    }

    public long getLongValue(LongValueRpcCallback longValueRpcCallback) {

        DefaultLongValue defaultLongValue = DefaultLongValue.newBuilder().setLongValue(0L).build();

		service.getLongValue(controller, defaultLongValue, longValueRpcCallback);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Long value from server:" + longValueRpcCallback.getLongValue());
        }

        return longValueRpcCallback.getLongValue();

    }

    public static void main(String[] args) {

        String host = "localhost";
        int port = 9090;

        final String portArgKey = "--port=";
        for (String cmd : args) {
            if (cmd.startsWith(portArgKey)) {
                port = Integer.parseInt(cmd.substring(portArgKey.length()));
                break;
            }
        }

        TransportClient c = new TransportClient();
        c.open(host, port);
        c.getLongValue(new LongValueRpcCallback());
        c.close();
    }

    public TransportClient() {
        
    }

    public static class TransportModule {

		public static void contributeIoHandler(MappedConfiguration<String, ProtoService> configruation) {

			configruation.add(ProtoService.getDescriptor().getFullName(), new TransportHandler());
		}
	}
}
