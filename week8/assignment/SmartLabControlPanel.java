import java.util.*;

interface Capability {
    String getName();
    String apply(String deviceName, Object value);
}

class PowerCapability implements Capability {
    private boolean powerOn = false;

    @Override
    public String getName() {
        return "Power";
    }

    @Override
    public String apply(String deviceName, Object value) {
        String val = value.toString().toUpperCase();
        if ("ON".equals(val) || "TRUE".equalsIgnoreCase(val)) {
            powerOn = true;
            return deviceName + ": ON.";
        } else if ("OFF".equals(val) || "FALSE".equalsIgnoreCase(val)) {
            powerOn = false;
            return deviceName + ": OFF.";
        }
        return "Rejected: Invalid power state.";
    }

    public boolean isPowerOn() {
        return powerOn;
    }
}

class BrightnessCapability implements Capability {
    private int brightness = 100;

    @Override
    public String getName() {
        return "Brightness";
    }

    @Override
    public String apply(String deviceName, Object value) {
        int level;
        try {
            level = Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return "Rejected: Brightness must be an integer.";
        }

        if (level < 0 || level > 100) {
            return "Rejected: " + deviceName + " brightness must be between 0% and 100%.";
        }
        this.brightness = level;
        return deviceName + ": brightness set to " + level + "%.";
    }

    public int getBrightness() {
        return brightness;
    }
}

class TemperatureCapability implements Capability {
    private int temperature = 24;

    @Override
    public String getName() {
        return "Temperature";
    }

    @Override
    public String apply(String deviceName, Object value) {
        int temp;
        try {
            temp = Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return "Rejected: Temperature must be an integer.";
        }

        if (temp < 16 || temp > 30) {
            return "Rejected: " + deviceName + " temperature must be between 16°C and 30°C.";
        }
        this.temperature = temp;
        return deviceName + ": temperature set to " + temp + "°C.";
    }

    public int getTemperature() {
        return temperature;
    }
}

class Device {
    private final String name;
    private final Map<String, Capability> capabilities;

    public Device(String name) {
        this.name = name;
        this.capabilities = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addCapability(Capability capability) {
        capabilities.put(capability.getName().toLowerCase(), capability);
    }

    public boolean hasCapability(String capabilityName) {
        return capabilities.containsKey(capabilityName.toLowerCase());
    }

    public String executeCapability(String capabilityName, Object value) {
        Capability cap = capabilities.get(capabilityName.toLowerCase());
        if (cap == null) {
            return null; // skipped
        }
        return cap.apply(name, value);
    }
}

class SceneStep {
    private final String capabilityName;
    private final Object value;

    public SceneStep(String capabilityName, Object value) {
        this.capabilityName = capabilityName;
        this.value = value;
    }

    public String getCapabilityName() {
        return capabilityName;
    }

    public Object getValue() {
        return value;
    }
}

class Scene {
    private final String name;
    private final List<SceneStep> steps;

    public Scene(String name) {
        this.name = name;
        this.steps = new ArrayList<>();
    }

    public void addStep(String capabilityName, Object value) {
        steps.add(new SceneStep(capabilityName, value));
    }

    public void execute(List<Device> devices) {
        System.out.println("Scene '" + name + "' started.");
        int actionsApplied = 0;

        for (SceneStep step : steps) {
            for (Device device : devices) {
                if (device.hasCapability(step.getCapabilityName())) {
                    String result = device.executeCapability(step.getCapabilityName(), step.getValue());
                    if (result != null) {
                        System.out.println(result);
                        actionsApplied++;
                    }
                }
            }
        }
        System.out.println("Scene '" + name + "' completed: " + actionsApplied + " actions applied.");
    }
}

public class SmartLabControlPanel {
    public static void main(String[] args) {
        // 1. Create devices with capabilities
        Device labAc = new Device("Lab AC");
        labAc.addCapability(new PowerCapability());
        labAc.addCapability(new TemperatureCapability());

        Device ceilingLights = new Device("Ceiling Lights");
        ceilingLights.addCapability(new PowerCapability());
        ceilingLights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List<Device> labDevices = Arrays.asList(labAc, ceilingLights, projector);

        // 2. Run scene 'Lecture Mode'
        Scene lectureMode = new Scene("Lecture Mode");
        lectureMode.addStep("Power", "ON");
        lectureMode.addStep("Brightness", 40);
        lectureMode.addStep("Temperature", 24);
        lectureMode.execute(labDevices);

        // 3. Admin sets temperature of 'Lab AC' to 12°C (rejected)
        String tempResult = labAc.executeCapability("Temperature", 12);
        System.out.println(tempResult);

        // 4. Admin adds Brightness to 'Projector' and sets its brightness to 70%
        projector.addCapability(new BrightnessCapability());
        System.out.println("Projector: Brightness capability added.");
        String brightResult = projector.executeCapability("Brightness", 70);
        System.out.println(brightResult);
    }
}
