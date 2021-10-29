export default class ClientBase {

    protected getBaseUrl(a: string, b: string | undefined){
        return process.env["REACT_APP_API_URI"] ?? "";
    }
}
