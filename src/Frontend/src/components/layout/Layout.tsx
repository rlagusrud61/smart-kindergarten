import * as React from 'react';
import {Suspense} from 'react';
import {Route, Routes} from "react-router-dom";
import {MainLayoutRoutes} from "../../index";
import {TopNav} from "./TopNav";

export const Layout = () => {

    return (
        <Routes location="">
            {MainLayoutRoutes.map((route, index) => (
                <Route
                    key={index}
                    path={route.path}
                    element={<>
                        <TopNav/>
                        <Suspense fallback={<></>}>
                            <main role="main">
                                {route.element}
                            </main>
                        </Suspense>
                    </>}
                />
            ))}
        </Routes>
    );
}
