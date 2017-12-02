package au.com.rest.test.enums;

public enum KeyValueForSearch {

    FULL_NAME("fullName"), EMAIL("email"), LOGIN("login"), ACCOUNT_STATE("accountState");

    private String type;

    private KeyValueForSearch(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
