package com.argmaps.client.avro;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.Expectations;

import com.argmaps.transport.avro.generated.TransportServer;
import org.apache.avro.ipc.AvroRemoteException;

@RunWith(JMock.class)
public class TransportClientTest {

    Mockery context;

    @Before
    public void before() {

        context = new JUnit4Mockery();
    }

    @Test
    public void testGetLongValue() throws AvroRemoteException {

        final TransportServer mockedTransportServer = context.mock(TransportServer.class);
        TransportClient testObject = new TransportClient(mockedTransportServer);

        final long testLongValue = 123456789L;

        context.checking(new Expectations() {
            {
                one(mockedTransportServer).getLongValue();
                will(returnValue(testLongValue));
            }
        });
        assertEquals(testLongValue, testObject.getLongValue());
    }
}