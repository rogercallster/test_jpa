
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

import java.util.Properties;

public class PropertiesHandler implements Serializable {

    private String relationalDbUrl;
    private String relationalDbUsername;
    private String relationalDbPassword;

    private Properties properties;

    public PropertiesHandler(String filePath) throws IOException, IllegalArgumentException{
        Properties properties = getProperties(filePath);
        this.properties =  properties;
        init(properties);
    }

    public PropertiesHandler(Properties properties) throws IOException, IllegalArgumentException{
        this.properties = properties;
        init(properties);
    }

    public Properties getProperties() {
        return properties;
    }

    private static Object getValue(Properties props, String name, Class<?> type) {
        String value = props.getProperty(name);
        if (value == null)
            throw new IllegalArgumentException("Missing configuration value: " + name);
        if (type == String.class)
            return value;
        if (type == boolean.class)
            return Boolean.parseBoolean(value);
        if (type == int.class)
            return Integer.parseInt(value);
        if (type == float.class)
            return Float.parseFloat(value);
        throw new IllegalArgumentException("Unknown configuration value type: " + type.getName());
    }

    private void init(Properties properties) {
        relationalDbUrl = (String) (getValue(properties, "relationaldb.url", String.class));
        relationalDbUsername = (String) getValue(properties, "relationaldb.username", String.class);
        relationalDbPassword = (String) getValue(properties, "relationaldb.password", String.class);
    }

    private Properties getProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            properties.load(inputStream);
        }
        return properties;
    }


    public String getRelationalDbUrl() {
        return relationalDbUrl;
    }

    public void setRelationalDbUrl(String relationalDbUrl) {
        this.relationalDbUrl = relationalDbUrl;
    }

    public String getRelationalDbUsername() {
        return relationalDbUsername;
    }

    public void setRelationalDbUsername(String relationalDbUsername) {
        this.relationalDbUsername = relationalDbUsername;
    }

    public String getRelationalDbPassword() {
        return relationalDbPassword;
    }

    public void setRelationalDbPassword(String relationalDbPassword) {
        this.relationalDbPassword = relationalDbPassword;
    }




}
