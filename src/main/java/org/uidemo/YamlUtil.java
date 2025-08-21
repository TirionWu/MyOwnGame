package org.uidemo;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class YamlUtil {
    
    /**
     * 从resources目录加载YAML文件
     * @param fileName 文件名
     * @return 解析后的Map对象
     */
    public static Map<String, Object> loadYaml(String fileName) {
        Yaml yaml = new Yaml();
        InputStream inputStream = YamlUtil.class.getClassLoader().getResourceAsStream(fileName);
        return yaml.load(inputStream);
    }
    
    /**
     * 根据角色名称从YAML数据中获取角色信息
     * @param yamlData YAML数据
     * @param roleName 角色名称
     * @return 角色信息Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getRoleInfo(Map<String, Object> yamlData, String roleName) {
        return (Map<String, Object>) yamlData.get(roleName);
    }
    
    /**
     * 从YAML文件中加载指定名称的角色
     * @param fileName YAML文件名
     * @param roleName 角色名称
     * @return Role对象
     */
    public static Role loadRoleFromYaml(String fileName, String roleName) {
        Map<String, Object> playerData = loadYaml(fileName);
        Map<String, Object> roleData = getRoleInfo(playerData, roleName);
        
        return Role.builder()
                .name((String) roleData.get("name"))
                .hp(((Number) roleData.get("hp")).doubleValue())
                .atk(((Number) roleData.get("atk")).doubleValue())
                .def(((Number) roleData.get("def")).doubleValue())
                .build();
    }
}