package com.finologee.apifinologeebank.core.annotation;

import com.finologee.apifinologeebank.core.service.IbanCheckerService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IbanValidator implements ConstraintValidator<IbanChecker, String> {
    private final IbanCheckerService ibanCheckerService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return ibanCheckerService.callIbanChecker(s);
    }
}
