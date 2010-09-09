package com.argmaps.client.proto;

import com.argmaps.transport.proto.generated.TransportServer;
import com.google.protobuf.RpcCallback;

import com.argmaps.transport.proto.generated.TransportServer.LongValue;

class LongValueRpcCallback extends LongValueRpcCallbackTemplate {

    private long longValue = 0L;

    @Override
    public void run(LongValue result) {
        
        longValue = result.getLongValue();
    }

    @Override
    protected long getLongValue() {

        return longValue;
    }
}
