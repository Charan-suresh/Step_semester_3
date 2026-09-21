public interface Alertable {
    String sendAlert(String message);

    static void broadcastAll(Alertable[] devices, String message) {
        if (devices == null) {
            return;
        }
        for (Alertable device : devices) {
            if (device != null) {
                System.out.println(device.sendAlert(message));
            }
        }
    }

    static String getZoneIfMotionSensor(Alertable a) {
        if (a instanceof MotionSensor) {
            MotionSensor ms = (MotionSensor) a;
            return ms.getZoneName();
        }
        return "Not a motion sensor";
    }

    static void main(String[] args) {
        MotionSensor m = new MotionSensor("Living Room");
        System.out.println(m.sendAlert("Motion detected"));

        DualZoneMotionSensor d = new DualZoneMotionSensor("Hallway", "Stairwell");
        System.out.println(d.sendAlert("Motion detected"));

        SmokeDetector s = new SmokeDetector("SD-01");
        System.out.println(s.sendAlert("Smoke detected"));

        System.out.println(getZoneIfMotionSensor(m));
        System.out.println(getZoneIfMotionSensor(s));

        Alertable[] devices = new Alertable[]{m, d, s};
        broadcastAll(devices, "System check alert");
    }
}
