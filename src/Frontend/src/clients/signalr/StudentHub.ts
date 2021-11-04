import {HubConnection, HubConnectionBuilder, LogLevel} from "@microsoft/signalr";
import {Event} from "./Event";
import {Activity, VocalActivity} from "../rest/ApiClient";

export class StudentHub {

    public readonly connection: HubConnection;

    constructor(id: string) {
        this.connection = new HubConnectionBuilder()
            .withUrl(process.env["REACT_APP_API_URI"] + "/hubs/Student/" + id)
            .withAutomaticReconnect()
            .configureLogging(LogLevel.Warning)
            .build();
        this.init();
    }

    private init() {
        this.connection.onclose(async () => {
            // We don't do closing where I'm from
            console.warn("SignalR connection was closed");
        });

        this.connection.on("ReceiveActivityUpdate", (activity: Activity) => {
            this.onActivityUpdate.fire(activity);
        });

        this.connection.on("ReceiveVocalActivityUpdate", (activity: VocalActivity) => {
            this.onVocalActivityUpdate.fire(activity);
        });
    }

    public async connect() {
        await this.connection.start().catch(e => console.warn(e));
    }

    public async disconnect() {
        await this.connection.stop();
    }


    public onActivityUpdate = new Event();
    public onVocalActivityUpdate = new Event();

}
