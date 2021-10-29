import {HubConnectionBuilder, LogLevel} from "@microsoft/signalr";

export class ActivityHubClient {

    public readonly connection = new HubConnectionBuilder()
        .withUrl(process.env["REACT_APP_API_URI"]+"/hubs/Activity")
        .withAutomaticReconnect()
        .configureLogging(LogLevel.Warning)
        .build();

    constructor() {
        this.init();
    }

    private init(){
        this.connection.onclose(async () => {
            // We don't do closing where I'm from
            console.warn("SignalR connection was closed");
            await this.connect();
        });
    }

    public async connect(){
        await this.connection.start();
    }

    public async disconnect(){
        await this.connection.stop();
    }


}
