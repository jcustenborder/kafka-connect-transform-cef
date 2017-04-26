/**
 * Copyright © 2017 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.transforms.Transformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Description("Transformation for converting string based CEF messages to ")
public class CEFTransformation<R extends ConnectRecord<R>> implements Transformation<R> {
  private static final Logger log = LoggerFactory.getLogger(CEFTransformation.class);
  CEFTransformationConfig config;
  Map<Schema, Schema> schemaLookup = new HashMap<>();


  @Override
  public ConnectRecord apply(ConnectRecord record) {
    if (null == record.valueSchema() || Schema.Type.STRUCT != record.valueSchema().type()) {
      log.trace("record.valueSchema() is null or record.valueSchema() is not a struct.");
      return record;
    }

    final Struct inputValue = (Struct) record.value();
    final String input = inputValue.getString(this.config.fieldMessage);
    Struct struct = new Struct(Constants.SCHEMA);
    CEFMessageFactory messageFactory = new CEFMessageFactory(struct);
    CEFParser parser = CEFParserFactory.create(messageFactory);
    Message message = parser.parse(input);

    return record.newRecord(
        record.topic(),
        record.kafkaPartition(),
        record.keySchema(),
        record.key(),
        struct.schema(),
        struct,
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
