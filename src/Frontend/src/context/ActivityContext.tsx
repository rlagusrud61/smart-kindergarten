import React, {useEffect, useState} from "react";
import {ActivityHubClient} from "../clients/signalr/ActivityHubClient";

export interface ActivityContextProperties {

}

export const ActivityContext = React.createContext<ActivityContextProperties | undefined>(undefined);

export interface ActivityProviderProps {
    children?: React.ReactElement<any>
}

export const ActivityProvider = ({children}: ActivityProviderProps) => {

    const [hub, setHub] = useState<ActivityHubClient>();

    useEffect(() => {
        if(hub){
            return;
        }
        const hb = new ActivityHubClient();
        setHub(hb);
        hb.connect();
    },[hub])

    const implementation: ActivityContextProperties = {

    }

    return (
        <ActivityContext.Provider value={implementation}>
            {children}
        </ActivityContext.Provider>
    )
}
