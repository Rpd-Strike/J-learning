package Exceptions;

public class DeleteException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 640516755926038863L;

    public DeleteException(String err)
    {
        super(err);
    }
}
