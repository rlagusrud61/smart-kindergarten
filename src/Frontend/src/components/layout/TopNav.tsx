import {Link} from "react-router-dom";
import React from "react";
import {Toggle} from "../theme/Toggle";

export const TopNav = () => {
    return (
    <div className="z-10 fixed w-full h-16 bg-gradient-to-r from-purple-400 via-pink-500 to-red-500 text-white justify-between flex flex-row items-center">
        <div className="brand-logo text-2xl font-bold px-5">Smart Kindergarten &trade;</div>
        <ul className="flex flex-row px-5 justify-center items-center">
            <li className="px-5 "><Link to="/">Home</Link></li>
            <li className="px-5 "><Link to="/History">History</Link></li>
            <li className="px-5">
                <Toggle/>
            </li>
        </ul>
    </div>
        );
}
