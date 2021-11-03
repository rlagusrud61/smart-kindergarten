import * as React from 'react';
import {Suspense, useEffect} from 'react';
import {Route, Routes} from "react-router-dom";
import {MainLayoutRoutes} from "../../index";
import {TopNav} from "./TopNav";
import {ToastContainer, ToastContainerProps} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {ThemeProvider} from "../theme/ThemeContext";
import {Background} from "../theme/Background";

export const Layout = () => {

    const toastProps: ToastContainerProps = {
        newestOnTop: true,
        position: "bottom-right",
        autoClose: 5000,
    }

    return (
        <Routes location="">
            {MainLayoutRoutes.map((route, index) => (
                <Route
                    key={index}
                    path={route.path}
                    element={
                        <>
                            <ThemeProvider initialTheme={"light"}>
                                <Background>
                            <TopNav/>
                            <ToastContainer {...toastProps} />
                            <Suspense fallback={<></>}>
                                <main role="main">
                                    {route.element}
                                </main>
                            </Suspense>
                                </Background>
                            </ThemeProvider>
                        </>}
                />
            ))}
        </Routes>
    );
}
