package com.expensetracker.dto;

import com.expensetracker.model.Category;
import java.math.BigDecimal;

/** Spring Data projection: result of a GROUP BY category SUM(amount) query. */
public interface CategoryTotal {
    Category getCategory();
    BigDecimal getTotal();
}
