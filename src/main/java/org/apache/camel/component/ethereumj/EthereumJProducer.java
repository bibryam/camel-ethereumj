/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.ethereumj;

import org.apache.camel.InvokeOnHeader;
import org.apache.camel.Message;
import org.apache.camel.impl.HeaderSelectorProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The ethereumj producer.
 */

public class EthereumJProducer extends HeaderSelectorProducer {
    private static final Logger LOG = LoggerFactory.getLogger(org.apache.camel.component.ethereumj.EthereumJProducer.class);
    private EthereumJConfiguration configuration;
    private EthereumJEndpoint endpoint;

    public EthereumJProducer(EthereumJEndpoint endpoint, final EthereumJConfiguration configuration) {
        super(endpoint, EthereumJConstants.OPERATION, () -> configuration.getOperationOrDefault(), false);
        endpoint = endpoint;
        this.configuration = configuration;
    }

    @Override
    public EthereumJEndpoint getEndpoint() {
        return (EthereumJEndpoint) super.getEndpoint();
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }

    @InvokeOnHeader(EthereumJConstants.WEB3_CLIENT_VERSION)
    void web3ClientVersion(Message message) throws IOException {
//        Request<?, Web3ClientVersion> web3ClientVersionRequest = web3j.web3ClientVersion();
        message.setBody("hello");
    }

}
