import React, {useEffect, useState} from "react";
import {ActivityHub} from "../clients/signalr/ActivityHub";
import {UseHub} from "../Hooks/HubHooks";
import {ToastDark} from "../utils/ToastUtil";
import {EventHistory, Student, UrgentEvent} from "../clients/rest/ApiClient";

export interface ActivityContextProperties {
    hub: ActivityHub | undefined
}

export const ActivityContext = React.createContext<ActivityContextProperties | undefined>(undefined);

export interface ActivityProviderProps {
    children?: React.ReactElement<any>
}

export const ActivityProvider = ({children}: ActivityProviderProps) => {

    const hub = UseHub(ActivityHub)

    useEffect(() => {

        hub?.urgentEventEvent.addHandler((history:EventHistory) => {
            ToastDark("Urgent Event", GetUrgentEventMessage(history.student!,history.event!))
        })

        // hub?.connect();
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

export const GetUrgentEventMessage = (student:Student, e:UrgentEvent) : string => {
    switch (e){
        case UrgentEvent.Crying:
            return `${student.name} is crying`
        case UrgentEvent.Falling:
            return `${student.name} has fallen 🪦`
        case UrgentEvent.Dying:
            return `${student.name} is dying inside`
        default:
            return `Unknown Event`
    }
}
