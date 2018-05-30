

package com.arcao.geocaching.api.data;

import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_DeviceInfo extends DeviceInfo {

  private final int applicationCurrentMemoryUsage;
  private final int applicationPeakMemoryUsage;
  private final String applicationSoftwareVersion;
  private final String deviceManufacturer;
  private final String deviceName;
  private final String deviceOperatingSystem;
  private final float deviceTotalMemoryInMb;
  private final String deviceUniqueId;
  private final String mobileHardwareVersion;
  private final String webBrowserVersion;

  private AutoValue_DeviceInfo(
      int applicationCurrentMemoryUsage,
      int applicationPeakMemoryUsage,
      String applicationSoftwareVersion,
      String deviceManufacturer,
      String deviceName,
      String deviceOperatingSystem,
      float deviceTotalMemoryInMb,
      String deviceUniqueId,
      @Nullable String mobileHardwareVersion,
      @Nullable String webBrowserVersion) {
    this.applicationCurrentMemoryUsage = applicationCurrentMemoryUsage;
    this.applicationPeakMemoryUsage = applicationPeakMemoryUsage;
    this.applicationSoftwareVersion = applicationSoftwareVersion;
    this.deviceManufacturer = deviceManufacturer;
    this.deviceName = deviceName;
    this.deviceOperatingSystem = deviceOperatingSystem;
    this.deviceTotalMemoryInMb = deviceTotalMemoryInMb;
    this.deviceUniqueId = deviceUniqueId;
    this.mobileHardwareVersion = mobileHardwareVersion;
    this.webBrowserVersion = webBrowserVersion;
  }

  @Override
  public int applicationCurrentMemoryUsage() {
    return applicationCurrentMemoryUsage;
  }

  @Override
  public int applicationPeakMemoryUsage() {
    return applicationPeakMemoryUsage;
  }

  @Override
  public String applicationSoftwareVersion() {
    return applicationSoftwareVersion;
  }

  @Override
  public String deviceManufacturer() {
    return deviceManufacturer;
  }

  @Override
  public String deviceName() {
    return deviceName;
  }

  @Override
  public String deviceOperatingSystem() {
    return deviceOperatingSystem;
  }

  @Override
  public float deviceTotalMemoryInMb() {
    return deviceTotalMemoryInMb;
  }

  @Override
  public String deviceUniqueId() {
    return deviceUniqueId;
  }

  @Nullable
  @Override
  public String mobileHardwareVersion() {
    return mobileHardwareVersion;
  }

  @Nullable
  @Override
  public String webBrowserVersion() {
    return webBrowserVersion;
  }

  @Override
  public String toString() {
    return "DeviceInfo{"
         + "applicationCurrentMemoryUsage=" + applicationCurrentMemoryUsage + ", "
         + "applicationPeakMemoryUsage=" + applicationPeakMemoryUsage + ", "
         + "applicationSoftwareVersion=" + applicationSoftwareVersion + ", "
         + "deviceManufacturer=" + deviceManufacturer + ", "
         + "deviceName=" + deviceName + ", "
         + "deviceOperatingSystem=" + deviceOperatingSystem + ", "
         + "deviceTotalMemoryInMb=" + deviceTotalMemoryInMb + ", "
         + "deviceUniqueId=" + deviceUniqueId + ", "
         + "mobileHardwareVersion=" + mobileHardwareVersion + ", "
         + "webBrowserVersion=" + webBrowserVersion
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof DeviceInfo) {
      DeviceInfo that = (DeviceInfo) o;
      return (this.applicationCurrentMemoryUsage == that.applicationCurrentMemoryUsage())
           && (this.applicationPeakMemoryUsage == that.applicationPeakMemoryUsage())
           && (this.applicationSoftwareVersion.equals(that.applicationSoftwareVersion()))
           && (this.deviceManufacturer.equals(that.deviceManufacturer()))
           && (this.deviceName.equals(that.deviceName()))
           && (this.deviceOperatingSystem.equals(that.deviceOperatingSystem()))
           && (Float.floatToIntBits(this.deviceTotalMemoryInMb) == Float.floatToIntBits(that.deviceTotalMemoryInMb()))
           && (this.deviceUniqueId.equals(that.deviceUniqueId()))
           && ((this.mobileHardwareVersion == null) ? (that.mobileHardwareVersion() == null) : this.mobileHardwareVersion.equals(that.mobileHardwareVersion()))
           && ((this.webBrowserVersion == null) ? (that.webBrowserVersion() == null) : this.webBrowserVersion.equals(that.webBrowserVersion()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= applicationCurrentMemoryUsage;
    h$ *= 1000003;
    h$ ^= applicationPeakMemoryUsage;
    h$ *= 1000003;
    h$ ^= applicationSoftwareVersion.hashCode();
    h$ *= 1000003;
    h$ ^= deviceManufacturer.hashCode();
    h$ *= 1000003;
    h$ ^= deviceName.hashCode();
    h$ *= 1000003;
    h$ ^= deviceOperatingSystem.hashCode();
    h$ *= 1000003;
    h$ ^= Float.floatToIntBits(deviceTotalMemoryInMb);
    h$ *= 1000003;
    h$ ^= deviceUniqueId.hashCode();
    h$ *= 1000003;
    h$ ^= (mobileHardwareVersion == null) ? 0 : mobileHardwareVersion.hashCode();
    h$ *= 1000003;
    h$ ^= (webBrowserVersion == null) ? 0 : webBrowserVersion.hashCode();
    return h$;
  }

  private static final long serialVersionUID = 7352443462642812711L;

  static final class Builder extends DeviceInfo.Builder {
    private Integer applicationCurrentMemoryUsage;
    private Integer applicationPeakMemoryUsage;
    private String applicationSoftwareVersion;
    private String deviceManufacturer;
    private String deviceName;
    private String deviceOperatingSystem;
    private Float deviceTotalMemoryInMb;
    private String deviceUniqueId;
    private String mobileHardwareVersion;
    private String webBrowserVersion;
    Builder() {
    }
    @Override
    public DeviceInfo.Builder applicationCurrentMemoryUsage(int applicationCurrentMemoryUsage) {
      this.applicationCurrentMemoryUsage = applicationCurrentMemoryUsage;
      return this;
    }
    @Override
    public DeviceInfo.Builder applicationPeakMemoryUsage(int applicationPeakMemoryUsage) {
      this.applicationPeakMemoryUsage = applicationPeakMemoryUsage;
      return this;
    }
    @Override
    public DeviceInfo.Builder applicationSoftwareVersion(String applicationSoftwareVersion) {
      if (applicationSoftwareVersion == null) {
        throw new NullPointerException("Null applicationSoftwareVersion");
      }
      this.applicationSoftwareVersion = applicationSoftwareVersion;
      return this;
    }
    @Override
    public DeviceInfo.Builder deviceManufacturer(String deviceManufacturer) {
      if (deviceManufacturer == null) {
        throw new NullPointerException("Null deviceManufacturer");
      }
      this.deviceManufacturer = deviceManufacturer;
      return this;
    }
    @Override
    public DeviceInfo.Builder deviceName(String deviceName) {
      if (deviceName == null) {
        throw new NullPointerException("Null deviceName");
      }
      this.deviceName = deviceName;
      return this;
    }
    @Override
    public DeviceInfo.Builder deviceOperatingSystem(String deviceOperatingSystem) {
      if (deviceOperatingSystem == null) {
        throw new NullPointerException("Null deviceOperatingSystem");
      }
      this.deviceOperatingSystem = deviceOperatingSystem;
      return this;
    }
    @Override
    public DeviceInfo.Builder deviceTotalMemoryInMb(float deviceTotalMemoryInMb) {
      this.deviceTotalMemoryInMb = deviceTotalMemoryInMb;
      return this;
    }
    @Override
    public DeviceInfo.Builder deviceUniqueId(String deviceUniqueId) {
      if (deviceUniqueId == null) {
        throw new NullPointerException("Null deviceUniqueId");
      }
      this.deviceUniqueId = deviceUniqueId;
      return this;
    }
    @Override
    public DeviceInfo.Builder mobileHardwareVersion(@Nullable String mobileHardwareVersion) {
      this.mobileHardwareVersion = mobileHardwareVersion;
      return this;
    }
    @Override
    public DeviceInfo.Builder webBrowserVersion(@Nullable String webBrowserVersion) {
      this.webBrowserVersion = webBrowserVersion;
      return this;
    }
    @Override
    public DeviceInfo build() {
      String missing = "";
      if (this.applicationCurrentMemoryUsage == null) {
        missing += " applicationCurrentMemoryUsage";
      }
      if (this.applicationPeakMemoryUsage == null) {
        missing += " applicationPeakMemoryUsage";
      }
      if (this.applicationSoftwareVersion == null) {
        missing += " applicationSoftwareVersion";
      }
      if (this.deviceManufacturer == null) {
        missing += " deviceManufacturer";
      }
      if (this.deviceName == null) {
        missing += " deviceName";
      }
      if (this.deviceOperatingSystem == null) {
        missing += " deviceOperatingSystem";
      }
      if (this.deviceTotalMemoryInMb == null) {
        missing += " deviceTotalMemoryInMb";
      }
      if (this.deviceUniqueId == null) {
        missing += " deviceUniqueId";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_DeviceInfo(
          this.applicationCurrentMemoryUsage,
          this.applicationPeakMemoryUsage,
          this.applicationSoftwareVersion,
          this.deviceManufacturer,
          this.deviceName,
          this.deviceOperatingSystem,
          this.deviceTotalMemoryInMb,
          this.deviceUniqueId,
          this.mobileHardwareVersion,
          this.webBrowserVersion);
    }
  }

}
