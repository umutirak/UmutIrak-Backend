package umut.backend.Enums;

import java.util.Arrays;

public enum UserRole {
    ADMIN, USER;

    public static String[] authorities() {
        UserRole[] roles = UserRole.values();
        return Arrays.stream(roles)
                .map(Enum::name)
                .toArray(String[]::new);
    }
}
