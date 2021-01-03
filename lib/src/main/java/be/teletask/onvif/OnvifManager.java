package be.teletask.onvif;

import be.teletask.onvif.listeners.*;
import be.teletask.onvif.models.OnvifDevice;
import be.teletask.onvif.models.OnvifMediaProfile;
import be.teletask.onvif.models.OnvifPreset;
import be.teletask.onvif.requests.*;
import be.teletask.onvif.responses.OnvifResponse;


/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifManager implements OnvifResponseListener {

    //Constants
    public final static String TAG = OnvifManager.class.getSimpleName();

    //Attributes
    private OnvifExecutor executor;
    private OnvifResponseListener onvifResponseListener;

    //Constructors
    public OnvifManager() {
        this(null);
    }

    private OnvifManager(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
        executor = new OnvifExecutor(this);
    }

    //Methods
    public void getServices(OnvifDevice device, OnvifServicesListener listener) {
        OnvifRequest request = new GetServicesRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getDeviceInformation(OnvifDevice device, OnvifDeviceInformationListener listener) {
        OnvifRequest request = new GetDeviceInformationRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getMediaProfiles(OnvifDevice device, OnvifMediaProfilesListener listener) {
        OnvifRequest request = new GetMediaProfilesRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getMediaStreamURI(OnvifDevice device, OnvifMediaProfile profile, OnvifMediaStreamURIListener listener) {
        OnvifRequest request = new GetMediaStreamRequest(profile, listener);
        executor.sendRequest(device, request);
    }

    public void getSnapshotURI(OnvifDevice device, OnvifMediaProfile profile, OnvifSnapshotURIListener listener) {
        OnvifRequest request = new GetSnapshotUriRequest(profile, listener);
        executor.sendRequest(device, request);
    }

    public void getStatus(OnvifDevice device, OnvifMediaProfile profile, OnvifStatusListener listener) {
        OnvifRequest request = new GetStatusRequest(profile, listener);
        executor.sendRequest(device, request);
    }

    public void getPresets(OnvifDevice device, OnvifMediaProfile profile, OnvifPresetsListener listener) {
        OnvifRequest request = new GetPresetsRequest(profile, listener);
        executor.sendRequest(device, request);
    }

    public void setPreset(OnvifDevice device, OnvifMediaProfile profile, String name, String token, OnvifPresetListener listener) {
        OnvifRequest request = new SetPresetRequest(profile, name, token, listener);
        executor.sendRequest(device, request);
    }

    public void removePreset(OnvifDevice device, OnvifMediaProfile profile, String token) {
        OnvifRequest request = new RemovePresetRequest(profile, token);
        executor.sendRequest(device, request);
    }

    public void gotoHomePosition(OnvifDevice device, OnvifMediaProfile profile) {
        OnvifRequest request = new GotoHomePositionRequest(profile);
        executor.sendRequest(device, request);
    }

    public void gotoPreset(OnvifDevice device, OnvifMediaProfile profile, OnvifPreset preset) {
        OnvifRequest request = new GotoPresetRequest(profile, preset);
        executor.sendRequest(device, request);
    }

    public void absoluteMove(OnvifDevice device, OnvifMediaProfile profile, double pan, double tilt, double zoom) {
        OnvifRequest request = new AbsoluteMoveRequest(profile, pan, tilt, zoom);
        executor.sendRequest(device, request);
    }


    public void getCreatePullPointSubscription(OnvifDevice device, CreatePullPointSubscriptionListener listener,
                                               String topicExpression, String initialTerminationTime ) {
        OnvifRequest request = new CreatePullSubscriptionRequest(listener, topicExpression, initialTerminationTime);
        executor.sendRequest(device, request);
    }

    public void getPullMessages(OnvifDevice device, PullMessagesListener listener, String subscriptionUrl, String messageLimit, String timeout ) {
        OnvifRequest request = new PullMessagesRequest(listener, subscriptionUrl, messageLimit, timeout);
        executor.sendRequest(device, request);
    }

    public void sendOnvifRequest(OnvifDevice device, OnvifRequest request) {
        executor.sendRequest(device, request);
    }

    public void setOnvifResponseListener(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
    }

    /**
     * Clear up the resources.
     */
    public void destroy() {
        onvifResponseListener = null;
        executor.clear();
    }

    @Override
    public void onResponse(OnvifDevice onvifDevice, OnvifResponse response) {
        if (onvifResponseListener != null)
            onvifResponseListener.onResponse(onvifDevice, response);
    }

    @Override
    public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {
        if (onvifResponseListener != null)
            onvifResponseListener.onError(onvifDevice, errorCode, errorMessage);
    }

}
