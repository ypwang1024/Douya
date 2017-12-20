/*
 * Copyright (c) 2017 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.douya.network.api.info.frodo;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import me.zhanghai.android.douya.ui.SizedImageItem;

public class BroadcastCard implements Parcelable {

    public enum Type {

        IMAGES("images"),
        LARGE("large"),
        NORMAL("normal");

        private String mApiString;

        Type(String apiString) {
            mApiString = apiString;
        }

        public static Type ofApiString(String apiString, Type defaultValue) {
            for (Type type : Type.values()) {
                if (TextUtils.equals(type.mApiString, apiString)) {
                    return type;
                }
            }
            return defaultValue;
        }

        public static Type ofApiString(String apiString) {
            return ofApiString(apiString, null);
        }

        public String getApiString() {
            return mApiString;
        }
    }

    /**
     * @deprecated Use {@link #getType()} instead.
     */
    @SerializedName("card_style")
    public String type;

    @SerializedName("subtitle_entities")
    public ArrayList<TextEntity> entities = new ArrayList<>();

    @SerializedName("images_block")
    public ImageBlock imageBlock;

    /**
     * @deprecated Use {@link #getRatingUnavailableReason(Context)} instead.
     */
    @SerializedName("null_rating_reason")
    public String ratingUnavailableReason;

    public SimpleRating rating;

    public SizedImage image;

    @SerializedName("subtitle")
    public String text;

    public String title;

    public String uri;

    public String url;

    public Type getType() {
        //noinspection deprecation
        return Type.ofApiString(type);
    }

    public String getRatingUnavailableReason(Context context) {
        //noinspection deprecation
        return Rating.getRatingUnavailableReason(ratingUnavailableReason, context);
    }


    public static final Parcelable.Creator<BroadcastCard> CREATOR =
            new Parcelable.Creator<BroadcastCard>() {
                @Override
                public BroadcastCard createFromParcel(Parcel source) {
                    return new BroadcastCard(source);
                }
                @Override
                public BroadcastCard[] newArray(int size) {
                    return new BroadcastCard[size];
                }
            };

    public BroadcastCard() {}

    protected BroadcastCard(Parcel in) {
        type = in.readString();
        entities = in.createTypedArrayList(TextEntity.CREATOR);
        imageBlock = in.readParcelable(ImageBlock.class.getClassLoader());
        //noinspection deprecation
        ratingUnavailableReason = in.readString();
        rating = in.readParcelable(SimpleRating.class.getClassLoader());
        image = in.readParcelable(SizedImage.class.getClassLoader());
        text = in.readString();
        title = in.readString();
        uri = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeTypedList(entities);
        dest.writeParcelable(imageBlock, flags);
        //noinspection deprecation
        dest.writeString(ratingUnavailableReason);
        dest.writeParcelable(rating, flags);
        dest.writeParcelable(image, flags);
        dest.writeString(text);
        dest.writeString(title);
        dest.writeString(uri);
        dest.writeString(url);
    }


    public static class ImageBlock implements Parcelable {

        @SerializedName("count_str")
        public String countString;

        public ArrayList<Image> images = new ArrayList<>();


        public static final Creator<ImageBlock> CREATOR = new Creator<ImageBlock>() {
            @Override
            public ImageBlock createFromParcel(Parcel source) {
                return new ImageBlock(source);
            }
            @Override
            public ImageBlock[] newArray(int size) {
                return new ImageBlock[size];
            }
        };

        public ImageBlock() {}

        protected ImageBlock(Parcel in) {
            countString = in.readString();
            images = in.createTypedArrayList(Image.CREATOR);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(countString);
            dest.writeTypedList(images);
        }


        public static class Image implements SizedImageItem, Parcelable {

            public SizedImage image;

            public String uri;

            public SizedImage.Item getLarge() {
                return image.getLarge();
            }

            public SizedImage.Item getMedium() {
                return image.getMedium();
            }

            public SizedImage.Item getSmall() {
                return image.getSmall();
            }

            @Override
            public String getLargeUrl() {
                return image.getLargeUrl();
            }

            @Override
            public int getLargeWidth() {
                return image.getLargeWidth();
            }

            @Override
            public int getLargeHeight() {
                return image.getLargeHeight();
            }

            @Override
            public String getMediumUrl() {
                return image.getMediumUrl();
            }

            @Override
            public int getMediumWidth() {
                return image.getMediumWidth();
            }

            @Override
            public int getMediumHeight() {
                return image.getMediumHeight();
            }

            @Override
            public String getSmallUrl() {
                return image.getSmallUrl();
            }

            @Override
            public int getSmallWidth() {
                return image.getSmallWidth();
            }

            @Override
            public int getSmallHeight() {
                return image.getSmallHeight();
            }

            @Override
            public boolean isAnimated() {
                return image.isAnimated();
            }


            public static final Creator<Image> CREATOR = new Creator<Image>() {
                @Override
                public Image createFromParcel(Parcel source) {
                    return new Image(source);
                }
                @Override
                public Image[] newArray(int size) {
                    return new Image[size];
                }
            };

            public Image() {}

            protected Image(Parcel in) {
                image = in.readParcelable(SizedImage.class.getClassLoader());
                uri = in.readString();
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(image, flags);
                dest.writeString(uri);
            }
        }
    }
}
