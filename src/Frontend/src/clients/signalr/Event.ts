export class Event {

    // private callbacks : ((...params:any[]) => any)[] = [];
    private callbacks : Function[] = [];

    public addHandler(callback: Function){
        this.callbacks.push(callback);
    }

    public removeHandler(callback: Function){
        const index = this.callbacks.indexOf(callback);
        if(index >= 0){
            this.callbacks.splice(index, 1);
        }
    }

    public fire(...params : any[]){
        this.callbacks.forEach((callback) => callback(...params))
    }

}
