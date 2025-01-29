package dao;

public class DaoFactory {
    private static DaoFactory instance;

    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }
    private DaoFactory() {}


}
