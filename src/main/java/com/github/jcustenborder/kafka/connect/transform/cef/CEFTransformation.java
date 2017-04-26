/**
 * Copyright © 2017 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.connect.transform.cef;

import com.github.jcustenborder.cef.CEFParser;
import com.github.jcustenborder.cef.CEFParserFactory;
import com.github.jcustenborder.cef.Message;
import com.github.jcustenborder.kafka.connect.utils.config.Description;
import com.google.common.collect.ImmutableList;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.transforms.Transformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Description("Transformation to convert a standard [Syslog](https://en.wikipedia.org/wiki/Syslog) message to a " +
    "[Common Event Format (CEF)](https://www.protect724.hpe.com/docs/DOC-1072) Syslog struct.")
public class CEFTransformation<R extends ConnectRecord<R>> implements Transformation<R> {
  private static final Logger log = LoggerFactory.getLogger(CEFTransformation.class);
  CEFTransformationConfig config;

  Map<Schema, List<String>> fieldMappings = new HashMap<>();

  @Override
  public ConnectRecord apply(ConnectRecord record) {
    if (null == record.valueSchema() || Schema.Type.STRUCT != record.valueSchema().type()) {
      log.trace("record.valueSchema() is null or record.valueSchema() is not a struct.");
      return record;
    }

    final Struct inputStruct = (Struct) record.value();
    log.trace("apply() - Reading Syslog body from '{}' field.", this.config.fieldMessage);
    final String inputMessage = inputStruct.getString(this.config.fieldMessage);

    Struct outputStruct = new Struct(Constants.SCHEMA);
    CEFMessageFactory messageFactory = new CEFMessageFactory(outputStruct);
    CEFParser parser = CEFParserFactory.create(messageFactory);
    try {
      log.trace("apply() - parsing '{}'", inputMessage);
      Message message = parser.parse(inputMessage);
    } catch (IllegalStateException ex) {
      return record;
    }

    List<String> fieldsToCopy = this.fieldMappings.computeIfAbsent(inputStruct.schema(), new Function<Schema, List<String>>() {
      @Override
      public List<String> apply(Schema inputSchema) {
        List<String> fieldNames = new ArrayList<>(inputSchema.fields().size());
        for (Field inputField : inputSchema.fields()) {
          Field outputField = Constants.SCHEMA.field(inputField.name());
          if (null != outputField && inputField.schema().type() == outputField.schema().type()) {
            fieldNames.add(inputField.name());
          }
        }
        return ImmutableList.copyOf(fieldNames);
      }
    });

    for (String fieldName : fieldsToCopy) {
      log.trace("apply() - Copying field '{}'", fieldName);
      Object value = inputStruct.get(fieldName);
      outputStruct.put(fieldName, value);
    }

    return record.newRecord(
        record.topic() + this.config.topicSuffix,
        record.kafkaPartition(),
        record.keySchema(),
        record.key(),
        outputStruct.schema(),
        outputStruct,
        record.timestamp()
    );
  }

  @Override
  public ConfigDef config() {
    return CEFTransformationConfig.config();
  }

  @Override
  public void close() {

  }

  @Override
  public void configure(Map<String, ?> settings) {
    this.config = new CEFTransformationConfig(settings);


//    parser = CEFParserFactory.
  }
}
