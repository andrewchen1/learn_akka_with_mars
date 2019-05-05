package top.andrewchen1.paper10.model;

/**
 * @author dafuchen
 * 2019-05-04
 */
public enum QueryOrderEventTypeEnum {
    /**
     * 根据id 进行查找
     */
    FIND_BY("top.andrewchen1.paper10.repository.impl.QueryCurrentOrder"),
    /**
     * 这个event 在这个id之后的下一个id
     */
    FIND_NEXT("top.andrewchen1.paper10.repository.impl.QueryNextOrder");

    private String classPath;

    QueryOrderEventTypeEnum(String  classPath) {
        this.classPath = classPath;
    }

    public String getClassPath() {
        return classPath;
    }
}
