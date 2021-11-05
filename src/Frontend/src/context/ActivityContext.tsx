import React, {useEffect, useState} from "react";
import {ActivityHub} from "../clients/signalr/ActivityHub";
import {useHub} from "../Hooks/HubHooks";
import {ToastDark, ToastDefault} from "../utils/ToastUtil";
import {EventHistory, Student, UrgentEvent} from "../clients/rest/ApiClient";

export interface ActivityContextProperties {
    hub: ActivityHub | undefined
}

export const ActivityContext = React.createContext<ActivityContextProperties | undefined>(undefined);

export interface ActivityProviderProps {
    children?: React.ReactElement<any>
}

export const ActivityProvider = ({children}: ActivityProviderProps) => {

    const hub = useHub(ActivityHub, [])

    useEffect(() => {

        hub?.urgentEventEvent.addHandler((history:EventHistory) => {
            ToastDefault("Urgent Event", GetUrgentEventMessage(history.student!,history.event!))
        })
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
        default:
            return `Unknown Event`
    }
}
