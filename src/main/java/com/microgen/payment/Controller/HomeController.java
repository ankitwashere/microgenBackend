package com.microgen.payment.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microgen.payment.Model.Payment;
import com.microgen.payment.Service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*",  maxAge = 3600)
public class HomeController extends GenericElementController{

    @Autowired
    AppService appService;

    @RequestMapping("/")
    public String defaultPage() {
        return "default";
    }

    @RequestMapping("/home")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/submit-payments/{sort}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectNode savePaymentsSorted(@PathVariable("sort") String sorting, @RequestBody List<Payment> payments, BindingResult result){
        return appService.savePayments(payments,sorting);
    }

    @RequestMapping(value = "/submit-payments/", method = RequestMethod.POST)
    public ObjectNode savePayments(@RequestBody List<Payment> payments){
        return appService.savePayments(payments, "default");
    }

}
