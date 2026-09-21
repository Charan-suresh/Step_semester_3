public class UserProfile implements Exportable {
    private final String username;

    public UserProfile(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        this.username = username.trim();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String exportData() {
        Exportable.incrementExportCount();
        return "Exported profile: " + username;
    }

    public static int getTotalExports() {
        return Exportable.getTotalExports();
    }
}
