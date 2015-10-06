/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "language", catalog = "zh_dic", schema = "")
@NamedQueries({
    @NamedQuery(name = "Language.findAll", query = "SELECT l FROM Language l"),
    @NamedQuery(name = "Language.findById", query = "SELECT l FROM Language l WHERE l.id = :id")})
public class Language implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "SRC")
    private String src;
    @Basic(optional = false)
    @Lob
    @Column(name = "TIME")
    private String time;
    @Lob
    @Column(name = "ENGLISH")
    private String english;
    @Lob
    @Column(name = "CHS")
    private String chs;
    @Lob
    @Column(name = "CHR")
    private String chr;
    @Lob
    @Column(name = "ARABIC")
    private String arabic;
    @Lob
    @Column(name = "HEBREW")
    private String hebrew;
    @Lob
    @Column(name = "RUSSIAN")
    private String russian;
    @Lob
    @Column(name = "GREECE")
    private String greece;
    @Lob
    @Column(name = "SLOVENE")
    private String slovene;
    @Lob
    @Column(name = "PORTUGAL")
    private String portugal;
    @Lob
    @Column(name = "TURKSIH")
    private String turksih;
    @Lob
    @Column(name = "FRENCH")
    private String french;
    @Lob
    @Column(name = "GERMAN")
    private String german;
    @Lob
    @Column(name = "ITALY")
    private String italy;
    @Lob
    @Column(name = "SPAIN")
    private String spain;
    @Lob
    @Column(name = "HUNGARY")
    private String hungary;
    @Lob
    @Column(name = "POLISH")
    private String polish;
    @Lob
    @Column(name = "RUMANIA")
    private String rumania;
    @Lob
    @Column(name = "CZECH")
    private String czech;
    @Lob
    @Column(name = "VIETNAMESE")
    private String vietnamese;
    @Lob
    @Column(name = "DENMARK")
    private String denmark;
    @Lob
    @Column(name = "JAPANESE")
    private String japanese;
    @Lob
    @Column(name = "THAI")
    private String thai;

    public Language() {
    }

    public Language(Integer id) {
        this.id = id;
    }

    public Language(Integer id, String src, String time) {
        this.id = id;
        this.src = src;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        String oldSrc = this.src;
        this.src = src;
        changeSupport.firePropertyChange("src", oldSrc, src);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        String oldTime = this.time;
        this.time = time;
        changeSupport.firePropertyChange("time", oldTime, time);
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        String oldEnglish = this.english;
        this.english = english;
        changeSupport.firePropertyChange("english", oldEnglish, english);
    }

    public String getChs() {
        return chs;
    }

    public void setChs(String chs) {
        String oldChs = this.chs;
        this.chs = chs;
        changeSupport.firePropertyChange("chs", oldChs, chs);
    }

    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        String oldChr = this.chr;
        this.chr = chr;
        changeSupport.firePropertyChange("chr", oldChr, chr);
    }

    public String getArabic() {
        return arabic;
    }

    public void setArabic(String arabic) {
        String oldArabic = this.arabic;
        this.arabic = arabic;
        changeSupport.firePropertyChange("arabic", oldArabic, arabic);
    }

    public String getHebrew() {
        return hebrew;
    }

    public void setHebrew(String hebrew) {
        String oldHebrew = this.hebrew;
        this.hebrew = hebrew;
        changeSupport.firePropertyChange("hebrew", oldHebrew, hebrew);
    }

    public String getRussian() {
        return russian;
    }

    public void setRussian(String russian) {
        String oldRussian = this.russian;
        this.russian = russian;
        changeSupport.firePropertyChange("russian", oldRussian, russian);
    }

    public String getGreece() {
        return greece;
    }

    public void setGreece(String greece) {
        String oldGreece = this.greece;
        this.greece = greece;
        changeSupport.firePropertyChange("greece", oldGreece, greece);
    }

    public String getSlovene() {
        return slovene;
    }

    public void setSlovene(String slovene) {
        String oldSlovene = this.slovene;
        this.slovene = slovene;
        changeSupport.firePropertyChange("slovene", oldSlovene, slovene);
    }

    public String getPortugal() {
        return portugal;
    }

    public void setPortugal(String portugal) {
        String oldPortugal = this.portugal;
        this.portugal = portugal;
        changeSupport.firePropertyChange("portugal", oldPortugal, portugal);
    }

    public String getTurksih() {
        return turksih;
    }

    public void setTurksih(String turksih) {
        String oldTurksih = this.turksih;
        this.turksih = turksih;
        changeSupport.firePropertyChange("turksih", oldTurksih, turksih);
    }

    public String getFrench() {
        return french;
    }

    public void setFrench(String french) {
        String oldFrench = this.french;
        this.french = french;
        changeSupport.firePropertyChange("french", oldFrench, french);
    }

    public String getGerman() {
        return german;
    }

    public void setGerman(String german) {
        String oldGerman = this.german;
        this.german = german;
        changeSupport.firePropertyChange("german", oldGerman, german);
    }

    public String getItaly() {
        return italy;
    }

    public void setItaly(String italy) {
        String oldItaly = this.italy;
        this.italy = italy;
        changeSupport.firePropertyChange("italy", oldItaly, italy);
    }

    public String getSpain() {
        return spain;
    }

    public void setSpain(String spain) {
        String oldSpain = this.spain;
        this.spain = spain;
        changeSupport.firePropertyChange("spain", oldSpain, spain);
    }

    public String getHungary() {
        return hungary;
    }

    public void setHungary(String hungary) {
        String oldHungary = this.hungary;
        this.hungary = hungary;
        changeSupport.firePropertyChange("hungary", oldHungary, hungary);
    }

    public String getPolish() {
        return polish;
    }

    public void setPolish(String polish) {
        String oldPolish = this.polish;
        this.polish = polish;
        changeSupport.firePropertyChange("polish", oldPolish, polish);
    }

    public String getRumania() {
        return rumania;
    }

    public void setRumania(String rumania) {
        String oldRumania = this.rumania;
        this.rumania = rumania;
        changeSupport.firePropertyChange("rumania", oldRumania, rumania);
    }

    public String getCzech() {
        return czech;
    }

    public void setCzech(String czech) {
        String oldCzech = this.czech;
        this.czech = czech;
        changeSupport.firePropertyChange("czech", oldCzech, czech);
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public void setVietnamese(String vietnamese) {
        String oldVietnamese = this.vietnamese;
        this.vietnamese = vietnamese;
        changeSupport.firePropertyChange("vietnamese", oldVietnamese, vietnamese);
    }

    public String getDenmark() {
        return denmark;
    }

    public void setDenmark(String denmark) {
        String oldDenmark = this.denmark;
        this.denmark = denmark;
        changeSupport.firePropertyChange("denmark", oldDenmark, denmark);
    }

    public String getJapanese() {
        return japanese;
    }

    public void setJapanese(String japanese) {
        String oldJapanese = this.japanese;
        this.japanese = japanese;
        changeSupport.firePropertyChange("japanese", oldJapanese, japanese);
    }

    public String getThai() {
        return thai;
    }

    public void setThai(String thai) {
        String oldThai = this.thai;
        this.thai = thai;
        changeSupport.firePropertyChange("thai", oldThai, thai);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Language)) {
            return false;
        }
        Language other = (Language) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.Language[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
