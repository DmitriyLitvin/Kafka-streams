package com.example.kafka.broker.flash_sale;

import com.example.kafka.broker.message.FlashSaleVoteMessage;
import com.example.kafka.util.LocalDateTimeUtil;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.LocalDateTime;

public class FlashSaleTwoValueTransformer implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessage> {
    private final long voteStart;
    private final long endStart;
    private ProcessorContext processorContext;

    public FlashSaleTwoValueTransformer(LocalDateTime voteStart, LocalDateTime endStart) {
        this.voteStart = LocalDateTimeUtil.toEpochTimestamp(voteStart);
        this.endStart = LocalDateTimeUtil.toEpochTimestamp(endStart);
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.processorContext = processorContext;
    }

    @Override
    public FlashSaleVoteMessage transform(FlashSaleVoteMessage flashSaleVoteMessage) {
        long currentTime = processorContext.timestamp();
        return currentTime >= voteStart && currentTime <= endStart ? flashSaleVoteMessage :null;
    }

    @Override
    public void close() {

    }
}
