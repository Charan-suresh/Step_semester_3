/**
 * Design Justification:
 * AudioFile IS-A MediaFile because it represents a concrete, persistent stored media file asset
 * with fixed identity (fileId) and structural format properties (getFormatInfo). Subclassing MediaFile
 * inherits shared state and behavior common to all physical file formats.
 *
 * In contrast, Podcast CAN-DO playback (implements Playable), but is NOT an on-disk MediaFile:
 * it represents a dynamic, live-streamed episodic broadcast produced externally and identified by
 * show name and episode number rather than a static storage format and file ID. Forcing Podcast to extend
 * MediaFile would violate the Liskov Substitution Principle and burden it with meaningless file metadata.
 */
interface Playable {
    String play();
    String play(int fromSecond);
    String pause();

    static void launchAll(Playable[] items) {
        if (items == null) {
            return;
        }
        for (Playable item : items) {
            if (item != null) {
                System.out.println(item.play());
            }
        }
    }
}

abstract class MediaFile {
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

class AudioFile extends MediaFile implements Playable {
    private final String title;

    public AudioFile(String title) {
        super();
        this.title = title;
    }

    @Override
    public String play() {
        return "Playing audio: " + title;
    }

    @Override
    public String play(int fromSecond) {
        int minutes = fromSecond / 60;
        int seconds = fromSecond % 60;
        return "Playing audio: " + title + " from " + String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public String pause() {
        return "Paused audio: " + title;
    }

    @Override
    public String getFormatInfo() {
        return "Audio file, ID: " + getFileId();
    }
}

class Podcast implements Playable {
    private final String showName;
    private final int episodeNumber;

    public Podcast(String showName, int episodeNumber) {
        if (episodeNumber <= 0) {
            throw new IllegalArgumentException("Episode number must be positive");
        }
        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }

    @Override
    public String play() {
        return "Streaming episode " + episodeNumber + " of " + showName;
    }

    @Override
    public String play(int fromSecond) {
        int minutes = fromSecond / 60;
        int seconds = fromSecond % 60;
        return "Streaming episode " + episodeNumber + " of " + showName + " from " + String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public String pause() {
        return "Paused episode " + episodeNumber + " of " + showName;
    }
}

public class UniversalMediaLauncher {
    public static void launchAll(Playable[] items) {
        Playable.launchAll(items);
    }

    public static void main(String[] args) {
        AudioFile a = new AudioFile("Morning Jazz");
        System.out.println(a.play());
        System.out.println(a.play(30));
        System.out.println(a.getFormatInfo());

        Podcast p = new Podcast("Tech Talk", 12);
        System.out.println(p.play());

        // upcasting: AudioFile reference stored as the Playable interface type
        Playable ref = a;
        System.out.println(ref.play());

        launchAll(new Playable[]{ ref, p });
    }
}
