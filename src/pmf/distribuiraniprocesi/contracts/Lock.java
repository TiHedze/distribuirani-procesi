package pmf.distribuiraniprocesi.contracts;

public interface Lock extends MessageHandler{
    void requestCS();
    void releaseCS();
}
