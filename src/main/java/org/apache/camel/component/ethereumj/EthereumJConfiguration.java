package org.apache.camel.component.ethereumj;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

@UriParams
public class EthereumJConfiguration implements Cloneable {
    @UriParam(label = "producer", defaultValue = "transaction")
    private String data;
    private String operation = EthereumJConstants.TRANSACTION.toLowerCase();


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public EthereumJConfiguration copy() {
        try {
            return (EthereumJConfiguration)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeCamelException(e);
        }
    }


    public String getOperationOrDefault() {
        return this.operation != null ? operation : EthereumJConstants.TRANSACTION;
    }


}
