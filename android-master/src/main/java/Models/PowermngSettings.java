package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by thiba on 14/10/2016.
 */

public class PowermngSettings {

    @SerializedName("cpufreq")
    @Expose
    private Boolean cpufreq;
    @SerializedName("powerbtn")
    @Expose
    private String powerbtn;

    @SerializedName("hostname")
    @Expose
    private String hostname;


    /**
     *
     * @return
     * The cpufreq
     */
    public Boolean getCpufreq() {
        return cpufreq;
    }

    /**
     *
     * @param cpufreq
     * The cpufreq
     */
    public void setCpufreq(Boolean cpufreq) {
        this.cpufreq = cpufreq;
    }

    /**
     *
     * @return
     * The powerbtn
     */
    public String getPowerbtn() {
        return powerbtn;
    }

    /**
     *
     * @param powerbtn
     * The powerbtn
     */
    public void setPowerbtn(String powerbtn) {
        this.powerbtn = powerbtn;
    }


    /**
     *
     * @return
     * The powerbtn
     */
    public String getHostname() {
        return hostname;
    }

    /**
     *
     * @param hostname
     * The hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

}

