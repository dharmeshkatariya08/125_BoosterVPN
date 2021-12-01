package com.booster.vpn.AdsClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.booster.vpn.R;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.List;

public class Inflate_ADS {
    Context activity;

    public Inflate_ADS(Context context) {
        this.activity = context;
    }

    public void inflate_NATIV_ADMOB(NativeAd unifiedNativeAd, ViewGroup viewGroup) {
        try {
            viewGroup.setVisibility(0);
            View inflate = LayoutInflater.from(this.activity).inflate(R.layout.ads_nativ_admob, (ViewGroup) null);
            viewGroup.removeAllViews();
            viewGroup.addView(inflate);
            NativeAdView unifiedNativeAdView = (NativeAdView) inflate.findViewById(R.id.uadview);
            unifiedNativeAdView.setMediaView((com.google.android.gms.ads.nativead.MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
            unifiedNativeAdView.setPriceView(unifiedNativeAdView.findViewById(R.id.ad_price));
            unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ad_stars));
            unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.ad_store));
            unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.ad_advertiser));
            ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            unifiedNativeAdView.getMediaView().setMediaContent(unifiedNativeAd.getMediaContent());
            if (unifiedNativeAd.getBody() == null) {
                unifiedNativeAdView.getBodyView().setVisibility(4);
            } else {
                unifiedNativeAdView.getBodyView().setVisibility(0);
                ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
            }
            if (unifiedNativeAd.getCallToAction() == null) {
                unifiedNativeAdView.getCallToActionView().setVisibility(4);
            } else {
                unifiedNativeAdView.getCallToActionView().setVisibility(0);
                ((TextView) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
            }
            if (unifiedNativeAd.getIcon() == null) {
                unifiedNativeAdView.getIconView().setVisibility(8);
            } else {
                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                unifiedNativeAdView.getIconView().setVisibility(0);
            }
            if (unifiedNativeAd.getStarRating() == null) {
                unifiedNativeAdView.getStarRatingView().setVisibility(4);
            } else {
                ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
                unifiedNativeAdView.getStarRatingView().setVisibility(0);
            }
            if (unifiedNativeAd.getAdvertiser() == null) {
                unifiedNativeAdView.getAdvertiserView().setVisibility(4);
            } else {
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
                unifiedNativeAdView.getAdvertiserView().setVisibility(0);
            }
            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        } catch (Exception e) {

        }
//        unifiedNativeAd.getVideoController();
    }


    public void inflate_Small_NATIV_ADMOB(NativeAd nativeAd, ViewGroup viewGroup) {
        try {
            viewGroup.setVisibility(0);
            View inflate = LayoutInflater.from(this.activity).inflate(R.layout.ads_nativ_admob3, (ViewGroup) null);
            viewGroup.removeAllViews();
            viewGroup.addView(inflate);

//            NativeTemplateStyle styles = new
//                    NativeTemplateStyle.Builder().build();
//            TemplateView template = inflate.findViewById(R.id.my_template);
//            template.setStyles(styles);
//            template.setNativeAd(nativeAd);

            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
            Native_TemplateView template = inflate.findViewById(R.id.my_template);
            template.setStyles((NativeTemplateStyle) styles);
            template.setNativeAd(nativeAd);
//            unifiedNativeAdView.setMediaView((com.google.android.gms.ads.nativead.MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
//            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
//            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
//            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
//            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
//            unifiedNativeAdView.setPriceView(unifiedNativeAdView.findViewById(R.id.ad_price));
//            unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ad_stars));
//            unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.ad_store));
//            unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.ad_advertiser));
//            ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
//            unifiedNativeAdView.getMediaView().setMediaContent(unifiedNativeAd.getMediaContent());
//            if (unifiedNativeAd.getBody() == null) {
//                unifiedNativeAdView.getBodyView().setVisibility(4);
//            } else {
//                unifiedNativeAdView.getBodyView().setVisibility(0);
//                ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
//            }
//            if (unifiedNativeAd.getCallToAction() == null) {
//                unifiedNativeAdView.getCallToActionView().setVisibility(4);
//            } else {
//                unifiedNativeAdView.getCallToActionView().setVisibility(0);
//                ((TextView) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
//            }
//            if (unifiedNativeAd.getIcon() == null) {
//                unifiedNativeAdView.getIconView().setVisibility(8);
//            } else {
//                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
//                unifiedNativeAdView.getIconView().setVisibility(0);
//            }
//            if (unifiedNativeAd.getStarRating() == null) {
//                unifiedNativeAdView.getStarRatingView().setVisibility(4);
//            } else {
//                ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
//                unifiedNativeAdView.getStarRatingView().setVisibility(0);
//            }
//            if (unifiedNativeAd.getAdvertiser() == null) {
//                unifiedNativeAdView.getAdvertiserView().setVisibility(4);
//            } else {
//                ((TextView) unifiedNativeAdView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
//                unifiedNativeAdView.getAdvertiserView().setVisibility(0);
//            }
//            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        } catch (Exception e) {

        }


//        unifiedNativeAd.getVideoController();
    }

    public void inflate_NATIV_FB(com.facebook.ads.NativeAd nativeAd, ViewGroup viewGroup) {
        try {
            int i = 0;
            viewGroup.setVisibility(0);
            nativeAd.unregisterView();
            View inflate = LayoutInflater.from(this.activity).inflate(R.layout.ads_nativ_fb, (ViewGroup) null);
            viewGroup.removeAllViews();
            viewGroup.addView(inflate);
            LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.ad_choices_container);
            AdOptionsView adOptionsView = new AdOptionsView(this.activity, nativeAd, (NativeAdLayout) inflate.findViewById(R.id.nativview));
            linearLayout.removeAllViews();
            linearLayout.addView(adOptionsView, 0);
            MediaView mediaView = (MediaView) inflate.findViewById(R.id.native_ad_icon);
            TextView textView = (TextView) inflate.findViewById(R.id.native_ad_title);
            MediaView mediaView2 = (MediaView) inflate.findViewById(R.id.native_ad_media);
            TextView textView2 = (TextView) inflate.findViewById(R.id.native_ad_social_context);
            TextView textView3 = (TextView) inflate.findViewById(R.id.native_ad_body);
            TextView textView4 = (TextView) inflate.findViewById(R.id.native_ad_sponsored_label);
            TextView textView5 = (TextView) inflate.findViewById(R.id.native_ad_call_to_action);
            textView.setText(nativeAd.getAdvertiserName());
            textView3.setText(nativeAd.getAdBodyText());
            textView2.setText(nativeAd.getAdSocialContext());
            if (!nativeAd.hasCallToAction()) {
                i = 4;
            }
            textView5.setVisibility(i);
            textView5.setText(nativeAd.getAdCallToAction());
            textView4.setText(nativeAd.getSponsoredTranslation());
            ArrayList arrayList = new ArrayList();
            arrayList.add(textView);
            arrayList.add(textView3);
            arrayList.add(textView5);
            arrayList.add(mediaView);
            arrayList.add(textView2);
            nativeAd.registerViewForInteraction(inflate, mediaView2, mediaView, (List<View>) arrayList);
        } catch (Exception e) {

        }
    }

    public void inflate_NATIV_ADMOB1(NativeAd unifiedNativeAd, ViewGroup viewGroup) {
        try {
            viewGroup.setVisibility(0);
            View inflate = LayoutInflater.from(this.activity).inflate(R.layout.ads_nativ_admob1, (ViewGroup) null);
            viewGroup.removeAllViews();
            viewGroup.addView(inflate);
            NativeAdView unifiedNativeAdView = (NativeAdView) inflate.findViewById(R.id.uadview);
            unifiedNativeAdView.setMediaView((com.google.android.gms.ads.nativead.MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
            unifiedNativeAdView.setPriceView(unifiedNativeAdView.findViewById(R.id.ad_price));
            unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ad_stars));
            unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.ad_store));
            unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.ad_advertiser));
            ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            unifiedNativeAdView.getMediaView().setMediaContent(unifiedNativeAd.getMediaContent());
            if (unifiedNativeAd.getBody() == null) {
                unifiedNativeAdView.getBodyView().setVisibility(4);
            } else {
                unifiedNativeAdView.getBodyView().setVisibility(0);
                ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
            }
            if (unifiedNativeAd.getCallToAction() == null) {
                unifiedNativeAdView.getCallToActionView().setVisibility(4);
            } else {
                unifiedNativeAdView.getCallToActionView().setVisibility(0);
                ((TextView) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
            }
            if (unifiedNativeAd.getIcon() == null) {
                unifiedNativeAdView.getIconView().setVisibility(8);
            } else {
                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                unifiedNativeAdView.getIconView().setVisibility(0);
            }
            if (unifiedNativeAd.getStarRating() == null) {
                unifiedNativeAdView.getStarRatingView().setVisibility(4);
            } else {
                ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
                unifiedNativeAdView.getStarRatingView().setVisibility(0);
            }
            if (unifiedNativeAd.getAdvertiser() == null) {
                unifiedNativeAdView.getAdvertiserView().setVisibility(4);
            } else {
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
                unifiedNativeAdView.getAdvertiserView().setVisibility(0);
            }
            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
//        unifiedNativeAd.getVideoController();
        } catch (Exception e) {

        }
    }

    public void inflate_FBSmallNativeAd(NativeBannerAd nativeBannerAd, ViewGroup viewGroup) {
        try {


            // Unregister last ad
//        nativeBannerAd.unregisterView();
            viewGroup.setVisibility(0);
            // Add the Ad view into the ad container.
            View inflate = LayoutInflater.from(this.activity).inflate(R.layout.ads_smallfb_native, (ViewGroup) null);
            viewGroup.removeAllViews();
            viewGroup.addView(inflate);

            NativeAdLayout nativeAdLayout = (NativeAdLayout) inflate.findViewById(R.id.native_banner_ad_container);
//        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
//        adView = (LinearLayout) inflate.inflate(R.layout.native_banner_ad_layout, nativeAdLayout, false);
//        nativeAdLayout.addView(adView);

            // Add the AdChoices icon
            RelativeLayout adChoicesContainer = inflate.findViewById(R.id.ad_choices_container);
            AdOptionsView adOptionsView = new AdOptionsView(activity, nativeBannerAd, nativeAdLayout);
            adChoicesContainer.removeAllViews();
            adChoicesContainer.addView(adOptionsView, 0);

            // Create native UI using the ad metadata.
            TextView nativeAdTitle = inflate.findViewById(R.id.native_ad_title);
            TextView nativeAdSocialContext = inflate.findViewById(R.id.native_ad_social_context);
            TextView sponsoredLabel = inflate.findViewById(R.id.native_ad_sponsored_label);
            MediaView nativeAdIconView = inflate.findViewById(R.id.native_icon_view);
            Button nativeAdCallToAction = inflate.findViewById(R.id.native_ad_call_to_action);

            // Set the Text.
            nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
            nativeAdCallToAction.setVisibility(
                    nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
            nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
            sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

            // Register the Title and CTA button to listen for clicks.
            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(nativeAdTitle);
            clickableViews.add(nativeAdCallToAction);
            nativeBannerAd.registerViewForInteraction(inflate, nativeAdIconView, clickableViews);
        } catch (Exception e) {

        }
    }
}