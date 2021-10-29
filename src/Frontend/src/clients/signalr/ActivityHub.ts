import {HubConnectionBuilder, LogLevel} from "@microsoft/signalr";
import {EventHistory, Student, UrgentEvent} from "../rest/ApiClient";
import {Event} from "./Event"

export interface IHubClient {
    connect: () => Promise<void>,
    disconnect: () => Promise<void>
}

export class ActivityHub {

    public readonly connection = new HubConnectionBuilder()
        .withUrl(process.env["REACT_APP_API_URI"]+"/hubs/Activity")
        .withAutomaticReconnect()
        .configureLogging(LogLevel.Warning)
        .build();

    constructor() {
        this.init();
    }

    /**
     * yeah... that's what it's called alright
     */
    public urgentEventEvent = new Event();

    private init(){
        this.connection.onclose(async () => {
            // We don't do closing where I'm from
            console.warn("SignalR connection was closed");
            // await this.connect();
        });

        this.connection.on("ReceiveUrgentEvent", (history:EventHistory) => {
            this.urgentEventEvent.fire(history);
        });
    }

    public async connect(){
        await this.connection.start();
    }

    public async disconnect(){
        await this.connection.stop();
    }
}
