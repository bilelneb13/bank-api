package com.finologee.apifinologeebank.core.mapper;

import com.finologee.apifinologeebank.core.dto.PaymentDto;
import com.finologee.apifinologeebank.core.model.BankAccount;
import com.finologee.apifinologeebank.core.model.Payment;
import com.finologee.apifinologeebank.core.model.PaymentStatus;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD, imports = {LocalDateTime.class})
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    List<PaymentDto> toDtoList(List<Payment> payment);

    @Mapping(source = "giverAccount.accountNumber", target = "giverAccountNumber")
    @Mapping(source = "creationDate", target = "creationDate")
    PaymentDto toDto(Payment payment);

    @Mapping(expression = "java(LocalDateTime.now())", target = "creationDate")
    Payment paymentDtoToPayment(PaymentDto paymentDto, BankAccount sourceAccount, PaymentStatus status);

    @AfterMapping
    default void after(PaymentDto paymentDto, BankAccount sourceAccount, PaymentStatus status, @MappingTarget Payment.PaymentBuilder payment) {
        payment.paymentStatus(status);
        payment.giverAccount(sourceAccount);
    }
}
