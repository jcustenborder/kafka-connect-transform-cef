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

import com.github.jcustenborder.cef.Message;
import com.github.jcustenborder.cef.MessageFactory;
import org.apache.kafka.connect.data.Struct;


class CEFMessageFactory implements MessageFactory {
  final Struct value;

  CEFMessageFactory(Struct value) {
    this.value = value;
  }

  @Override
  public Message.Builder newBuilder() {
    return new CEFMessage.Builder(value);
  }
}
