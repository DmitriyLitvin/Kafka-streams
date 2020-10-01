package com.example.kafka.broker.order;

import com.example.kafka.broker.message.OrderMessage;
import com.example.kafka.broker.message.OrderPatternMessage;
import com.example.kafka.broker.message.OrderRewardMessage;
import com.example.kafka.broker.serde.OrderRewardMessageSerde;
import com.example.kafka.broker.serde.OrderRewardPatternSerde;
import com.example.kafka.broker.serde.OrderSerde;
import com.example.kafka.util.CommodityStreamUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaStreamBrancher;
import org.springframework.kafka.support.serializer.JsonSerde;

import static com.example.kafka.util.CommodityStreamUtil.mapToOrderReward;

@Configuration
@Log4j2
public class CommodityOneStream {

    //@Bean
    public KStream<String, OrderMessage> kStreamCommodityTrading(StreamsBuilder streamsBuilder) {
        var string = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde(OrderRewardMessage.class);
        KStream<String, OrderMessage> maskedStream = streamsBuilder
                .stream("t.commodity.order", Consumed.with(string, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);
        maskedStream.to("t.commodity.order-masked", Produced.with(string, orderSerde));

        //1 sink stream
        KStream<String, OrderPatternMessage>[] patternMessageKStream = maskedStream
                .mapValues(CommodityStreamUtil::mapToOrderPattern)
                .branch(CommodityStreamUtil.isPlastic(), (k,v) -> true);

        patternMessageKStream[0].to("t.commodity.order-plastic", Produced.with(string, orderPatternSerde));
        patternMessageKStream[0].print(Printed.<String,OrderPatternMessage>toSysOut().withLabel("plastic"));
        patternMessageKStream[1].to("t.commodity.order-nonplastic", Produced.with(string, orderPatternSerde));
        patternMessageKStream[1].print(Printed.<String,OrderPatternMessage>toSysOut().withLabel("nonplastic"));

//        new KafkaStreamBrancher<String, OrderPatternMessage>()
//                .branch(CommodityStreamUtil.isPlastic(), kStream -> kStream.to("t.commodity.order-plastic",
//                        Produced.with(string, orderPatternSerde)))
//                .defaultBranch(kStream -> kStream.to("t.commodity.nonorder-plastic",
//                        Produced.with(string, orderPatternSerde)))
//                .onTopOf(maskedStream.mapValues(CommodityStreamUtil::mapToOrderPattern));

        //2 sink stream
        KStream<String, OrderRewardMessage> rewardMessageKStream = maskedStream
                .filter((k,v) -> v.getQuantity() > 200)
                .filterNot(CommodityStreamUtil.isCheap())
                .map((k,v) -> KeyValue.pair(k, mapToOrderReward(v)));
        rewardMessageKStream.to("t.commodity.pattern-two", Produced.with(string, orderRewardSerde));
        rewardMessageKStream.print(Printed.<String,OrderRewardMessage>toSysOut().withLabel("rewarded"));

        //3 sink stream
        KStream<String, OrderMessage> storageStream = maskedStream.selectKey(CommodityStreamUtil.generateStorageKey());
        storageStream.to("t.commodity.base64",Produced.with(string, orderSerde));

        //4 sink stream
        maskedStream.filter((k,v) -> v.getOrderLocation().toUpperCase().startsWith("C"))
                .foreach((k,v) -> this.reportFraud(v));

        return maskedStream;
    }

    private void reportFraud(OrderMessage v) {
        log.info("Reporting fraud {} ", v);
    }
}
