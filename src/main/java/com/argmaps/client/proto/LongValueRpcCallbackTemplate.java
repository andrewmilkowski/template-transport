package com.argmaps.client.proto;

import com.google.protobuf.RpcCallback;
import com.argmaps.transport.proto.generated.TransportServer.LongValue;

abstract class LongValueRpcCallbackTemplate implements RpcCallback<LongValue> {

    @Override
    public void run(LongValue result) {

    }

    protected long getLongValue() {

        return 0;
    }
}
