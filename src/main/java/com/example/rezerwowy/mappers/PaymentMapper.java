package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.PaymentDto;
import com.example.rezerwowy.exceptions.ReservationNotFoundException;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.services.PaymentService;
import com.example.rezerwowy.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

	@Lazy
	private final PaymentService paymentService;
    @Lazy
	private final ReservationService reservationService;
    public PaymentDto mapPaymentToPaymentDto(Payment payment) {
        Long reservationId = payment.getReservation() != null
                ? payment.getReservation().getId()
                : null;

        return PaymentDto.builder()
                .id(payment.getId())
                .publicId(payment.getPublicId())
                .buyer(payment.getBuyer())
                .date(payment.getDate())
                .reservationId(reservationId)
                .build();
    }

    public Payment mapPaymentDtoToPayment(PaymentDto paymentDto) {
		Reservation reservation = null;
		if (paymentDto.reservationId() != null) {
			try {
				reservation = reservationService.getReservationById(paymentDto.reservationId());
			} catch (ReservationNotFoundException ignored) { }
		}

        return Payment.builder()
                .id(paymentDto.id())
                .buyer(paymentDto.buyer())
                .date(paymentDto.date())
                .reservation(reservation)
                .build();
    }
}
