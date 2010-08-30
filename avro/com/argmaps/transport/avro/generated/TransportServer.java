package com.argmaps.transport.avro.generated;

@SuppressWarnings("all")
public interface TransportServer {
  public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"TransportServer\",\"namespace\":\"com.argmaps.transport.avro.generated\",\"types\":[],\"messages\":{\"getLongValue\":{\"request\":[],\"response\":\"long\"}}}");
  long getLongValue()
    throws org.apache.avro.ipc.AvroRemoteException;
}
