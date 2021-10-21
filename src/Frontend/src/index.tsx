import React from 'react';
import ReactDOM from 'react-dom';
import './styles/index.css';
import Home from './components/pages/Home';
import {BrowserRouter, Route, Routes, useRoutes} from "react-router-dom";
import {RouteObject, RouterProps} from "react-router";
import {Layout} from "./components/layout/Layout";
import {OtherPage} from "./components/pages/OtherPage";

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
            <Layout/>
        </BrowserRouter>
    </React.StrictMode>,
    document.getElementById('root')
);

