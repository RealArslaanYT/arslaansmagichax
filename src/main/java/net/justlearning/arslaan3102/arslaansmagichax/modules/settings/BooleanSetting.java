package net.justlearning.arslaan3102.arslaansmagichax.modules.settings;

public class BooleanSetting extends Setting {

    private boolean enabled;

    public BooleanSetting(String name, boolean defaultValue) {
        super(name);
        this.enabled = defaultValue;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(enabled);
    }

    @Override
    public void setValueFromString(String value) {
        this.enabled = Boolean.parseBoolean(value);
    }
}
