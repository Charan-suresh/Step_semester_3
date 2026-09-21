public abstract class MediaFile {
    private static int counter = 1000;
    private final String fileId;

    public MediaFile() {
        counter++;
        this.fileId = "MF-" + counter;
    }

    public String getFileId() {
        return fileId;
    }

    public abstract String getFormatInfo();
}
