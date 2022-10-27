package com.finalVariant.OnlineStore.model.dao;

import java.lang.reflect.InvocationTargetException;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    protected static String resourcePropertiesName;

    public static DaoFactory getInstance(Class<? extends DaoFactory> type, String properties){
        resourcePropertiesName = properties;
        if(daoFactory == null){
            synchronized (DaoFactory.class){
                try{
                    daoFactory = type.getDeclaredConstructor().newInstance();
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return daoFactory;
    }

    public abstract CategoryDao createCategoryDao();

    public abstract ColorDao createColorDao();

    public abstract OrderDao createOrderDao();

    public abstract ProductDao createProductDao();

    public abstract SizeDao createSizeDao();

    public abstract UserDao createUserDao();
}
