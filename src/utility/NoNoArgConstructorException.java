package utility;

public class NoNoArgConstructorException extends Exception {
    public NoNoArgConstructorException(Class<?> clazz) {
        super("This class " + clazz.getSimpleName() + " do not allow use of no-arg constructor");
    }
}
