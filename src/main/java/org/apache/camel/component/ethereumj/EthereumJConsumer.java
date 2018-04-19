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

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.ethereum.core.Block;
import org.ethereum.core.TransactionReceipt;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;
import org.ethereum.facade.Repository;
import org.ethereum.listener.EthereumListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.util.List;

/**
 * The ethereumj consumer.
 */
public class EthereumJConsumer extends DefaultConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(org.apache.camel.component.ethereumj.EthereumJConsumer.class);
    private final EthereumJConfiguration configuration;
    private EthereumJEndpoint endpoint;

    public EthereumJConsumer(EthereumJEndpoint endpoint, Processor processor, EthereumJConfiguration configuration) {
        super(endpoint, processor);
        this.endpoint = endpoint;
        this.configuration = configuration;
    }

    @Override
    public EthereumJEndpoint getEndpoint() {
        return (EthereumJEndpoint) super.getEndpoint();
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        LOG.info("Subscribing: " + this.configuration);


        Ethereum ethereum = EthereumFactory.createEthereum();
        ethereum.addListener(new FollowAccount(ethereum));


        LOG.info("Subscribed: " + this.configuration);
    }


    class FollowAccount extends EthereumListenerAdapter {


        Ethereum ethereum = null;

        public FollowAccount(Ethereum ethereum) {
            this.ethereum = ethereum;
        }

        @Override
        public void onBlock(Block block, List<TransactionReceipt> receipts) {

            System.out.println("NEW BLOCK " + block );
            System.out.println("NEW receipts " + receipts.size() );

            byte[] cow = Hex.decode("cd2a3d9f938e13cd947ec05abc7fe734df8dd826");

            // Get snapshot some time ago - 10% blocks ago
            long bestNumber = ethereum.getBlockchain().getBestBlock().getNumber();
            long oldNumber = (long) (bestNumber * 0.9);

            Block oldBlock = ethereum.getBlockchain().getBlockByNumber(oldNumber);

            Repository repository = ethereum.getRepository();
            Repository snapshot = ethereum.getSnapshotTo(oldBlock.getStateRoot());

            BigInteger nonce_ = snapshot.getNonce(cow);
            BigInteger nonce = repository.getNonce(cow);

            processDone(block.getShortDescr());
//            System.err.println(" #" + block.getNumber() + " [cd2a3d9] => snapshot_nonce:" +  nonce_ + " latest_nonce:" + nonce);
        }
    }


    private void processDone(String data) {
        LOG.info("processDone ");
        Exchange exchange = this.getEndpoint().createExchange();
        exchange.getIn().setBody(data);
        processEvent(exchange);
    }

    public void processEvent(Exchange exchange) {
        LOG.info("processEvent " + exchange);
        try {
            getProcessor().process(exchange);
        } catch (Exception e) {
            LOG.error("Error processing event ", e);
        }
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }
}
