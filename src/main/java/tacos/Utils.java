package tacos;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Utils {
    public static boolean isNullOrEmpty(String value){
        return value == null || value.isEmpty();
    }
}
