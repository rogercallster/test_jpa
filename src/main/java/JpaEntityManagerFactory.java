import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.postgresql.ds.*;

public class JpaEntityManagerFactory {

    Class[] entityClasses;

    public JpaEntityManagerFactory(Class[] entityClasses) {
        this.entityClasses = entityClasses;
    }

    protected List<String> getEntityClassNames() {
        return Arrays.asList(getEntities()).stream().map(Class::getName).collect(Collectors.toList());
    }

    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.connection.datasource", getDataSource());
        return properties;
    }

    protected Class[] getEntities() {
        return entityClasses;
    }

    protected EntityManagerFactory getEntityManagerFactory() {
        PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(getClass().getSimpleName());
        Map<String, Object> configuration = new HashMap<>();

        return new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration).build();
    }

    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    private DataSource getDataSource() {
        PropertiesHandler properties = null;
        try {
            properties = new PropertiesHandler("src/main/resources/jpa.properties");
            System.out.println("Ankur ::::: > getting properties" + properties.toString());
        } catch (IOException e) {
            System.out.println("testing");
        }

        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setPassword(properties.getRelationalDbPassword());
        ds.setUrl(properties.getRelationalDbUrl());
        ds.setUser(properties.getRelationalDbUsername());
        return ds;
    }

    protected HibernatePersistenceUnitInfo getPersistenceUnitInfo(String name) {
        return new HibernatePersistenceUnitInfo(name, getEntityClassNames(), getProperties());
    }
}