package com.example.expense.assistant.mcp.registration;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    public Expense register(
            String date,
            String merchant,
            String description,
            String purpose,
            String expenseCategory,
            int total,
            Integer tax) {

        // サンプルのため、登録処理は行わず、Expenseオブジェクトを生成して返します
        return new Expense(
                UUID.randomUUID().toString(),
                date,
                merchant,
                description,
                purpose,
                expenseCategory,
                total,
                tax);
    }
}
