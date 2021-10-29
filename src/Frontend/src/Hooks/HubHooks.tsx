import {useEffect, useState} from "react";
import {IHubClient} from "../clients/signalr/ActivityHub";

export function UseHub<T extends IHubClient>(hubType: new () => T): T | undefined {

    const [hub, setHub] = useState<T>();

    useEffect(() => {
        if (hub) {
            return;
        }
        const hb = new hubType();
        setHub(hb);
        hb.connect();
    }, [hubType, hub])

    return hub;
}
