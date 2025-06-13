package model;

import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private static Properties props = new Properties();

    static {
        boolean loaded = false;
        try {
            // Thử đọc từ classpath (dành cho khi đã build ra WEB-INF/classes)
            InputStream input = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties");
            if (input != null) {
                props.load(input);
                loaded = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Nếu chưa load được, thử đọc từ đường dẫn tuyệt đối (dành cho môi trường dev)
        if (!loaded) {
            try (InputStream input2 = ConfigUtil.class.getResourceAsStream("/src/config.properties")) {
                if (input2 != null) {
                    props.load(input2);
                    loaded = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!loaded) {
            System.out.println("Không tìm thấy file config.properties! Hãy chắc chắn file nằm trong thư mục conf và được copy vào build/classes hoặc WEB-INF/classes khi build/deploy.");
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
} 