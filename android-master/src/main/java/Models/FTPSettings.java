package Models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by thiba on 26/10/2016.
 */

public class FTPSettings extends BaseSettings{

    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("port")
    @Expose
    private Integer port;
    @SerializedName("maxclients")
    @Expose
    private Integer maxclients;
    @SerializedName("maxconnectionsperhost")
    @Expose
    private Integer maxconnectionsperhost;
    @SerializedName("maxloginattempts")
    @Expose
    private Integer maxloginattempts;
    @SerializedName("timeoutidle")
    @Expose
    private Integer timeoutidle;
    @SerializedName("anonymous")
    @Expose
    private Boolean anonymous;

    @SerializedName("displaylogin")
    @Expose
    private String displaylogin;
    @SerializedName("rootlogin")
    @Expose
    private Boolean rootlogin;
    @SerializedName("requirevalidshell")
    @Expose
    private Boolean requirevalidshell;
    @SerializedName("limittransferrate")
    @Expose
    private Boolean limittransferrate;
    @SerializedName("maxuptransferrate")
    @Expose
    private Integer maxuptransferrate;
    @SerializedName("maxdowntransferrate")
    @Expose
    private Integer maxdowntransferrate;
    @SerializedName("usepassiveports")
    @Expose
    private Boolean usepassiveports;
    @SerializedName("minpassiveports")
    @Expose
    private Integer minpassiveports;
    @SerializedName("maxpassiveports")
    @Expose
    private Integer maxpassiveports;
    @SerializedName("masqueradeaddress")
    @Expose
    private String masqueradeaddress;
    @SerializedName("dynmasqrefresh")
    @Expose
    private Integer dynmasqrefresh;
    @SerializedName("allowforeignaddress")
    @Expose
    private Boolean allowforeignaddress;
    @SerializedName("allowrestart")
    @Expose
    private Boolean allowrestart;
    @SerializedName("identlookups")
    @Expose
    private Boolean identlookups;
    @SerializedName("usereversedns")
    @Expose
    private Boolean usereversedns;
    @SerializedName("transferlog")
    @Expose
    private Boolean transferlog;
    @SerializedName("extraoptions")
    @Expose
    private String extraoptions;
    @SerializedName("sharedfolderref")
    @Expose
    private String sharedfolderref;

    /**
     *
     * @return
     * The enable
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     *
     * @param enable
     * The enable
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     *
     * @return
     * The port
     */
    public Integer getPort() {
        return port;
    }

    /**
     *
     * @param port
     * The port
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     *
     * @return
     * The maxclients
     */
    public Integer getMaxclients() {
        return maxclients;
    }

    /**
     *
     * @param maxclients
     * The retransmit
     */
    public void setMaxclients(Integer maxclients) {
        this.maxclients = maxclients;
    }

    /**
     *
     * @return
     * The maxconnectionsperhost
     */
    public Integer getMaxconnectionsperhost() {
        return maxconnectionsperhost;
    }

    /**
     *
     * @param maxconnectionsperhost
     * The maxconnectionsperhost
     */
    public void setMaxconnectionsperhost(Integer maxconnectionsperhost) {
        this.maxconnectionsperhost = maxconnectionsperhost;
    }

    /**
     *
     * @return
     * The maxloginattempts
     */
    public Integer getMaxloginattempts() {
        return maxloginattempts;
    }

    /**
     *
     * @param maxloginattempts
     * The allownewfiles
     */
    public void setMaxloginattempts(Integer maxloginattempts) {
        this.maxloginattempts = maxloginattempts;
    }
    public Integer getTimeoutidle(){
        return timeoutidle;
    }
    public void setTimeoutidle(Integer timeoutidle){
        this.timeoutidle = timeoutidle;
    }
    /**
     *
     * @return
     * The anonymous
     */
    public Boolean getAnonymous() {
        return anonymous;
    }
    /**
     *
     * @param anonymous
     * The anonymous
     */
    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    /**
     *
     * @return
     * The displaylogin
     */
    public String getDisplaylogin() {
        return displaylogin;
    }

    /**
     *
     * @param displaylogin
     * The displaylogin
     */
    public void setDisplaylogin(String displaylogin) {
        this.displaylogin = displaylogin;
    }

    /**
     * @return
     * The rootlogin
     */
    public Boolean getRootlogin() {return rootlogin;}

    /**
     * @param rootlogin
     * The rootlogin
     */
    public void setRootlogin(Boolean rootlogin) {
        this.rootlogin = rootlogin;
    }
    /**
     * @return
     * The requirevalidshell
     */
    public Boolean getRequirevalidshell() {
        return requirevalidshell;
    }

    /**
     * @param requirevalidshell
     * The requirevalidshell
     */
    public void setRequirevalidshell(Boolean requirevalidshell) {
        this.requirevalidshell = requirevalidshell;
    }

    /**
     * @return
     * The limittransferrate
     */
    public Boolean getLimittransferrate() {
        return limittransferrate;
    }

    /**
     * @param limittransferrate
     * The limittransferrate
     */
    public void setLimittransferrate(Boolean limittransferrate) {
        this.limittransferrate = limittransferrate;
    }

    /**
     * @return
     * The maxuptransferrate
     */
    public Integer getMaxuptransferrate() {
        return maxuptransferrate;
    }

    /**
     *
     * @param maxuptransferrate
     * The maxuptransferrate
     */
    public void setMaxuptransferrate(Integer maxuptransferrate) {
        this.maxuptransferrate = maxuptransferrate;
    }

    /**
     *
     * @return
     * The maxdowntransferrate
     */
    public Integer getMaxdowntransferrate() {
        return maxdowntransferrate;
    }

    /**
     *
     * @param maxdowntransferrate
     * The maxdowntransferrate
     */
    public void setMaxdowntransferrate(Integer maxdowntransferrate) {
        this.maxdowntransferrate = maxdowntransferrate;
    }

    /**
     *
     * @return
     * The usepassiveports
     */
    public Boolean getUsepassiveports() {
        return usepassiveports;
    }

    /**
     *
     * @param usepassiveports
     * The usepassiveports
     */
    public void setUsepassiveports(Boolean usepassiveports) {
        this.usepassiveports = usepassiveports;
    }

    /**
     *
     * @return
     * The minpassiveports
     */
    public Integer getMinpassiveports() {
        return minpassiveports;
    }

    /**
     *
     * @param minpassiveports
     * The minpassiveports
     */
    public void setMinpassiveports(Integer minpassiveports) {
        this.minpassiveports = minpassiveports;
    }

    /**
     *
     * @return
     * The maxpassiveports
     */
    public Integer getMaxpassiveports() {
        return maxpassiveports;
    }

    /**
     *
     * @param maxpassiveports
     * The maxpassiveports
     */
    public void setMaxpassiveports(Integer maxpassiveports) {
        this.maxpassiveports = maxpassiveports;
    }

    /**
     *
     * @return
     * The masqueradeaddress
     */
    public String getMasqueradeaddress() {
        return masqueradeaddress;
    }

    /**
     *
     * @param masqueradeaddress
     * The masqueradeaddress
     */
    public void setMasqueradeaddress(String masqueradeaddress) {
        this.masqueradeaddress = masqueradeaddress;
    }

    /**
     *
     * @return
     * The dynmasqrefresh
     */
    public Integer getDynmasqrefresh() {
        return dynmasqrefresh;
    }

    /**
     *
     * @param dynmasqrefresh
     * The dynmasqrefresh
     */
    public void setDynmasqrefresh(Integer dynmasqrefresh) {
        this.dynmasqrefresh = dynmasqrefresh;
    }

    /**
     *
     * @return
     * The allowforeignaddress
     */
    public Boolean getAllowforeignaddress() {
        return allowforeignaddress;
    }

    /**
     *
     * @param allowforeignaddress
     * The allowforeignaddress
     */
    public void setAllowforeignaddress(Boolean allowforeignaddress) {
        this.allowforeignaddress = allowforeignaddress;
    }

    /**
     *
     * @return
     * The allowrestart
     */
    public Boolean getAllowrestart() {
        return allowrestart;
    }

    /**
     *
     * @param allowrestart
     * The allowrestart
     */
    public void setAllowrestart(Boolean allowrestart) {
        this.allowrestart = allowrestart;
    }

    /**
     *
     * @return
     * The identlookups
     */
    public Boolean getIdentlookups() {
        return identlookups;
    }

    /**
     *
     * @param identlookups
     * The identlookups
     */
    public void setIdentlookups(Boolean identlookups) {
        this.identlookups = identlookups;
    }

    /**
     *
     * @return
     * The usereversedns
     */
    public Boolean getUsereversedns() {
        return usereversedns;
    }

    /**
     *
     * @param usereversedns
     * The usereversedns
     */
    public void setUsereversedns(Boolean usereversedns) {
        this.usereversedns = usereversedns;
    }

    /**
     *
     * @return
     * The transferlog
     */
    public Boolean getTransferlog() {
        return transferlog;
    }

    /**
     *
     * @param transferlog
     * The transferlog
     */
    public void setTransferlog(Boolean transferlog) {
        this.transferlog = transferlog;
    }

    /**
     *
     * @return
     * The extraoptions
     */
    public String getExtraoptions() {
        return extraoptions;
    }

    /**
     *
     * @param extraoptions
     * The extraoptions
     */
    public void setExtraoptions(String extraoptions) {
        this.extraoptions = extraoptions;
    }

    public String getSharedfolderref() {
        return sharedfolderref;
    }

    public void setSharedfolderref(String sharedfolderref) {
        this.sharedfolderref = sharedfolderref;
    }

    private FTPSettings()
    {
        super("FTP");

    }

    public Map<String, String> getPArams()
    {
        Map<String, String> dictionary = new HashMap<String, String>();

        dictionary.put("enable",enable?"mTrue":"mFalse");
//        dictionary.put("allownewfiles",allownewfiles?"mTrue":"mFalse");
//        dictionary.put("port",port.toString());
//        dictionary.put("retransmit",retransmit.toString());
//        dictionary.put("blocksize",blocksize.toString());
//        dictionary.put("sharedfolderref",sharedfolderref);
//        dictionary.put("extraoptions",extraoptions);

        return dictionary;
    }

    @Override
    public String ToJson()
    {

        Log.d("xuzhenyue","ToJson");
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"enable\":"+(enable?"true":"false"));
        sb.append(",\"port\":"+port.toString());
        sb.append(",\"maxclients\":"+maxclients);
        sb.append(",\"maxconnectionsperhost\":"+maxconnectionsperhost);
        sb.append(",\"maxloginattempts\":"+maxloginattempts);
        sb.append(",\"timeoutidle\":"+timeoutidle);
        sb.append(",\"anonymous\":"+(anonymous?"true":"false"));
        sb.append(",\"displaylogin\":\""+displaylogin +"\"");
        sb.append(",\"rootlogin\":"+(rootlogin?"true":"false"));
        sb.append(",\"requirevalidshell\":"+(requirevalidshell?"true":"false"));
        sb.append(",\"limittransferrate\":"+(limittransferrate?"true":"false"));
        sb.append(",\"maxuptransferrate\":"+maxuptransferrate);
        sb.append(",\"maxdowntransferrate\":"+maxdowntransferrate);
        sb.append(",\"usepassiveports\":"+(usepassiveports?"true":"false"));
        sb.append(",\"minpassiveports\":"+minpassiveports);
        sb.append(",\"maxpassiveports\":"+maxpassiveports);
        sb.append(",\"masqueradeaddress\":\""+masqueradeaddress+"\"");
        sb.append(",\"dynmasqrefresh\":"+dynmasqrefresh);
        sb.append(",\"allowforeignaddress\":"+(allowforeignaddress?"true":"false"));
        sb.append(",\"allowrestart\":"+(allowrestart?"true":"false"));
        sb.append(",\"identlookups\":"+(identlookups?"true":"false"));
        sb.append(",\"usereversedns\":"+(usereversedns?"true":"false"));
        sb.append(",\"transferlog\":"+(transferlog?"true":"false"));
        //sb.append(",\"allownewfiles\":"+(allownewfiles?"true":"false"));
        //sb.append(",\"sharedfolderref\":\""+sharedfolderref+"\"");
        //sb.append(",\"retransmit\":"+retransmit);
       // sb.append(",\"blocksize\":"+blocksize);
        sb.append(",\"extraoptions\":\""+extraoptions+"\"");
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(port).append(maxclients).append(maxconnectionsperhost).append(maxloginattempts).
                append(timeoutidle).append(anonymous).append(displaylogin).append(rootlogin).append(requirevalidshell).append(limittransferrate)
                .append(maxuptransferrate).append(maxdowntransferrate).append(usepassiveports).append(minpassiveports).append(maxpassiveports)
                .append(masqueradeaddress).append(dynmasqrefresh).append(allowforeignaddress).append(allowrestart).append(identlookups)
                .append(usereversedns).append(transferlog).append(extraoptions).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FTPSettings) == false) {
            return false;
        }
        FTPSettings rhs = ((FTPSettings) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(port, rhs.port).append(maxclients, rhs.maxclients).append(maxconnectionsperhost, rhs.maxconnectionsperhost)
                .append(maxloginattempts, rhs.maxloginattempts).append(timeoutidle, rhs.timeoutidle).append(anonymous, rhs.anonymous)
                .append(displaylogin, rhs.displaylogin).append(rootlogin, rhs.rootlogin).append(requirevalidshell, rhs.requirevalidshell)
                .append(limittransferrate, rhs.limittransferrate).append(maxuptransferrate, rhs.maxuptransferrate).append(maxdowntransferrate, rhs.maxdowntransferrate)
                .append(usepassiveports, rhs.usepassiveports).append(minpassiveports, rhs.minpassiveports).append(maxpassiveports, rhs.maxpassiveports)
                .append(masqueradeaddress, rhs.masqueradeaddress).append(dynmasqrefresh, rhs.dynmasqrefresh).append(allowforeignaddress, rhs.allowforeignaddress)
                .append(allowrestart, rhs.allowrestart).append(identlookups, rhs.identlookups).append(usereversedns, rhs.usereversedns)
                .append(transferlog, rhs.transferlog).append(extraoptions, rhs.extraoptions).isEquals();
    }


}
