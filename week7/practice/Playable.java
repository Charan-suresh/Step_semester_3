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
public interface Playable {
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

    static void main(String[] args) {
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
