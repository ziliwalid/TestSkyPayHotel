package enums;

public enum RoomType {
    STANDARD("standard"),
    JUNIOR("junior"),
    SUITE("suite");

    private final String type;

    RoomType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
