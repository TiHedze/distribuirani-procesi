package pmf.distribuiraniprocesi.contracts;

public interface Resource {
    void acquire(int resourceId);
    void release(int resourceId);
}
