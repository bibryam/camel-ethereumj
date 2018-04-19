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

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ethereumj component uses the Web3j client API and allows you to add/read nodes to/from a ethereumj compliant content repositories.
 */
@UriEndpoint(firstVersion = "2.20.0", scheme = "ethereumj", title = "ethereumj", syntax = "ethereumj:cmsUrl", consumerClass = EthereumJConsumer.class, label = "ethereumj,blockchain")
public class EthereumJEndpoint extends DefaultEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(org.apache.camel.component.ethereumj.EthereumJEndpoint.class);

    @UriPath(description = "URL to the ethereumj repository")
    @Metadata(required = "true")
    private EthereumJConfiguration configuration;

    public EthereumJEndpoint(String uri, String remaining, EthereumJComponent component, EthereumJConfiguration configuration) {
        super(uri, component);
        this.configuration = configuration;
     }

    @Override
    public Producer createProducer() throws Exception {
        return new EthereumJProducer(this, configuration);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        EthereumJConsumer consumer = new EthereumJConsumer(this, processor, configuration);
        configureConsumer(consumer);
        return consumer;
    }

    public boolean isSingleton() {
        return true;
    }



}
