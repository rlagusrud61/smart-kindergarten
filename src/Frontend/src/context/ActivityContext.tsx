import React, {useEffect, useState} from "react";
import {ActivityHubClient} from "../clients/signalr/ActivityHubClient";
import {UseHub} from "../Hooks/HubHooks";

export interface ActivityContextProperties {
    hub: ActivityHubClient | undefined
}

export const ActivityContext = React.createContext<ActivityContextProperties | undefined>(undefined);

export interface ActivityProviderProps {
    children?: React.ReactElement<any>
}

export const ActivityProvider = ({children}: ActivityProviderProps) => {

    const hub = UseHub(ActivityHubClient)

    useEffect(() => {
        return function cleanup() {
            hub?.disconnect();
        }
    },[hub])

    const implementation: ActivityContextProperties = {
        hub:hub
    }

    return (
        <ActivityContext.Provider value={implementation}>
            {children}
        </ActivityContext.Provider>
    )
}
