import React from 'react';
import ReactDOM from 'react-dom';
import Home from './components/pages/Home';
import {BrowserRouter} from "react-router-dom";
import {RouteObject} from "react-router";
import {Layout} from "./components/layout/Layout";
import {OtherPage} from "./components/pages/OtherPage";
import {ActivityProvider} from "./context/ActivityContext";
import './styles/tailwind.css';
import './styles/Kindergarten.scss';

export const MainLayoutRoutes: RouteObject[] = [
    {
        path: "/",
        element: <Home/>
    },
    {
        path: "/OtherPage",
        element: <OtherPage/>
    }
]

ReactDOM.render(
    <React.StrictMode>
        <BrowserRouter>
            <ActivityProvider>
                <Layout/>
            </ActivityProvider>
        </BrowserRouter>
    </React.StrictMode>,
    document.getElementById('root')
);

