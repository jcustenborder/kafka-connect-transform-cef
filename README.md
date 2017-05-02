# Configuration

## CEFTransformation

Transformation to convert a standard [Syslog](https://en.wikipedia.org/wiki/Syslog) message to a [Common Event Format (CEF)](https://www.protect724.hpe.com/docs/DOC-1072) Syslog struct.

```properties
transforms=ceftransformation
transforms.ceftransformation.type=com.github.jcustenborder.kafka.connect.transform.cef.CEFTransformation

# Set these required values
```

| Name             | Description                                                  | Type   | Default | Valid Values | Importance |
|------------------|--------------------------------------------------------------|--------|---------|--------------|------------|
| field.message    | The field that stores the message.                           | string | message |              | high       |
| topic.cef.suffix | The suffix to append to the topic when CEF data is detected. | string | .cef    |              | high       |


# Schemas

## com.github.jcustenborder.kafka.connect.transform.cef.CEFMessage

The parsed representation of a CEF Message.

| Name               | Optional | Schema                                                                                                                                                                                                                | Default Value | Documentation                                                                                |
|--------------------|----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------|----------------------------------------------------------------------------------------------|
| date               | true     | [Timestamp](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Timestamp.html)                                                                                                                       |               | The timestamp of the message.                                                                |
| facility           | true     | [Int32](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#INT32)                                                                                                                   |               | The facility of the message.                                                                 |
| host               | true     | [String](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#STRING)                                                                                                                 |               | The host of the message.                                                                     |
| level              | true     | [Int32](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#INT32)                                                                                                                   |               | The level of the syslog message as defined by [rfc5424](https://tools.ietf.org/html/rfc5424) |
| message            | false    | [String](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#STRING)                                                                                                                 |               | Unparsed version of the message.                                                             |
| cefVersion         | false    | [Int32](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#INT32)                                                                                                                   |               | Version of CEF the message is using.                                                         |
| deviceVendor       | false    | [String](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#STRING)                                                                                                                 |               | Vendor of the device that logged the message.                                                |
| deviceProduct      | false    | [String](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#STRING)                                                                                                                 |               | The product that logged the message.                                                         |
| deviceVersion      | false    | [String](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#STRING)                                                                                                                 |               | The version of the device that is logging the message.                                       |
| deviceEventClassId | false    | [String](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#STRING)                                                                                                                 |               | The internal event id for the message.                                                       |
| name               | false    | [String](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#STRING)                                                                                                                 |               | Name of the event. This is typically a short description.                                    |
| severity           | false    | [String](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#STRING)                                                                                                                 |               | The severity of the message.                                                                 |
| extensions         | true     | Map of <[String](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#STRING), [String](https://kafka.apache.org/0102/javadoc/org/apache/kafka/connect/data/Schema.Type.html#STRING)> |               | Key value pairs of any extensions to the message.                                            |