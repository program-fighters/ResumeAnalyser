package org.example.Constant;

public interface RegexConstant {

    String NUMERIC_REGEX = "[0-9]+";
    String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]+$";
    String EXCLUDE_SPECIAL_CHARACTERS = "^(?=[a-zA-Z0-9()_�-]*$)(?!.*[<>'\"/;`%~@#$^*+=[\\]{}|\\\\,.?:])+$";
    String PASSWORD_REGEX_14_DIGIT = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{14,25})";
    String PASSWORD_REGEX_8_DIGIT = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,25})";

    String ALPHANUMERIC_UNDERSCORE_REGEX = "^[a-zA-Z0-9_]*$";
    String Numeric_Regex = "[0-9]+";
    String ONLY_STRING_REGEX = "^[a-zA-Z]+$";
    String ALPHANUMERIC_PERIOD_REGEX = "^[a-zA-Z0-9.]+$";
    String USER_NAME_VALID_REGEX = "^[a-zA-Z][a-zA-Z0-9]*(\\.[a-zA-Z0-9]+)?$";
    String USER_NAME_VALID_LENGTH_REGEX = "^[a-zA-z0-9.]{4,15}$";

    // Regex for financier form
    String VALID_FIN_COMPANY_NAME_REGX = "^[0-9A-Za-zÀ-ÿ\\s,._+;()*~'#@!?&-]+$";
    String VALID_ALPHA_NUMERIC_REGX = "^[a-zA-Z\\d]+$";
    String VALID_NUMERIC_REGX = "^\\d+$";
    String VALID_EMAIL_REGX = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    String VALID_EMAIL_DOMAIN_REGX = "@[a-zA-Z\\d.-]+\\Z";
    String VALID_PHONE_REGX = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
    String VALIDATE_PHONE_REGEX_DURING_SEARCH = "^[0-9]{1,16}$";
    String VALID_URL_REGX = "\\b(https?|ftp|file)://[-a-zA-Z\\d+&@#/%?=~_|!:,.;]*[-a-zA-Z\\d+&@#/%=~_|]";
    String VALID_PUBLIC_KEY_REGEX = "[^<>'\\\";`%#&$]*";
}
