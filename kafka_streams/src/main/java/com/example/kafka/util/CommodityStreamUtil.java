package com.example.kafka.util;

import com.example.kafka.broker.message.OrderMessage;
import com.example.kafka.broker.message.OrderPatternMessage;
import com.example.kafka.broker.message.OrderRewardMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Predicate;

import java.util.Base64;

public class CommodityStreamUtil {
    public static OrderMessage maskCreditCard(OrderMessage orderMessage) {
        OrderMessage converted = orderMessage.copy();
        var maskedCreditCard = orderMessage.getCreditCardNumber()
                .replaceFirst("\\d{12}", StringUtils.repeat('*', 12));

        converted.setCreditCardNumber(maskedCreditCard);

        return converted;
    }

    public static OrderPatternMessage mapToOrderPattern(OrderMessage original) {
        OrderPatternMessage orderPatternMessage = new OrderPatternMessage();
        orderPatternMessage.setItemName(original.getItemName());
        orderPatternMessage.setOrderDateTime(original.getOrderDateTime());
        orderPatternMessage.setOrderLocation(original.getOrderLocation());
        orderPatternMessage.setItemName(original.getItemName());
        orderPatternMessage.setTotalItemAmount(original.getPrice() * original.getQuantity());

        return orderPatternMessage;
    }

    public static OrderRewardMessage mapToOrderReward(OrderMessage original) {
        OrderRewardMessage orderRewardMessage = new OrderRewardMessage();
        orderRewardMessage.setItemName(original.getItemName());
        orderRewardMessage.setOrderDateTime(original.getOrderDateTime());
        orderRewardMessage.setOrderLocation(original.getOrderLocation());
        orderRewardMessage.setItemName(original.getItemName());
        orderRewardMessage.setPrice(original.getPrice());
        orderRewardMessage.setQuantity(original.getQuantity());

        return orderRewardMessage;
    }


    public static Predicate<? super String, ? super OrderPatternMessage> isPlastic() {
        return (k, v) -> StringUtils.startsWithIgnoreCase(v.getItemName(),"Plastic");
    }

    public static Predicate<? super String,? super OrderMessage> isCheap() {
        return (k, v) -> v.getPrice() < 100;
    }

    public static KeyValueMapper<String, OrderMessage, String> generateStorageKey() {
        return (k, v) -> Base64.getEncoder().encodeToString(v.getOrderNumber().getBytes());
    }
}
