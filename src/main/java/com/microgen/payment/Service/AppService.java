package com.microgen.payment.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microgen.payment.Model.Payment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("appService")
public class AppService {

    public ObjectNode savePayments(List<Payment> payments, String sort) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode node = mapper.createObjectNode();

        /*sort the payments based on the type mentioned in the url (path variable) accepts date & amount or nothing*/

        switch (sort) {
            case "date":
                payments = payments.stream().sorted(Comparator.comparing(Payment::getDate)).collect(Collectors.toList());
                break;
            case "amount":
                payments = payments.stream().sorted(Comparator.comparing(Payment::getAmount)).collect(Collectors.toList());
                break;
            default:
                break;
        }

        ArrayNode savedPayments = mapper.createArrayNode();
        ArrayNode errorPayments = mapper.createArrayNode();

        payments.stream().forEach(payment -> {
            ObjectNode paymentNode = mapper.valueToTree(payment);
            /*checking for a positive amount*/
            if (payment.getAmount() != null && payment.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                savedPayments.add(paymentNode);
            } else {
                errorPayments.add(paymentNode);
            }
        });

        node.putPOJO("savedPayments", savedPayments);
        node.putPOJO("errorPayments", errorPayments);
        return node;
    }
}
