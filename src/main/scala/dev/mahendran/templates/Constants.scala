package dev.mahendran.templates

object Constants {
  final val ZK_CONN_STRING = ""
  //Kafka Konstants
  final val KAFKA_CH_ROOT = "/"
  final val ZK_PATH = "/config/topics"
  final val BOOTSTRAP_SERVERS = ""
  final val ACKS = "all"
  final val BATCH_SIZE = 16384
  final val LINGER_MS = 100
  final val BUFFER_MEMORY = 3554432
  final val DATA_SERIALIZER = "com.icc.poc.DataDeserializer"
  final val KAFKA_RESET = "earliest"
  final val KAFKA_MAX_FETCH_BYTES = "16000"
}