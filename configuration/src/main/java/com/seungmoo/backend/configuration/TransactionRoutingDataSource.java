package com.seungmoo.backend.configuration;

import com.seungmoo.backend.configuration.constants.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@RequiredArgsConstructor
public class TransactionRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly() == false) {
            return TransactionType.READ_WRITE;
        }
        return TransactionType.READ_ONLY;
    }
}

