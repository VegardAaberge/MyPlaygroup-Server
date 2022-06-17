package com.myplaygroup.server.schedule.service;

import com.myplaygroup.server.exception.NotFoundException;
import com.myplaygroup.server.schedule.model.Payment;
import com.myplaygroup.server.schedule.repository.PaymentsRepository;
import com.myplaygroup.server.schedule.requests.PaymentRequest;
import com.myplaygroup.server.schedule.response.PaymentItem;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AppUserService userService;
    private final PaymentsRepository paymentsRepository;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public List<PaymentItem> getPayments() {
        List<Payment> payments = paymentsRepository.findAll(Sort.by("id"));

        List<PaymentItem> paymentItems = getPaymentItems(payments);

        return paymentItems;
    }

    public List<PaymentItem> addPayments(List<PaymentRequest> request) {

        request.forEach(item -> {
            if(item.id == -1){
                addPayment(item);
            }else {
                modifyPayment(item);
            }
        });

        return getPayments();
    }

    private void modifyPayment(PaymentRequest item){
        Payment payment = paymentsRepository.findById(item.id)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        payment.setAmount(item.amount);
        payment.setDate(item.date);

        paymentsRepository.save(payment);
    }

    private void addPayment(PaymentRequest item){
        AppUser appUser = userService.loadUserByUsername(item.username);

        Payment payment = new Payment(
                item.clientId,
                item.date,
                item.amount,
                appUser
        );
        paymentsRepository.save(payment);
    }

    private List<PaymentItem> getPaymentItems(List<Payment> payments){
        return payments.stream().map(item -> new PaymentItem(
                item.getId(),
                item.getClientId(),
                item.getAppUser().getUsername(),
                item.getDate(),
                item.getAmount(),
                item.getCancelled()
        )).collect(Collectors.toList());
    }
}
