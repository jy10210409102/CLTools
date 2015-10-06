/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "help_topic", catalog = "mysql", schema = "")
@NamedQueries({
    @NamedQuery(name = "HelpTopic.findAll", query = "SELECT h FROM HelpTopic h"),
    @NamedQuery(name = "HelpTopic.findByHelpTopicId", query = "SELECT h FROM HelpTopic h WHERE h.helpTopicId = :helpTopicId"),
    @NamedQuery(name = "HelpTopic.findByName", query = "SELECT h FROM HelpTopic h WHERE h.name = :name"),
    @NamedQuery(name = "HelpTopic.findByHelpCategoryId", query = "SELECT h FROM HelpTopic h WHERE h.helpCategoryId = :helpCategoryId"),
    @NamedQuery(name = "HelpTopic.findByUrl", query = "SELECT h FROM HelpTopic h WHERE h.url = :url")})
public class HelpTopic implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "help_topic_id")
    private Integer helpTopicId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "help_category_id")
    private short helpCategoryId;
    @Basic(optional = false)
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Lob
    @Column(name = "example")
    private String example;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;

    public HelpTopic() {
    }

    public HelpTopic(Integer helpTopicId) {
        this.helpTopicId = helpTopicId;
    }

    public HelpTopic(Integer helpTopicId, String name, short helpCategoryId, String description, String example, String url) {
        this.helpTopicId = helpTopicId;
        this.name = name;
        this.helpCategoryId = helpCategoryId;
        this.description = description;
        this.example = example;
        this.url = url;
    }

    public Integer getHelpTopicId() {
        return helpTopicId;
    }

    public void setHelpTopicId(Integer helpTopicId) {
        Integer oldHelpTopicId = this.helpTopicId;
        this.helpTopicId = helpTopicId;
        changeSupport.firePropertyChange("helpTopicId", oldHelpTopicId, helpTopicId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", oldName, name);
    }

    public short getHelpCategoryId() {
        return helpCategoryId;
    }

    public void setHelpCategoryId(short helpCategoryId) {
        short oldHelpCategoryId = this.helpCategoryId;
        this.helpCategoryId = helpCategoryId;
        changeSupport.firePropertyChange("helpCategoryId", oldHelpCategoryId, helpCategoryId);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        changeSupport.firePropertyChange("description", oldDescription, description);
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        String oldExample = this.example;
        this.example = example;
        changeSupport.firePropertyChange("example", oldExample, example);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        String oldUrl = this.url;
        this.url = url;
        changeSupport.firePropertyChange("url", oldUrl, url);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (helpTopicId != null ? helpTopicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HelpTopic)) {
            return false;
        }
        HelpTopic other = (HelpTopic) object;
        if ((this.helpTopicId == null && other.helpTopicId != null) || (this.helpTopicId != null && !this.helpTopicId.equals(other.helpTopicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.HelpTopic[ helpTopicId=" + helpTopicId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
