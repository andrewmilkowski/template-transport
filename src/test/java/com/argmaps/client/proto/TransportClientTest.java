package com.argmaps.client.proto;

import com.argmaps.transport.proto.generated.TransportServer;
import com.fepss.rpc.client.RpcChannelImpl;
import com.google.protobuf.RpcController;
import org.jmock.Expectations;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

import com.argmaps.transport.proto.generated.TransportServer.LongValue;

import static org.junit.Assert.assertEquals;

public class TransportClientTest {

    Mockery context;

    @Before
    public void before() {

        context = new JUnit4Mockery();

    }

    private class TestLongValueRpcCallback extends LongValueRpcCallbackTemplate {

        private long longValue = 123456789L;
        
        @Override
        protected long getLongValue() {

            return longValue;
        }
    }


    @Test
    public void testGetLongValue() {

        final TransportServer.ProtoService.Interface mockedTransportServer = context.mock(TransportServer.ProtoService.Interface.class);

        final RpcChannelImpl channel = new RpcChannelImpl("localhost", 9090);
	    final RpcController controller = channel.newRpcController();
        final TransportServer.DefaultLongValue defaultLongValue = TransportServer.DefaultLongValue.newBuilder().setLongValue(0L).build();

        com.argmaps.client.proto.TransportClient testObject = new TransportClient(controller, mockedTransportServer);

        final TestLongValueRpcCallback testLongValueRpcCallback = new TestLongValueRpcCallback();

        final long testLongValue = 123456789L;

        context.checking(new Expectations() {
            {
                one(mockedTransportServer).getLongValue(controller, defaultLongValue, testLongValueRpcCallback);
            }
        });

        assertEquals(testLongValue, testObject.getLongValue(testLongValueRpcCallback));

    }
}