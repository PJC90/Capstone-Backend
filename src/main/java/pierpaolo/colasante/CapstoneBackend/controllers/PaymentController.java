package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pierpaolo.colasante.CapstoneBackend.entities.Payment;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.PaymentDTO;
import pierpaolo.colasante.CapstoneBackend.services.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment savePayment(@RequestBody PaymentDTO body){
        return paymentService.savePayment(body);
    }


}
