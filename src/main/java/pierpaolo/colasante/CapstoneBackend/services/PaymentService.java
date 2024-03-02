package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Payment;
import pierpaolo.colasante.CapstoneBackend.entities.Review;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.PaymentDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.PaymentDAO;

import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentDAO paymentDAO;
    public Page<Payment> findAllPayment(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return paymentDAO.findAll(pageable);
    }
    public Payment findById(UUID paymentId){
        return paymentDAO.findById(paymentId).
                orElseThrow(()->new NotFoundException(paymentId));
    }
    public Payment savePayment(PaymentDTO body){
        Payment payment = new Payment();
        payment.setTransactionCode(body.transactionCode());
        payment.setTotal(body.total());
        payment.setPaymentType(body.paymentType());
        return paymentDAO.save(payment);
    }
}
