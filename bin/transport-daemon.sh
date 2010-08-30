#!/usr/bin/env bash

usage="Usage: transport-daemon.sh (start|stop) <protocol-type> <args...>"

# if no args specified, show usage
if [ $# -le 1 ]; then
  echo $usage
  exit 1
fi

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

# get arguments
startStop=$1
shift

protocolType=$1
shift

case $startStop in

  (start)

  case $protocolType in

    (thrift)
  
      java -classpath ../target/classes:../lib/libthrift.jar:../lib/commons-logging-1.1.1.jar:../lib/slf4j-api-1.6.1.jar:../lib/slf4j-log4j12-1.6.1.jar:../lib/log4j-1.2.16.jar com.argmaps.transport.thrift.ThriftServer start

    ;;

    (avro)

      java -classpath ../target/classes:../lib/avro-1.3.3.jar:../lib/commons-logging-1.1.1.jar:../lib/slf4j-api-1.6.1.jar:../lib/slf4j-log4j12-1.6.1.jar:../lib/log4j-1.2.16.jar:../lib/jackson-core-asl-1.5.6.jar:../lib/jackson-mapper-asl-1.5.6.jar  com.argmaps.transport.avro.AvroServer start

    ;;

    (proto)

    java -classpath ../target/classes:../lib/protobuf-java-2.1.0.jar:../lib/fepss-rpc-0.2.1-SNAPSHOT.jar:../lib/mina-core-2.0.0-RC1.jar:../lib/commons-logging-1.1.1.jar:../lib/slf4j-api-1.6.1.jar:../lib/slf4j-log4j12-1.6.1.jar:../lib/log4j-1.2.16.jar com.argmaps.transport.proto.fepss.ProtoServer start
    ;;

    (*)
      echo $usage
      exit 1
    
    ;;
    esac

  ;;

  (stop)
    
  ps -eo pid,args | grep ThriftServer | awk '{print $1}' | xargs -I {} kill {}

  ;;

  (*)
    echo $usage
    exit 1
  ;;

esac
