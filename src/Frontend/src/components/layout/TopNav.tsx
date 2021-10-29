import {Link} from "react-router-dom";
import React from "react";

export const TopNav = () => {
    return (
    <div className="z-10 fixed w-full h-16 bg-gradient-to-r from-purple-400 via-pink-500 to-red-500 text-white justify-between flex flex-row items-center">
        <div className="brand-logo text-2xl font-bold px-5">Smart Kindergarten &trade;</div>
        <ul className="menu-list flex flex-row px-5">
            <li className="menu-list-item px-5"><Link to="/">Home</Link></li>
            <li className="menu-list-item px-5"><Link to="/OtherPage">History</Link></li>
        </ul>
    </div>
        );
}
