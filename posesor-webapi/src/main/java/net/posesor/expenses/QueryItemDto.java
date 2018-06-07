package net.posesor.expenses;

import lombok.Value;

import java.net.URI;

/**
 * Container class used to send dto item with its location address.
 */
@Value
public class QueryItemDto {
    private URI location;
    private ExpenseDocumentDto item;
}
