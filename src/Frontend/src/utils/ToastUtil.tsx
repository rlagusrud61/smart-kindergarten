import {toast} from "react-toastify";
import * as React from "react";

export const ToastDefault = (title : string | undefined, body: string | undefined = undefined) => {
    toast(<div><h1>{title}</h1><p style={{minHeight:"1em"}}>{body}</p></div>);
}

export const ToastDark = (title : string | undefined, body: string | undefined = undefined) => {
    toast.dark(<div><h1>{title}</h1><p style={{minHeight:"1em"}}>{body}</p></div>);
}

export const ToastSuccess = (title : string | undefined, body: string | undefined = undefined) => {
    toast.success(<div><h1>{title}</h1><p style={{minHeight:"1em"}}>{body}</p></div>);
}

export const ToastInfo = (title : string | undefined, body: string | undefined = undefined) => {
    toast.info(<div><h1>{title}</h1><p style={{minHeight:"1em"}}>{body}</p></div>);
}

export const ToastError = (title : string | undefined, body: string | undefined = undefined) => {
    toast.error(<div><h1>{title}</h1><p style={{minHeight:"1em"}}>{body}</p></div>);
}

export const ToastWarning = (title : string | undefined, body: string | undefined = undefined) => {
    toast.warning(<div><h1>{title}</h1><p style={{minHeight:"1em"}}>{body}</p></div>);
}
