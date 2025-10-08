package net.justlearning.arslaan3102.arslaansmagichax.modules.settings;

public abstract class Setting {
    private String name;
    private boolean visible = true;

    public Setting(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public abstract String getValueAsString();
    public abstract void setValueFromString(String value);
}
