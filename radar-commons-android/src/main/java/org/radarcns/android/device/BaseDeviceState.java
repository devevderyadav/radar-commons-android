/*
 * Copyright 2017 The Hyve
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.radarcns.android.device;

import android.os.Parcel;
import android.os.Parcelable;

import android.support.annotation.CallSuper;
import org.radarcns.kafka.ObservationKey;

/** Current state of a wearable device. */
@SuppressWarnings({"unused", "WeakerAccess"})
public class BaseDeviceState implements Parcelable {
    public static final Parcelable.Creator<BaseDeviceState> CREATOR = new DeviceStateCreator<>(BaseDeviceState.class);

    private final ObservationKey id = new ObservationKey(null, null, null);
    private DeviceStatusListener.Status status = DeviceStatusListener.Status.DISCONNECTED;

    public DeviceStatusListener.Status getStatus() {
        return status;
    }

    public ObservationKey getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @CallSuper
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id.getProjectId());
        dest.writeString(id.getUserId());
        dest.writeString(id.getSourceId());
        dest.writeInt(status.ordinal());
    }

    @CallSuper
    public void updateFromParcel(Parcel in) {
        id.setProjectId(in.readString());
        id.setUserId(in.readString());
        id.setSourceId(in.readString());
        status = DeviceStatusListener.Status.values()[in.readInt()];
    }

    public synchronized void setStatus(DeviceStatusListener.Status status) {
        this.status = status;
    }

    /**
     * Get the battery level, between 0 (empty) and 1 (full).
     * @return battery level or Float.NaN if unknown.
     */
    public float getBatteryLevel()  {
        return Float.NaN;
    }

    /**
     * Whether the state will gather any temperature information. This implementation returns false.
     */
    public boolean hasTemperature() {
        return false;
    }

    /**
     * Get the temperature in degrees Celcius.
     * @return temperature or Float.NaN if unknown.
     */
    public float getTemperature() {
        return Float.NaN;
    }

    /**
     * Whether the state will gather any heart rate information. This implementation returns false.
     */
    public boolean hasHeartRate() {
        return false;
    }

    /**
     * Get the heart rate in bpm.
     * @return heart rate or Float.NaN if unknown.
     */
    public float getHeartRate() {
        return Float.NaN;
    }


    /**
     * Whether the state will gather any acceleration information. This implementation returns false.
     */
    public boolean hasAcceleration() {
        return false;
    }

    /**
     * Get the x, y and z components of the acceleration in g.
     * @return array of acceleration or of Float.NaN if unknown
     */
    public float[] getAcceleration() {
        return new float[] {Float.NaN, Float.NaN, Float.NaN};
    }

    /**
     * Get the magnitude of the acceleration in g, computed from {@link #getAcceleration()}.
     * @return acceleration or Float.NaN if unknown.
     */
    public float getAccelerationMagnitude() {
        float[] acceleration = getAcceleration();
        return (float) Math.sqrt(
                acceleration[0] * acceleration[0]
                + acceleration[1] * acceleration[1]
                + acceleration[2] * acceleration[2]);
    }
}
