package com.argmaps.client.proto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fepss.rpc.client.RpcChannelImpl;

import com.google.protobuf.RpcController;

import com.argmaps.transport.proto.generated.TransportServer.ProtoService;
import com.argmaps.transport.proto.generated.TransportServer.DefaultLongValue;

public class TransportClient {

    protected final Log LOG = LogFactory.getLog(this.getClass().getName());

    private RpcController controller;

    private ProtoService.Interface service;

    private void open(String host, int port) {

        RpcChannelImpl channel = new RpcChannelImpl(host, port);
	    controller = channel.newRpcController();
	    service = ProtoService.newStub(channel);

    }

    private void close() {

    }

    public long getLongValue(LongValueRpcCallbackTemplate longValueRpcCallback) {

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


    // JMock constructor
    public TransportClient(RpcController controller, ProtoService.Interface service) {

        this.controller = controller;
        this.service = service;
    }
}
