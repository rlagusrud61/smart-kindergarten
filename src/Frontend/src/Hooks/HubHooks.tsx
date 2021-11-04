/* eslint-disable react-hooks/exhaustive-deps */
import {useEffect, useState} from "react";
import {IHubClient} from "../clients/signalr/ActivityHub";

export function UseHub<T extends IHubClient>(hubType: new (...args: any) => T, deps: any[], ...params: any[]): T | undefined {

    const [hub, setHub] = useState<T>();

    useEffect(() => {
        const hb = new hubType(...params);
        setHub(hb);

        hb?.connect();

        return () => {
            hb?.disconnect();
        }
    }, [hubType, ...deps])

    return hub;
}
