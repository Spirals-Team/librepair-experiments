package it.feio.android.omninotes.models;


public class Attachment extends it.feio.android.omninotes.commons.models.Attachment implements android.os.Parcelable {
    private android.net.Uri uri;

    public Attachment(android.net.Uri uri, java.lang.String mime_type) {
        super();
        setUri(uri);
        setMime_type(mime_type);
    }

    public Attachment(int id, android.net.Uri uri, java.lang.String name, int size, long length, java.lang.String mime_type) {
        super();
        setId(id);
        setUri(uri);
        setName(name);
        setSize(size);
        setLength(length);
        setMime_type(mime_type);
    }

    private Attachment(android.os.Parcel in) {
        setId(in.readInt());
        setUri(android.net.Uri.parse(in.readString()));
        setMime_type(in.readString());
    }

    public android.net.Uri getUri() {
        return uri;
    }

    public void setUri(android.net.Uri uri) {
        this.uri = uri;
        setUriPath(uri.getPath());
    }

    @java.lang.Override
    public int describeContents() {
        return 0;
    }

    @java.lang.Override
    public void writeToParcel(android.os.Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getUri().toString());
        parcel.writeString(getMime_type());
    }

    public static final android.os.Parcelable.Creator<it.feio.android.omninotes.models.Attachment> CREATOR = new android.os.Parcelable.Creator<it.feio.android.omninotes.models.Attachment>() {
        public it.feio.android.omninotes.models.Attachment createFromParcel(android.os.Parcel in) {
            return new it.feio.android.omninotes.models.Attachment(in);
        }

        public it.feio.android.omninotes.models.Attachment[] newArray(int size) {
            return new it.feio.android.omninotes.models.Attachment[size];
        }
    };
}

