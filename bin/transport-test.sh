#!/usr/bin/env bash

usage="Usage: transport-test.sh (thrift|avro|proto) <args...>"

# if no args specified, show usage
if [ $# -eq 0 ]; then
  echo $usage
  exit 1
fi

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

# get arguments
protocolType=$1
shift

command=$1
shift

case $protocolType in

  (thrift)

  java -classpath ../target/classes:../lib/libthrift.jar:../lib/commons-logging-1.1.1.jar:../lib/slf4j-api-1.6.1.jar:../lib/slf4j-log4j12-1.6.1.jar:../lib/log4j-1.2.16.jar:../lib/jackson-core-asl-1.5.6.jar:../lib/jackson-mapper-asl-1.5.6.jar com.argmaps.client.thrift.TransportClient
    
  ;;

  (avro)

  java -classpath ../target/classes:../lib/avro-1.3.3.jar:../lib/commons-logging-1.1.1.jar:../lib/slf4j-api-1.6.1.jar:../lib/slf4j-log4j12-1.6.1.jar:../lib/log4j-1.2.16.jar:../lib/jackson-core-asl-1.5.6.jar:../lib/jackson-mapper-asl-1.5.6.jar com.argmaps.client.avro.TransportClient

  ;;

  (proto)

  java -classpath ../target/classes:../lib/protobuf-java-2.1.0.jar:../lib/fepss-rpc-0.2.1-SNAPSHOT.jar:../lib/mina-core-2.0.0-RC1.jar:../lib/tapestry-core-5.2.0.jar:../lib/tapestry5-annotations-5.2.0.jar:../lib/tapestry-ioc-5.2.0.jar:../lib/tapestry-func-5.2.0.jar:../lib/javassist.jar:../lib/commons-logging-1.1.1.jar:../lib/slf4j-api-1.6.1.jar:../lib/slf4j-log4j12-1.6.1.jar:../lib/log4j-1.2.16.jar com.argmaps.client.proto.TransportClient
  
  ;;

  (*)
    echo $usage
    exit 1
    ;;

esac
