
package org.molgenis.genomebrowser;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.genomebrowser.meta.GenomeBrowserAttributes;
import org.molgenis.genomebrowser.meta.GenomeBrowserSettings;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GenomeBrowserTrack extends GenomeBrowserTrack {

  private final String id;
  private final String label;
  private final String labelAttr;
  private final EntityType entity;
  private final GenomeBrowserSettings.TrackType trackType;
  private final Iterable<GenomeBrowserTrack> molgenisReferenceTracks;
  private final GenomeBrowserSettings.MolgenisReferenceMode molgenisReferenceMode;
  private final GenomeBrowserAttributes genomeBrowserAttrs;
  private final String actions;
  private final String attrs;
  private final String scoreAttr;
  private final String exonKey;

  AutoValue_GenomeBrowserTrack(
      String id,
      String label,
      String labelAttr,
      EntityType entity,
      GenomeBrowserSettings.TrackType trackType,
      @Nullable Iterable<GenomeBrowserTrack> molgenisReferenceTracks,
      GenomeBrowserSettings.MolgenisReferenceMode molgenisReferenceMode,
      GenomeBrowserAttributes genomeBrowserAttrs,
      @Nullable String actions,
      @Nullable String attrs,
      @Nullable String scoreAttr,
      @Nullable String exonKey) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (label == null) {
      throw new NullPointerException("Null label");
    }
    this.label = label;
    if (labelAttr == null) {
      throw new NullPointerException("Null labelAttr");
    }
    this.labelAttr = labelAttr;
    if (entity == null) {
      throw new NullPointerException("Null entity");
    }
    this.entity = entity;
    if (trackType == null) {
      throw new NullPointerException("Null trackType");
    }
    this.trackType = trackType;
    this.molgenisReferenceTracks = molgenisReferenceTracks;
    if (molgenisReferenceMode == null) {
      throw new NullPointerException("Null molgenisReferenceMode");
    }
    this.molgenisReferenceMode = molgenisReferenceMode;
    if (genomeBrowserAttrs == null) {
      throw new NullPointerException("Null genomeBrowserAttrs");
    }
    this.genomeBrowserAttrs = genomeBrowserAttrs;
    this.actions = actions;
    this.attrs = attrs;
    this.scoreAttr = scoreAttr;
    this.exonKey = exonKey;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public String getLabelAttr() {
    return labelAttr;
  }

  @Override
  public EntityType getEntity() {
    return entity;
  }

  @Override
  public GenomeBrowserSettings.TrackType getTrackType() {
    return trackType;
  }

  @Nullable
  @Override
  public Iterable<GenomeBrowserTrack> getMolgenisReferenceTracks() {
    return molgenisReferenceTracks;
  }

  @Override
  public GenomeBrowserSettings.MolgenisReferenceMode getMolgenisReferenceMode() {
    return molgenisReferenceMode;
  }

  @Override
  public GenomeBrowserAttributes getGenomeBrowserAttrs() {
    return genomeBrowserAttrs;
  }

  @Nullable
  @Override
  public String getActions() {
    return actions;
  }

  @Nullable
  @Override
  public String getAttrs() {
    return attrs;
  }

  @Nullable
  @Override
  public String getScoreAttr() {
    return scoreAttr;
  }

  @Nullable
  @Override
  public String getExonKey() {
    return exonKey;
  }

  @Override
  public String toString() {
    return "GenomeBrowserTrack{"
        + "id=" + id + ", "
        + "label=" + label + ", "
        + "labelAttr=" + labelAttr + ", "
        + "entity=" + entity + ", "
        + "trackType=" + trackType + ", "
        + "molgenisReferenceTracks=" + molgenisReferenceTracks + ", "
        + "molgenisReferenceMode=" + molgenisReferenceMode + ", "
        + "genomeBrowserAttrs=" + genomeBrowserAttrs + ", "
        + "actions=" + actions + ", "
        + "attrs=" + attrs + ", "
        + "scoreAttr=" + scoreAttr + ", "
        + "exonKey=" + exonKey
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GenomeBrowserTrack) {
      GenomeBrowserTrack that = (GenomeBrowserTrack) o;
      return (this.id.equals(that.getId()))
           && (this.label.equals(that.getLabel()))
           && (this.labelAttr.equals(that.getLabelAttr()))
           && (this.entity.equals(that.getEntity()))
           && (this.trackType.equals(that.getTrackType()))
           && ((this.molgenisReferenceTracks == null) ? (that.getMolgenisReferenceTracks() == null) : this.molgenisReferenceTracks.equals(that.getMolgenisReferenceTracks()))
           && (this.molgenisReferenceMode.equals(that.getMolgenisReferenceMode()))
           && (this.genomeBrowserAttrs.equals(that.getGenomeBrowserAttrs()))
           && ((this.actions == null) ? (that.getActions() == null) : this.actions.equals(that.getActions()))
           && ((this.attrs == null) ? (that.getAttrs() == null) : this.attrs.equals(that.getAttrs()))
           && ((this.scoreAttr == null) ? (that.getScoreAttr() == null) : this.scoreAttr.equals(that.getScoreAttr()))
           && ((this.exonKey == null) ? (that.getExonKey() == null) : this.exonKey.equals(that.getExonKey()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.label.hashCode();
    h *= 1000003;
    h ^= this.labelAttr.hashCode();
    h *= 1000003;
    h ^= this.entity.hashCode();
    h *= 1000003;
    h ^= this.trackType.hashCode();
    h *= 1000003;
    h ^= (molgenisReferenceTracks == null) ? 0 : this.molgenisReferenceTracks.hashCode();
    h *= 1000003;
    h ^= this.molgenisReferenceMode.hashCode();
    h *= 1000003;
    h ^= this.genomeBrowserAttrs.hashCode();
    h *= 1000003;
    h ^= (actions == null) ? 0 : this.actions.hashCode();
    h *= 1000003;
    h ^= (attrs == null) ? 0 : this.attrs.hashCode();
    h *= 1000003;
    h ^= (scoreAttr == null) ? 0 : this.scoreAttr.hashCode();
    h *= 1000003;
    h ^= (exonKey == null) ? 0 : this.exonKey.hashCode();
    return h;
  }

}
