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

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

class CEFTransformationConfig extends AbstractConfig {

  public static final String TOPIC_CEF_CONF = "topic.cef.suffix";
  static final String TOPIC_CEF_DOC = "The suffix to append to the topic when CEF data is detected.";
  static final String TOPIC_CEF_DEFAULT = ".cef";


  public static final String FIELD_MESSAGE_CONF = "field.message";
  static final String FIELD_MESSAGE_DOC = "The field that stores the message.";
  static final String FIELD_MESSAGE_DEFAULT = "message";


  public final String fieldMessage;
  public final String topicSuffix;

  public CEFTransformationConfig(Map<String, ?> parsedConfig) {
    super(config(), parsedConfig);
    this.fieldMessage = getString(FIELD_MESSAGE_CONF);
    this.topicSuffix = getString(TOPIC_CEF_CONF);
  }

  static ConfigDef config() {
    return new ConfigDef()
        .define(TOPIC_CEF_CONF, ConfigDef.Type.STRING, TOPIC_CEF_DEFAULT, ConfigDef.Importance.HIGH, TOPIC_CEF_DOC)
        .define(FIELD_MESSAGE_CONF, ConfigDef.Type.STRING, FIELD_MESSAGE_DEFAULT, ConfigDef.Importance.HIGH, FIELD_MESSAGE_DOC);
  }
}
