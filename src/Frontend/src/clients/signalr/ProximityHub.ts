import {HubConnectionBuilder, LogLevel} from "@microsoft/signalr";
import {Event} from "./Event"

export class ProximityHub {

    public readonly connection = new HubConnectionBuilder()
        .withUrl(process.env["REACT_APP_API_URI"] + "/hubs/Proximity")
        .withAutomaticReconnect()
        .configureLogging(LogLevel.Warning)
        .build();

    constructor() {
        this.init();
    }

    private init() {
        this.connection.onclose(async () => {
            // We don't do closing where I'm from
            console.warn("SignalR connection was closed");
        });

        this.connection.on("UpdateDeviceProximity", (studentId: string, nearbyStudents: string[]) => {
            this.onProximityUpdate.fire(studentId, nearbyStudents);
        });
    }

    public async connect() {
        await this.connection.start();
    }

    public async disconnect() {
        await this.connection.stop();
    }

    public onProximityUpdate = new Event();

}
