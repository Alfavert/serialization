package S57Library.files;

public class S57UpdateExeption extends Exception {
    public S57UpdateExeption() {
        super();
    }

    /*
     * @param message
     */
    public S57UpdateExeption(String message) {
        super(message);
    }

    /*
     * @param cause
     */
    public S57UpdateExeption(Throwable cause) {
        super(cause);
    }

    /*
     * @param message
     * @param cause
     */
    public S57UpdateExeption(String message, Throwable cause) {
        super(message, cause);
    }


}
