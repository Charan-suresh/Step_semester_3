public interface RemoteControllable {
    String connect(String appId);

    static void connectAll(RemoteControllable[] items, String appId) {
        if (items == null) {
            return;
        }
        for (RemoteControllable item : items) {
            if (item != null) {
                System.out.println(item.connect(appId));
            }
        }
    }
}
