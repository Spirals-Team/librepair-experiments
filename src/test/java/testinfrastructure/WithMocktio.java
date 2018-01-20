package testinfrastructure;

import org.mockito.Mockito;

import static testinfrastructure.MustAlwaysBePrimed.MUST_ALWAYS_BE_PRIMED;

public interface WithMocktio {
        @SuppressWarnings("unchecked") // This is to avoid warnings when mocking e.g. List<Integer> x = mock(List.class)
        default <T, S extends T> S mockGeneric(Class<T> classToMock) {
            return (S) Mockito.mock(classToMock, MUST_ALWAYS_BE_PRIMED);
        }
}
