package timeBench.data.io.schema;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import prefuse.data.Tuple;
import timeBench.calendar.Calendar;
import timeBench.calendar.Granularity;
import timeBench.data.GenericTemporalElement;
import timeBench.data.TemporalDataException;
import timeBench.data.TemporalDataset;
import timeBench.data.TemporalObject;

import javax.xml.bind.annotation.*;
import java.util.Map;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class TemporalObjectEncoding {

    protected static final Logger logger = LogManager.getLogger(TemporalObjectEncoding.class);

    @XmlAttribute(required = true)
    private String key = null;

    @XmlElementWrapper(name = "data-element", required = false)
    @XmlElement(name = "column")
    private String[] dataColumns = new String[0];

    @XmlElement(name = "granularity-id", required = true)
    private int granularityId;

    @XmlElement(name = "granularity-context-id", required = true)
    private int granularityContextId;
    
    @XmlTransient
    private Granularity granularity = null;
    
    @XmlTransient
    private boolean temporalObjectIdIncluded = false;

    // TODO declare optional encodings that can safely be skipped vs. required
    // encodings that are needed (e.g. for an interval)

    // same manager for all in a dataset
    // private CalendarManager manager;

    TemporalObjectEncoding() {
    }

    public TemporalObjectEncoding(String key, String[] dataColumns) {
        super();
        this.setKey(key);
        this.dataColumns = dataColumns;
    }

    void init(Calendar calendar) throws TemporalDataException {
        if (granularity == null)
            this.granularity = new Granularity(calendar, granularityId, granularityContextId);
        
        this.temporalObjectIdIncluded = ArrayUtils.contains(this.dataColumns, 
                TemporalObject.ID);
    }

    public abstract void buildTemporalElement(TemporalDataset tmpds,
            Tuple tuple, Map<String, GenericTemporalElement> elements)
            throws TemporalDataException;

    public String[] getDataColumns() {
        return dataColumns;
    }

    public void setDataColumns(String[] dataColumns) {
        this.dataColumns = dataColumns;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public void setGranularity(Granularity granularity) {
        this.granularity = granularity;
        this.granularityId = granularity.getIdentifier();
    }

    public boolean isTemporalObjectIdIncluded() {
        return temporalObjectIdIncluded;
    }
}
