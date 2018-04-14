package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {
    private static final int CNP_LENGTH = 13;
    private final String CNP_INVALIDATION_REGEX = "[a-zA-Z]+";

    private final Client client;
    private final List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public ClientValidator(Client client) {
        this.client = client;
        this.errors = new ArrayList<>();
    }

    public boolean validate() {
        validateCNP(client.getPersonalNumericCode());
        return errors.isEmpty();
    }

    private void validateCNP(String personalNumericCode) {
        if (Pattern.matches(CNP_INVALIDATION_REGEX, personalNumericCode) == true) {
            errors.add("CNP must only contain integers");
        }
        if (personalNumericCode.length() != CNP_LENGTH) {
            errors.add("CNP must contain 13 digits");
        }
    }
}
